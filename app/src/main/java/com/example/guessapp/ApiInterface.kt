package com.example.guessapp;

import com.example.guessapp.data.RandomJoke;

import retrofit2.Call;
import retrofit2.http.GET;

interface ApiInterface {

    @GET("/jokes/random")
    fun getJokes(): Call<RandomJoke>
}
