package com.ava.ai

import android.app.Application
import com.ava.ai.data.database.AvaDatabase
import com.ava.ai.data.datastore.PreferencesManager

class AvaApplication : Application() {

    lateinit var database: AvaDatabase
        private set

    lateinit var preferencesManager: PreferencesManager
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Initialize database
        database = AvaDatabase.getDatabase(this)

        // Initialize preferences
        preferencesManager = PreferencesManager(this)
    }

    companion object {
        lateinit var instance: AvaApplication
            private set
    }
}
