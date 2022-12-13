package com.itmobile.vabw.colordetection.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.itmobile.vabw.colordetection.model.UserColor

class ColorRepository(application: Application) {
    private val colorDao = ColorDatabase.getInstance(application).getColorDao()

    suspend fun insertColor(color: UserColor) {
        colorDao.insertColor(color)
    }

    suspend fun deleteColor(color: UserColor) {
        colorDao.deleteColor(color)
    }

    fun getAllColor(): LiveData<List<UserColor>> {
        return colorDao.getAllColor()
    }

}