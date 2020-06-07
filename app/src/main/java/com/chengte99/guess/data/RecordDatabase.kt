package com.chengte99.guess.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Record::class), version = 1)
abstract class RecordDatabase: RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        private var instance: RecordDatabase? = null
        fun getInstance(context: Context): RecordDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context,
                    RecordDatabase::class.java,
                    "record.db").build()
            }
            return instance
        }
    }

}