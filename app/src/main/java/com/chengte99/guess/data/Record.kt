package com.chengte99.guess.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Record(
    @NonNull
    @ColumnInfo(name = "nick")
    var name: String,
    @NonNull
    var counter: Int) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}

@Entity
class Word {
    @PrimaryKey
    var name = ""
    var info = ""
    var stat = 0
}