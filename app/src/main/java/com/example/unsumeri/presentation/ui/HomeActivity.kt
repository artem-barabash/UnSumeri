package com.example.unsumeri.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.namespace.R
import com.example.namespace.databinding.ActivityHomeBinding
import com.example.unsumeri.domain.entities.User
import com.example.unsumeri.domain.entities.UserAccount
import com.example.unsumeri.domain.use_cases.factory.UserAccountFactory
import com.example.unsumeri.presentation.ui.fragments.CategoriesFragment
import com.example.unsumeri.presentation.ui.fragments.HomeFragment
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.EMAIL
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.FIRST_NAME
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.LAST_NAME
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.NUMBER_KEY
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.PASSWORD
import com.example.unsumeri.presentation.ui.fragments.LoginFragment.Companion.TEMP_USER_DATA
import com.example.unsumeri.presentation.view_model.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var sharedPreferences: SharedPreferences

    private var page = 0

    private val sharedViewModel:HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        super.onCreate(savedInstanceState)


        getUserData()

        //changePage(page)
        replaceFragment(HomeFragment())

        binding.bottomNavigationBar.setOnItemSelectedListener {

            when (it.itemId){
                R.id.itemHome -> { replaceFragment(HomeFragment()) }
                R.id.itemCategory -> { replaceFragment(CategoriesFragment()) }
                R.id.itemSettings -> { replaceFragment(HomeFragment()) }
                R.id.itemProfile -> { replaceFragment(HomeFragment()) }
            }
            //changePage(page)

            return@setOnItemSelectedListener true
        }

        binding.swiperefresh.setOnRefreshListener {
            val i = Intent(this@HomeActivity, HomeActivity::class.java)
            finish()
            overridePendingTransition(1, 1)
            startActivity(i)
        }
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

        val userAccount = UserAccount(numberKey, user, password)

        UserAccountFactory(userAccount)

        println("user=$user")
        println("numberKey=$numberKey")
        println("password=$password")
    }

    private fun changePage(changedPage:Int){
        when(changedPage){
            0 -> replaceFragment(HomeFragment())
            1 -> replaceFragment(CategoriesFragment())
            2 -> replaceFragment(HomeFragment())
            3 -> replaceFragment(HomeFragment())
        }
    }
    private fun replaceFragment(selected: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_layout, selected)
        transaction.setReorderingAllowed(true)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.bottomNavigationBar.selectedItemId =  R.id.itemHome
    }


}