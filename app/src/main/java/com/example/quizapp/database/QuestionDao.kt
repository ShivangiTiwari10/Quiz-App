package com.example.quizapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.quizapp.models.Questions

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuestion(question: Questions)


    @Query("SELECT * FROM questions")
    fun getAllQuestions(): LiveData<List<Questions>>


}