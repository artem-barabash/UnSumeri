package com.example.unsumeri.presentation.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.namespace.R
import com.example.namespace.databinding.FragmentRegistrationBinding
import com.example.unsumeri.data.Validator
import com.example.unsumeri.domain.entities.User
import com.example.unsumeri.domain.use_cases.factory.LoginViewModelFactory
import com.example.unsumeri.presentation.view_model.LoginViewModel


class RegistrationFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentRegistrationBinding? = null

    private val binding get() = _binding!!

    lateinit var textFirstName: EditText
    lateinit var textLastName: EditText
    lateinit var textEmailAddress: EditText
    lateinit var textPassword: EditText
    lateinit var textRepeatPassword: EditText

    lateinit var buttonSubmit: Button
    lateinit var buttonBack:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)


        val textObserver = Observer<String>{ text ->
            binding.textViewFailure.text = text
        }

        loginViewModel.statusRegistration.observe(this, textObserver)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        textFirstName = binding.textFirstName
        textLastName = binding.textLastName
        textEmailAddress = binding.textEmailAddress
        textPassword = binding.textPassword
        textRepeatPassword = binding.textRepeatPassword

        buttonSubmit = binding.registerButton

        buttonBack = binding.cancelButton

        buttonSubmit.setOnClickListener {

            val user = User(
                firstName = textFirstName.text.toString(),
                lastName = textLastName.text.toString(),
                email = textEmailAddress.text.toString(),
            )


            if(loginViewModel.checkUserRegistrationData(user, textPassword.text.toString(),
                    textRepeatPassword.text.toString())){

                if(Validator.PasswordValidator.methodValidPassword(user, binding.textPassword.text.toString())) {
                    if (loginViewModel.comparePassword(
                            binding.textPassword.text.toString(),
                            binding.textRepeatPassword.text.toString()
                        )
                    ) {
                        loginViewModel.registrationUser(user = user, textPassword.text.toString())

                        Navigation.findNavController(requireView()).navigate(R.id.action_registrationFragment_to_loginFragment)
                    }
                }else{
                    messageAlert(R.string.password_error)
                }
            }else{
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }

        buttonBack.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_registrationFragment_to_loginFragment)
        }

    }

    private fun messageAlert(message: Int){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}