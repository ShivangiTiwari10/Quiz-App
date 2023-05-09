package com.example.quizapp

import androidx.room.*

@Dao
interface QuestionDao {

    @Insert
    suspend fun insertQuestion(question: Questions)

    @Update
    suspend fun updateQuestion(question: Questions)

    @Delete
    suspend fun deleteQuestion(question: Questions)


    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<Questions>
}