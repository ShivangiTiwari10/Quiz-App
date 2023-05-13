package com.example.quizapp


import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.quizapp.database.QuestionDataBase
import com.example.quizapp.databinding.FragmentABinding
import com.example.quizapp.models.Questions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class FragmentA : Fragment() {

    private lateinit var binding: FragmentABinding

    lateinit var dataBase: QuestionDataBase
    private var currentQuestionIndex = 0

    private val totalQuestions = 6 // Total number of questions in the database
    private var answeredQuestions = 0 // Number of questions answered by the user
    private var score = 0

    private lateinit var dialog: Dialog
    private lateinit var dialogWrong: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentABinding.inflate(inflater, container, false)


        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.custom_dialog)

        dialogWrong = Dialog(requireContext())
        dialogWrong.setContentView(R.layout.custom_dialog_wrong)


        dataBase =
            Room.databaseBuilder(requireContext(), QuestionDataBase::class.java, "questionDatabase")
                .fallbackToDestructiveMigration().build()

        insertQuestion()

        getData(binding.root)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    fun getData(view: View) {

        dataBase.questionDao().getAllQuestions().observe(viewLifecycleOwner, Observer { questions ->

            if (currentQuestionIndex < totalQuestions) {
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
//                          ? Set Dialog Box

                        dialog.show()
                        score++
                        currentQuestionIndex++

                    } else {
                        dialogWrong.show()
                        currentQuestionIndex++

                    }
                    answeredQuestions++

                    binding.txtPlayScore.text = "Your score is:$score"

                    getData(it)
                }
            }


            if (currentQuestionIndex < totalQuestions) {
                // Continue showing the next question
                val nextQuestion = questions[currentQuestionIndex]
                binding.tvQuestion.text = nextQuestion.questionText
                binding.radioButton1.text = nextQuestion.option1
                binding.radioButton2.text = nextQuestion.option2
                binding.radioButton3.text = nextQuestion.option3
                binding.radioButton4.text = nextQuestion.option4
            } else {

                fragmentB()

            }
        })
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun insertQuestion() {
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
    }

    //    Start FragmentB
    private fun fragmentB() {

        val fragmentB = FragmentB()

        val args = Bundle()
        args.putInt("totalQuestions", totalQuestions)
        args.putInt("Score", score)
        fragmentB.arguments = args

        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.container, fragmentB)
        transaction.commit()

    }

}