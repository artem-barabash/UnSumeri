package com.example.unsumeri.presentation.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.unsumeri.data.room.UserDao
import com.example.unsumeri.domain.entities.UserAccount
import com.example.unsumeri.presentation.view_model.HomeViewModel

class HomeViewModelFactory(private val userAccount: UserAccount, private val userDao: UserDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(userAccount, userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}