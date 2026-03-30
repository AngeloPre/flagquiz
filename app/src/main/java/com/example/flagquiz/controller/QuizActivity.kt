package com.example.flagquiz.controller

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flagquiz.R
import com.example.flagquiz.model.Flag
import com.example.flagquiz.model.FlagRepository
import java.text.Normalizer

class QuizActivity : AppCompatActivity() {

    private val quizFlags: ArrayList<Flag> = FlagRepository.getQuizFlags()
    private var currentIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ivFlag = findViewById<ImageView>(R.id.ivFlag)
        val etAnswer = findViewById<EditText>(R.id.etAnswer)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val tvFeedback = findViewById<TextView>(R.id.tvFeedback)
        val tvQuestionCounter = findViewById<TextView>(R.id.tvQuestionCounter)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        fun showQuestion() {
            val flag = quizFlags[currentIndex]
            ivFlag.setImageResource(flag.drawableRes)
            tvQuestionCounter.text = "Pergunta ${currentIndex + 1} de ${quizFlags.size}"
            progressBar.progress = (currentIndex * 100) / quizFlags.size
            etAnswer.text.clear()
            tvFeedback.visibility = View.GONE
            btnNext.visibility = View.GONE
            btnConfirm.isEnabled = true
        }

        showQuestion()

        btnConfirm.setOnClickListener {
            val answer = etAnswer.text.toString().trim()
            if (answer.isEmpty()) return@setOnClickListener

            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etAnswer.windowToken, 0)

            val correct = quizFlags[currentIndex].country

            if (removeAccents(answer).equals(removeAccents(correct), ignoreCase = true)) {
                tvFeedback.text = "Correto!"
                tvFeedback.setTextColor(getColor(android.R.color.holo_green_dark))
                score += 20
            } else {
                tvFeedback.text = "Incorreto! Era: $correct"
                tvFeedback.setTextColor(getColor(android.R.color.holo_red_dark))
            }

            tvFeedback.visibility = View.VISIBLE
            
            if (currentIndex == quizFlags.size - 1) {
                btnNext.text = "Ver resultados"
            } else {
                btnNext.text = "Próximo"
            }

            btnNext.visibility = View.VISIBLE
            btnConfirm.isEnabled = false
        }

        btnNext.setOnClickListener {
            currentIndex++
            if (currentIndex < quizFlags.size) {
                showQuestion()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SCORE", score)
                intent.putExtra("USER", getIntent().getStringExtra("USER"))
                startActivity(intent)
            }
        }
    }

    private fun removeAccents(str: String): String {
        val normalized = Normalizer.normalize(str, Normalizer.Form.NFD)
        return normalized.replace(Regex("[\\p{InCombiningDiacriticalMarks}]"), "")
    }
}