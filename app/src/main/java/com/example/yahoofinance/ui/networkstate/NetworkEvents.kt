package com.example.yahoofinance.ui.networkstate

import androidx.lifecycle.LiveData

object NetworkEvents : LiveData<Event>() {
    internal fun notify(event: Event) {
        postValue(event)
    }
}