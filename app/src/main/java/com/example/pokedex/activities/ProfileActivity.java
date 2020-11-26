package com.example.pokedex.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.R;

public class ProfileActivity  extends AppCompatActivity implements View.OnClickListener{

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
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
