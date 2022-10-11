package com.farroos.movie.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "fullname")
    var fullname: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "address")
    var address: String

) : Parcelable
