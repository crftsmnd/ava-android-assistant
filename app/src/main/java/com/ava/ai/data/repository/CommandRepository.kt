package com.ava.ai.data.repository

import com.ava.ai.data.database.CustomCommandDao
import com.ava.ai.data.models.CustomCommand
import kotlinx.coroutines.flow.Flow

class CommandRepository(
    private val commandDao: CustomCommandDao
) {
    fun getEnabledCommands(): Flow<List<CustomCommand>> {
        return commandDao.getEnabledCommands()
    }

    fun getAllCommands(): Flow<List<CustomCommand>> {
        return commandDao.getAllCommands()
    }

    suspend fun searchCommands(query: String): List<CustomCommand> {
        return commandDao.searchCommands(query)
    }

    suspend fun saveCommand(command: CustomCommand): Long {
        return commandDao.insertCommand(command)
    }

    suspend fun updateCommand(command: CustomCommand) {
        commandDao.updateCommand(command)
    }

    suspend fun deleteCommand(command: CustomCommand) {
        commandDao.deleteCommand(command)
    }

    suspend fun deleteAllCommands() {
        commandDao.deleteAllCommands()
    }
}
