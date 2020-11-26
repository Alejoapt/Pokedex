package com.example.pokedex.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.R;
import com.example.pokedex.model.User;

public class PokemonActivity extends AppCompatActivity {

    private Button releaseBtn;
    private ImageView imagePokemon;
    private User myUser;
    private TextView defenseLbl;
    private TextView attackLbl;
    private TextView speedLbl;
    private TextView healthLbl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        defenseLbl = findViewById(R.id.defense_lbl);
        attackLbl = findViewById(R.id.attack_lbl);
        speedLbl = findViewById(R.id.speed_lbl);
        healthLbl = findViewById(R.id.healt_lbl);


        myUser = (User) getIntent().getExtras().getSerializable("myUser");
        releaseBtn = findViewById(R.id.release_btn);
        imagePokemon = findViewById(R.id.imagePokemon);
    }
}
