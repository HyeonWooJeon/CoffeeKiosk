package com.example.myapplication.MainViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.CHLog
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.Repository.MainRepository

class MainViewModel:ViewModel() {
    private val repository= MainRepository()

    fun loadFiltered(itemId: String): LiveData<List<ItemsModel>> {
        CHLog.d("mainViewModel","into MainViewModel $itemId")
        return repository.loadFiltered(itemId)
    }

    fun loadTitle(title: String): LiveData<List<ItemsModel>> {
        return repository.loadTitle(title)
    }
}