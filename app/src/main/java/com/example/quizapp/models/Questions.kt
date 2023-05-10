package com.example.quizapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Questions(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val questionText: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctAnswer: String
)
