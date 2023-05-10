package com.example.quizapp.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizapp.models.Questions


@Database(entities = [Questions::class], version = 2)
abstract class QuestionDataBase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

}