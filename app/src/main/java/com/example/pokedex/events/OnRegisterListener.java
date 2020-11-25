package com.example.pokedex.events;


import com.example.pokedex.model.User;

public interface OnRegisterListener {
    void onRegisterUser(boolean wasRegistered, User user);
}
