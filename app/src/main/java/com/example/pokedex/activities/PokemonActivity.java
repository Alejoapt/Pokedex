package com.example.pokedex.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.R;
import com.example.pokedex.model.User;

public class PokemonActivity extends AppCompatActivity {

    private Button releaseBtn;
    private ImageView imagePokemon;
    private User myUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);


        myUser = (User) getIntent().getExtras().getSerializable("myUser");
        releaseBtn = findViewById(R.id.release_btn);
        imagePokemon = findViewById(R.id.imagePokemon);
    }
}
