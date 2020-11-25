package com.example.pokedex.events;

import com.example.pokedex.model.User;

import java.util.ArrayList;


public interface OnUserListListener {
    void onGetUsers(ArrayList<User> userList);
}
