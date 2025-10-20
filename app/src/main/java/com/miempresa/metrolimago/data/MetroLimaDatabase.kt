package com.miempresa.metrolimago.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.miempresa.metrolimago.data.dao.EstacionDao
import com.miempresa.metrolimago.model.Estacion

@Database(entities = [Estacion::class], version = 1)
abstract class MetroLimaDatabase : RoomDatabase() {
    abstract fun estacionDao(): EstacionDao
}