package com.example.pokedex.model;

public class PokemonStats {

    private int base_stat;


    public PokemonStats() {
    }

    public PokemonStats(int base_stat) {
        this.base_stat = base_stat;
    }

    public int getBase_stat() {
        return base_stat;
    }

    public void setBase_stat(int base_stat) {
        this.base_stat = base_stat;
    }

}