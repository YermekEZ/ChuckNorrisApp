package com.example.guessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guessapp.model.MainViewModel
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

        val model: MainViewModel by viewModels()

        model.joke.observe(this, Observer<String>{joke->
            jokeText.text = joke
            }
        )



        //loadJoke()

        generateJoke.setOnClickListener {
            model.updateValue()
        }
    }

}