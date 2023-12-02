package com.example.unsumeri.presentation.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.namespace.R
import com.example.namespace.databinding.FragmentLoginBinding
import com.example.unsumeri.data.firebase.FirebaseManager
import com.example.unsumeri.domain.entities.LoggedInUserView
import com.example.unsumeri.domain.entities.User
import com.example.unsumeri.domain.use_cases.factory.LoginViewModelFactory
import com.example.unsumeri.presentation.ui.HomeActivity
import com.example.unsumeri.presentation.view_model.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var fireBaseManager: FirebaseManager

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var sharedPreferences: SharedPreferences
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)




        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        usernameEditText = binding.username
        passwordEditText = binding.password
        val loginButton = binding.login
        val registrationButton = binding.register

        val loadingProgressBar = binding.loading


        auth = FirebaseAuth.getInstance()

        sharedPreferences = requireContext().getSharedPreferences(TEMP_USER_DATA, MODE_PRIVATE)

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        loginButton.setOnClickListener {
            if(loginViewModel.isOnline(requireContext())){

                if(usernameEditText.text.toString().isNotEmpty() && passwordEditText.text.toString().isNotEmpty()){
                    auth.signInWithEmailAndPassword(usernameEditText.text.toString(), passwordEditText.text.toString())
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                getUserDataInFireBase(usernameEditText.text.toString(),
                                    passwordEditText.text.toString())
                                println("UUID = " + task.result.user!!.uid)


                            }
                        }.addOnFailureListener {
                            Toast.makeText(requireContext(), "Wrong login or password!", Toast.LENGTH_SHORT).show()
                        }
                }

            }else{
                Toast.makeText(requireContext(), "No internet connection!", Toast.LENGTH_SHORT).show()
            }

        }

        registrationButton.setOnClickListener {
            if(loginViewModel.isOnline(requireContext())){
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registrationFragment)
            }else{
                Toast.makeText(requireContext(), "No internet connection!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserDataInFireBase(email: String, password: String) {
        val dataReferenceUser: DatabaseReference = databaseReference.child("User")


        val query: Query = dataReferenceUser.orderByChild("email").equalTo(email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach{
                    val key = it.key.toString()
                    val user = User(
                        it.child("firstName").value.toString(),
                        it.child("lastName").value.toString(),
                        it.child("email").value.toString(),
                    )

                    lifecycleScope.coroutineContext.let {
                        sharedPreferences.edit().clear().apply()

                        println("login=$user")

                        saveDataSharedPreferences(user, key, password)
                    }
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                print(error.toException())
            }
        })
    }

    fun saveDataSharedPreferences(user: User, numberKey: String, password: String){
        val editor = sharedPreferences.edit()

        editor.putString(FIRST_NAME, user.firstName)
        editor.putString(LAST_NAME, user.lastName)
        editor.putString(EMAIL, user.email)
        editor.putString(PASSWORD, password)
        editor.putString(NUMBER_KEY, numberKey)

        editor.commit()
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        //val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        //val appContext = context?.applicationContext ?: return
        //Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()


    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }


    override fun onStart() {
        super.onStart()

        binding.username.text.clear()
        binding.password.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val TEMP_USER_DATA : String = "MySharedPref"

        const val FIRST_NAME: String = "firstNameU"
        const val LAST_NAME: String = "lastNameU"
        const val EMAIL: String = "emailU"
        const val PASSWORD: String = "passwordU"

        const val NUMBER_KEY: String = "numberKeyU"
    }
}