package com.example.realmlivedatatest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.realmlivedatatest.repo.CounterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {

    private val counterRepo = CounterRepository()

    //    private val counter = counterRepo.getCounterAsync()
    private val counter = counterRepo.getCounterFlow().asLiveData()

    val count = counter.map { it?.count ?: 0 }

    fun incrementCount() {
        viewModelScope.launch {
            counterRepo.incrementCount()
        }
    }

    fun autoIncrementCount() {
        viewModelScope.launch {
            repeat(5) {
                delay(1000)
                counterRepo.incrementCount()
            }
        }
    }
}