package com.ava.ai.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ava.ai.data.models.Conversation
import com.ava.ai.data.models.CustomCommand
import com.ava.ai.data.models.Message

@Database(
    entities = [
        Message::class,
        Conversation::class,
        CustomCommand::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AvaDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun conversationDao(): ConversationDao
    abstract fun customCommandDao(): CustomCommandDao

    companion object {
        @Volatile
        private var INSTANCE: AvaDatabase? = null

        fun getDatabase(context: Context): AvaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AvaDatabase::class.java,
                    "maya_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
