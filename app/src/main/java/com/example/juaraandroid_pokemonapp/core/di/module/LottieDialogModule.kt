package com.example.juaraandroid_pokemonapp.core.di.module

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.juaraandroid_pokemonapp.databinding.CustomLoadingBinding
import com.example.juaraandroid_pokemonapp.layoutInflater
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class LottieDialogModule {

    @Provides
    @ActivityScoped
    @CustomDialogQualifier
    fun provideLottieDialog(@ActivityContext context: Context): AlertDialog {
        val loadingBinding = CustomLoadingBinding.inflate(context.layoutInflater)
        loadingBinding.lottieAnimation.enableMergePathsForKitKatAndAbove(true)
        val dialogBuilder = AlertDialog.Builder(context).run {
            setView(loadingBinding.root)
        }
        return dialogBuilder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }
}