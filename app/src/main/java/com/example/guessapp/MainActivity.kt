package com.example.guessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val BASE_URL = "https://api.chucknorris.io"

class MainActivity : AppCompatActivity() {

    private lateinit var jokeText : TextView
    private lateinit var generateJoke: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jokeText = findViewById(R.id.jokeTextView)
        generateJoke = findViewById(R.id.generateJokeButton)

        loadJoke()

        generateJoke.setOnClickListener {
            loadJoke()
        }
    }

    private fun loadJoke() {

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        GlobalScope.launch(Dispatchers.IO){
            try {
                val response = api.getJokes().awaitResponse()
                if(response.isSuccessful) {
                    val data = response.body()!!

                    withContext(Dispatchers.Main) {
                        jokeText.text = data.value
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext, "Check your internet connection", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}