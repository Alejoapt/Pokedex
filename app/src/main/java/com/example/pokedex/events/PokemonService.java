package com.example.pokedex.events;


import com.example.pokedex.model.PokemonAnswer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonService {

    @GET("pokemon")
    Call<PokemonAnswer> obtainPokemonList(@Query("limit") int limit, @Query("offset") int offset);



}

