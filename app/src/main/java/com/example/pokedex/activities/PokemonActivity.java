package com.example.pokedex.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.R;
import com.example.pokedex.comm.HTTPSWebUtilDomi;
import com.example.pokedex.lists.adapter.PokemonAdapter;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonActivity extends AppCompatActivity implements View.OnClickListener{

    private Button releaseBtn;
    private ImageView imagePokemon;
    private TextView pokemonName;
    private TextView defenseLbl;
    private TextView attackLbl;
    private TextView speedLbl;
    private TextView healthLbl;
    private Pokemon myPokemon;
    private FirebaseFirestore db;
    private User myUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        pokemonName = findViewById(R.id.pokemon_name);
        defenseLbl = findViewById(R.id.defense_lbl);
        attackLbl = findViewById(R.id.attack_lbl);
        speedLbl = findViewById(R.id.speed_lbl);
        healthLbl = findViewById(R.id.healt_lbl);
        myUser = (User) getIntent().getExtras().getSerializable("myUser");
        releaseBtn = findViewById(R.id.release_btn);
        imagePokemon = findViewById(R.id.imagePokemon);
        db = FirebaseFirestore.getInstance();
        myPokemon = (Pokemon) getIntent().getExtras().getSerializable("pokemonClicked");


        if (myPokemon != null) {
            pokemonName.setText(myPokemon.getName().toUpperCase());
            Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + myPokemon.getNumber() + ".png").centerCrop()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imagePokemon);

            new Thread(
                    ()->{
                        Log.e(">>>", "Before json: ");

                        Gson gson = new Gson();
                        HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                        String json = utilDomi.GETrequest(myPokemon.getUrl());
                        Pokemon pokemon = gson.fromJson(json, Pokemon.class);

                        runOnUiThread(
                                ()->{
                                    defenseLbl.setText(Integer.toString(pokemon.getStats()[2].getBase_stat()));
                                    attackLbl.setText(Integer.toString(pokemon.getStats()[1].getBase_stat()));
                                    healthLbl.setText(Integer.toString(pokemon.getStats()[0].getBase_stat()));
                                    speedLbl.setText(Integer.toString(pokemon.getStats()[5].getBase_stat()));
                                }
                        );


                    }
            ).start();

        }else{
            Log.e(">>>", "MyPokemon is NUll");

        }

        releaseBtn.setOnClickListener(
                (v)->{
                    db.collection("trainers").document(myUser.getId()).collection("pokemons").document(myPokemon.getId()).delete();
                    onBackPressed();
                }
        );

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}
