package com.example.quizapp


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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FragmentA : Fragment() {


    private lateinit var binding: FragmentABinding
    // create string for question, answer and options

    lateinit var dataBase: QuestionDataBase
    private var currentQuestionIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentABinding.inflate(inflater, container, false)

        dataBase = Room.databaseBuilder(requireContext(), QuestionDataBase::class.java, "questionDatabase").fallbackToDestructiveMigration().build()

        GlobalScope.launch {
            dataBase.questionDao().addQuestion(Questions(0,"Tell Name","savita","BBli","Pinky","Sonum","Sonum"))

        }

        getData(binding.root)
        return binding.root


    }

    fun getData(view: View){

        dataBase.questionDao().getAllQuestions().observe(viewLifecycleOwner, Observer {
                questions ->
            if (questions.isNotEmpty()) {
                val currentQuestion = questions[currentQuestionIndex]
                binding.tvQuestion.text = currentQuestion.questionText
                binding.radioButton1.text = currentQuestion.option1
                binding.radioButton2.text = currentQuestion.option2
                binding.radioButton3.text = currentQuestion.option3
                binding.radioButton4.text = currentQuestion.option4
            }



        })
        binding.nextQuestionBtn.setOnClickListener {
            currentQuestionIndex++
            getData(it)
        }

    }

}