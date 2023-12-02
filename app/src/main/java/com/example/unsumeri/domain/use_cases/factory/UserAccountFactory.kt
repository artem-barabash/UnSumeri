package com.example.unsumeri.domain.use_cases.factory

import com.example.unsumeri.domain.entities.UserAccount

class UserAccountFactory(private val userAccount: UserAccount) {

    init {
        ACCOUNT = userAccount
    }

    companion object{

        lateinit var ACCOUNT : UserAccount
    }
}