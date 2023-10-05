package com.example.unsumeri.presentation.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.namespace.R
import com.example.unsumeri.domain.entities.User
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.EMAIL
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.FIRST_NAME
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.LAST_NAME
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.NUMBER_KEY
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.PASSWORD
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.TEMP_USER_DATA

class HomeActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getUserData()
    }



    private fun getUserData() {

        sharedPreferences = this.getSharedPreferences(TEMP_USER_DATA, MODE_PRIVATE)

        var user = User(
            sharedPreferences.getString(FIRST_NAME, "").toString(),
            sharedPreferences.getString(LAST_NAME, "").toString(),

            sharedPreferences.getString(EMAIL, "").toString(),

        )

        val numberKey = sharedPreferences.getString(NUMBER_KEY, "").toString()
        val password = sharedPreferences.getString(PASSWORD, "").toString()


        println("user=$user")
        println("numberKey=$numberKey")
        println("password=$password")

        //getBalanceFromFirebase()
    }



}