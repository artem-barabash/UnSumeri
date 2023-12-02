package com.example.unsumeri.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unsumeri.data.firebase.ItemFirebaseManager
import com.example.unsumeri.data.room.UserDao
import com.example.unsumeri.domain.entities.UserAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val userAccount: UserAccount, private val userDao: UserDao): ViewModel() {
    private val _user = MutableLiveData<UserAccount?>()
    val user: LiveData<UserAccount?> = _user

    private val firebaseManager = ItemFirebaseManager()

    init {

        _user.value = userAccount

        CoroutineScope(Dispatchers.IO).launch {
            //userDao.deleteAllArticles()

            firebaseManager.retrieveAllArticles(userDao)
        }

    }
}