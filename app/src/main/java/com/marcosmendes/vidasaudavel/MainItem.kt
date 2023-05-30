package com.marcosmendes.vidasaudavel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItem(
    val id: Int,
    @DrawableRes val drawableId: Int,
    @StringRes val stringResId: Int,
    val color: Int
    )
