package com.example.pokedex.model;

import java.util.ArrayList;

public class PokemonAnswer {

    private ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getResults(){
        return results;
    }

    public void setResults(ArrayList<Pokemon> results){
        this.results = results;
    }
}
