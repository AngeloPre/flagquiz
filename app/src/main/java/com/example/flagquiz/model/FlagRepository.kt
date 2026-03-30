package com.example.flagquiz.model

import com.example.flagquiz.R

object FlagRepository {
    fun allFlags(): List<Flag> = listOf(
        Flag("Argentina", R.drawable.flag_ar),
        Flag("Austrália", R.drawable.flag_au),
        Flag("Bélgica", R.drawable.flag_be),
        Flag("Brasil", R.drawable.flag_br),
        Flag("Canadá", R.drawable.flag_ca),
        Flag("Suíça", R.drawable.flag_ch),
        Flag("China", R.drawable.flag_cn),
        Flag("Alemanha", R.drawable.flag_de),
        Flag("Espanha", R.drawable.flag_es),
        Flag("França", R.drawable.flag_fr),
        Flag("Reino Unido", R.drawable.flag_gb),
        Flag("Índia", R.drawable.flag_in),
        Flag("Itália", R.drawable.flag_it),
        Flag("Japão", R.drawable.flag_jp),
        Flag("Coreia do Sul", R.drawable.flag_kr),
        Flag("México", R.drawable.flag_mx),
        Flag("Portugal", R.drawable.flag_pt),
        Flag("Rússia", R.drawable.flag_ru),
        Flag("Estados Unidos", R.drawable.flag_us),
        Flag("África do Sul", R.drawable.flag_za)
    )

    fun getQuizFlags(): ArrayList<Flag> {
        return ArrayList(allFlags().shuffled().take(5))
    }
}