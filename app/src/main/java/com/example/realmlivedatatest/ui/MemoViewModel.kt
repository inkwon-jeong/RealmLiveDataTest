package com.example.realmlivedatatest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realmlivedatatest.repo.MemoRepository
import kotlinx.coroutines.launch

class MemoViewModel : ViewModel() {

    private val memoRepo = MemoRepository()

    val memos = memoRepo.getAllMemosAsync()
//    val memos = memoRepo.getAllMemosFlow().asLiveData()

    fun addMemo(contents: String) {
        viewModelScope.launch {
            memoRepo.addMemo(contents)
        }
    }
}