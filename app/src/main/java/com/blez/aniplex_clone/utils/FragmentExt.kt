package com.blez.aniplex_clone.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.blez.aniplex_clone.R

fun rotateView(imageView: ImageView, context : Context
){
 val    imageView = imageView as ImageView
    val rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_clockwise)
    imageView.startAnimation(rotate)
}