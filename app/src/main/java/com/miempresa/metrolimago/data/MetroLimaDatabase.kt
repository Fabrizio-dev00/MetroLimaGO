package com.miempresa.metrolimago.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miempresa.metrolimago.data.dao.EstacionDao
import com.miempresa.metrolimago.model.Estacion

@Database(entities = [Estacion::class], version = 2, exportSchema = false)
abstract class MetroLimaDatabase : RoomDatabase() {

    abstract fun estacionDao(): EstacionDao

    companion object {
        @Volatile
        private var INSTANCE: MetroLimaDatabase? = null

        fun getDatabase(context: Context): MetroLimaDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MetroLimaDatabase::class.java,
                    "metro_lima_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}