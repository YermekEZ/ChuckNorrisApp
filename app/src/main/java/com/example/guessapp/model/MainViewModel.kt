package com.example.guessapp.model

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guessapp.ApiInterface
import com.example.guessapp.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainViewModel: ViewModel() {
    private var _joke: MutableLiveData<String> = MutableLiveData()
        val joke: LiveData<String>
            get() = _joke

    fun updateValue() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        GlobalScope.launch(Dispatchers.IO){
                val response = api.getJokes().awaitResponse()
                if(response.isSuccessful) {
                    val data = response.body()!!

                    withContext(Dispatchers.Main) {
                        _joke.value = data.value
                    }
                }
        }

    }
}