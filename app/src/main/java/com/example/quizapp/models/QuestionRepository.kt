package com.example.quizapp.models


import androidx.lifecycle.LiveData
import com.example.quizapp.database.QuestionDao

class QuestionRepository(private var questionDao: QuestionDao)  {

    val allQuestions: LiveData<List<Questions>> = questionDao.getAllQuestions()

    suspend fun addQuestion(question: Questions) {
        questionDao.addQuestion(question)
    }

}