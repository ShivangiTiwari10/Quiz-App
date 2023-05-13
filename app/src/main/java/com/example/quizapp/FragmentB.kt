package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizapp.databinding.FragmentBBinding

class FragmentB : Fragment() {


    private lateinit var binding: FragmentBBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBBinding.inflate(inflater, container, false)

        val totalQuestions = arguments?.getInt("totalQuestions", 0)
        val score = arguments?.getInt("Score", 0) ?: 0

        binding.getScore.text = "You have scored:$score,\n out of:$totalQuestions"

        binding.btnStartAgain.setOnClickListener {
            fragmentA()
        }
        return binding.root

    }

    private fun fragmentA() {
        val fragmentA = FragmentA()
        val fragmentManager = requireActivity().supportFragmentManager

        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.container, fragmentA)
        transaction.commit()

    }


}