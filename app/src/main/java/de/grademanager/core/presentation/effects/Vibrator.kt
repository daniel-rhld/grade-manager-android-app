package de.grademanager.core.presentation.effects

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

fun vibrate(context: Context, duration: Long) {
    val effect = VibrationEffect.createOneShot(
        duration,
        VibrationEffect.DEFAULT_AMPLITUDE
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).apply {
            vibrate(
                CombinedVibration.createParallel(effect)
            )
        }
    } else {
        (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).apply {
            vibrate(effect)
        }
    }
}