package com.marcosmendes.vidasaudavel

import android.app.Application
import com.marcosmendes.vidasaudavel.model.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.getDatabase(this)

    }
}