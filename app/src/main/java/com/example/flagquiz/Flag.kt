package com.example.flagquiz

import java.io.Serializable

data class Flag(
    val country: String,    // nome do país
    val drawableRes: Int    // R.drawable.flag_xx
) : Serializable