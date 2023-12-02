package com.example.unsumeri.presentation.view_model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.namespace.R
import com.example.unsumeri.data.firebase.FirebaseManager
import com.example.unsumeri.data.LoginRepository
import com.example.unsumeri.data.Validator
import com.example.unsumeri.domain.use_cases.Result
import com.example.unsumeri.domain.entities.LoggedInUserView
import com.example.unsumeri.domain.entities.LoginFormState
import com.example.unsumeri.domain.entities.LoginResult
import com.example.unsumeri.domain.entities.User
import com.google.firebase.auth.FirebaseAuth


class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val fireBaseManager = FirebaseManager()
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val _statusRegistration = MutableLiveData<String>()
    val statusRegistration: LiveData<String> = _statusRegistration


    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }


    fun isOnline(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        if (capabilities != null) {
            return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                true
            }else{
                false
            }
        }else{

            return false
        }

    }

    fun registrationUser(user: User, password: String) {
        fireBaseManager.addUser(user)
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
    }

    fun checkUserRegistrationData(user: User,password: String, repeatPassword: String) : Boolean {
        if (user.firstName.isNotEmpty() && user.lastName.isNotEmpty()
            && user.email.isNotEmpty() && password.isNotEmpty()
            && repeatPassword.isNotEmpty()
        ) {

            if (Validator.methodValidEmail(user.email)
                && Validator.ValidatorForPersonality.methodCheckPersonalityData(user.firstName) &&
                Validator.ValidatorForPersonality.methodCheckPersonalityData(user.lastName)
            ) {


                _statusRegistration.value = ""

                return true

                //при внесении в базу "${dateBirthday.dayOfMonth}.${dateBirthday.monthValue}.${dateBirthday.year}"


            } else {
                _statusRegistration.value = "The user information is not correct!"
                return false
            }


        } else {
            _statusRegistration.value = "Fields can not be empty!"
            return false
        }

        return false
    }


    fun comparePassword(password: String, repeatPassword: String): Boolean{
        return if(password == repeatPassword)
            true
        else{
            _statusRegistration.value = "Passwords do not match!"
            false
        }
    }


}