package com.example.quizapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.quizapp.database.QuestionDataBase
import com.example.quizapp.databinding.FragmentABinding
import com.example.quizapp.models.Questions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FragmentA : Fragment() {


    private lateinit var binding: FragmentABinding


    lateinit var dataBase: QuestionDataBase
    private var currentQuestionIndex = 0

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentABinding.inflate(inflater, container, false)

        dataBase =
            Room.databaseBuilder(requireContext(), QuestionDataBase::class.java, "questionDatabase")
                .fallbackToDestructiveMigration().build()

        GlobalScope.launch {
            dataBase.questionDao().addQuestion(
                Questions(
                    0,
                    "How to kill an activity in Android?",
                    "finish()",
                    " finishActivity(int requestCode)",
                    "A & B",
                    " kill()",
                    "A & B"
                )
            )
            dataBase.questionDao().addQuestion(
                Questions(
                    0,
                    "What is an anonymous class in android?",
                    "Interface class",
                    "class that don't have name but functionalities in it",
                    "Java class",
                    "Manifest file",
                    "class that don't have name but functionalities in it"
                )
            )
            dataBase.questionDao().addQuestion(
                Questions(
                    0,
                    "Android is?",
                    "an operating system",
                    "a web browser",
                    "a web server",
                    "None of the above",
                    "an operating system"
                )
            )
            dataBase.questionDao().addQuestion(
                Questions(
                    0,
                    "Android is based on which of the following language?",
                    "Java",
                    "C++",
                    "C",
                    "None of the above",
                    "Java"
                )
            )
            dataBase.questionDao().addQuestion(
                Questions(
                    0,
                    "APK stands for -",
                    "Android Phone Kit",
                    "Android Page Kit",
                    "Android Package Kit",
                    "None of the above",
                    "Android Package Kit"
                )
            )
            dataBase.questionDao().addQuestion(
                Questions(
                    0,
                    " What is an activity in android?",
                    "android class",
                    "android package",
                    "A single screen in an application with supporting java code",
                    "None of the above",
                    "A single screen in an application with supporting java code"
                )
            )
        }


        getData(binding.root)
        return binding.root


    }

    fun getData(view: View) {

        dataBase.questionDao().getAllQuestions().observe(viewLifecycleOwner, Observer { questions ->
            if (questions.isNotEmpty()) {
                val currentQuestion = questions[currentQuestionIndex]

                binding.tvQuestion.text = currentQuestion.questionText
                binding.radioButton1.text = currentQuestion.option1
                binding.radioButton2.text = currentQuestion.option2
                binding.radioButton3.text = currentQuestion.option3
                binding.radioButton4.text = currentQuestion.option4


                val correctAnswer = currentQuestion.correctAnswer
                binding.nextQuestionBtn.setOnClickListener {
                    val selectedAns = when (binding.radiogrp.checkedRadioButtonId) {
                        R.id.radioButton1 -> currentQuestion.option1
                        R.id.radioButton2 -> currentQuestion.option2
                        R.id.radioButton3 -> currentQuestion.option3
                        R.id.radioButton4 -> currentQuestion.option4
                        else -> ""
                    }

                    val isCorrect = selectedAns == correctAnswer

                    if (isCorrect) {
                        currentQuestionIndex++
                    } else {

                        Toast.makeText(requireContext(), "Wrong Answer", Toast.LENGTH_SHORT).show()
                    }
                    getData(it)
                }
            }


        })


    }

}