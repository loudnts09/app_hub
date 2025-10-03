package com.example.hubapplication.hoopscore

import android.view.View
import android.widget.TextView

data class Team(
    var name: String,
    var score: Int = 0,
    var streak: Int = 0,
    val scoreView: TextView,
    val fireView: TextView,
    val nameView: TextView
) {
    fun addPoints(points: Int, streakThreshold: Int) {
        score += points
        streak++
        scoreView.text = score.toString()
        updateFire(streakThreshold)
    }

    fun reset() {
        score = 0
        streak = 0
        scoreView.text = "0"
        fireView.visibility = View.GONE
    }

    fun resetStreak() {
        streak = 0
        updateFire(Int.MAX_VALUE) // esconde fogo
    }

    private fun updateFire(streakThreshold: Int) {
        fireView.visibility = if (streak >= streakThreshold) View.VISIBLE else View.GONE
    }
}
