package com.example.unsumeri.domain.use_cases

import android.app.Application
import com.example.unsumeri.data.room.AppDatabase

class UserDataApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}