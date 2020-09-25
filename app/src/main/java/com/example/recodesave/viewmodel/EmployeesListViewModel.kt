package com.example.recodesave.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recodesave.repository.UserRepository
import com.example.recodesave.roomdb.User
import kotlinx.coroutines.*

class EmployeesListViewModel(private val repository: UserRepository) : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val user = MutableLiveData<MutableList<User>>()
    val users: LiveData<MutableList<User>>
        get() = user

    fun getUsers() {
        uiScope.launch {
            //Working on UI thread
            print(Thread.currentThread().name)
            //Use dispatcher to switch between context
            val deferred = async(Dispatchers.Default) {
                //Working on background thread
                user.postValue(repository.getUser() as MutableList<User>?)
            }
            //Working on UI thread
            print(deferred.await())
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
    }