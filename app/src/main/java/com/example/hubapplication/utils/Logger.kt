package com.example.hubapplication.utils

import android.util.Log

object Logger {
    // Nível padrão
    private const val DEFAULT_TAG = "HubApp"

    // DEBUG
    fun d(tag: String = DEFAULT_TAG, message: String) {
        Log.d(tag, message)
    }

    // INFO
    fun i(tag: String = DEFAULT_TAG, message: String) {
        Log.i(tag, message)
    }

    // WARN
    fun w(tag: String = DEFAULT_TAG, message: String) {
        Log.w(tag, message)
    }

    // ERROR
    fun e(tag: String = DEFAULT_TAG, message: String) {
        Log.e(tag, message)
    }

    // VERBOSE
    fun v(tag: String = DEFAULT_TAG, message: String) {
        Log.v(tag, message)
    }

    // ASSERT
    fun wtf(tag: String = DEFAULT_TAG, message: String) {
        Log.wtf(tag, message)
    }
}
