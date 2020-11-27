package com.example.pokedex.model;

import java.io.Serializable;

public class Pokemon implements Serializable {

    private String id;
    private int number;
    private String name;
    private String url;
    private PokemonStats[] stats;

    public Pokemon() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public PokemonStats[] getStats() {
        return stats;
    }

    public void setStats(PokemonStats[] stats) {
        this.stats = stats;
    }


}
