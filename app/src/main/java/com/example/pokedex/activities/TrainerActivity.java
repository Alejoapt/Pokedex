package com.example.pokedex.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.comm.HTTPSWebUtilDomi;
import com.example.pokedex.events.PokemonService;
import com.example.pokedex.lists.adapter.PokemonAdapter;
import com.example.pokedex.lists.adapter.TrainerAdapter;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.PokemonAnswer;
import com.example.pokedex.model.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Policy;
import java.util.ArrayList;
import java.util.UUID;

import com.example.pokedex.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TrainerActivity extends AppCompatActivity implements View.OnClickListener, PokemonAdapter.OnUserClickListener{

    private User myUser;
    private Pokemon myPokemon;
    private RecyclerView userList;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private TrainerAdapter adapter;
    private PokemonAdapter pokemonAdapter;
    private Retrofit retofit;
    private int offset;
    private TextView searchLbl;
    private Button searchBtn;
    private TextView catchLbl;
    private Button catchBtn;
    private Pokemon pokemonSearched;
    private static final String TAG ="POKEMON";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        //Configuracion de la lista
        searchLbl = findViewById(R.id.searchLbl);
        searchBtn = findViewById(R.id.searchBtn);
        catchLbl = findViewById(R.id.catchLbl);
        catchBtn = findViewById(R.id.catchBtn);
        userList = findViewById(R.id.userList);
        pokemonAdapter = new PokemonAdapter(this);
        pokemonAdapter.setListener(this);
        userList.setAdapter(pokemonAdapter);
        userList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        userList.setLayoutManager(manager);
        myUser = (User) getIntent().getExtras().getSerializable("myUser");
        myPokemon = (Pokemon)getIntent().getExtras().getSerializable("myPokemon");
        //Listar usuarios
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();

        retofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        offset = 0;

        obtainData();


        Query userReference = db.collection("trainers").document(myUser.getId()).collection("pokemons");
        userReference.get().addOnCompleteListener(
                task -> {
                    pokemonAdapter.clear();
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Pokemon pokemon = doc.toObject(Pokemon.class);
                        pokemonAdapter.addOnePokemon(pokemon);
                    }
                }
        );


        catchBtn.setOnClickListener(
                v->{
                    pokemonAdapter.findByName(catchLbl.getText().toString());
                    Pokemon p = pokemonAdapter.getP();
                    if (p == null){
                        Log.e(">>>", "Pokemon is null ");
                    }else{
                        Log.e(">>>", "Pokemon is not  null ");
                    }
                    Log.e(">>>", "LabelName: " + catchLbl.getText());
                    Log.e(TAG, "Pokemon: " + p.getName());

                    String pokemonUUID = UUID.randomUUID().toString();
                    p.setId(pokemonUUID);
                    db.collection("trainers").document(myUser.getId()).collection("pokemons").document(pokemonUUID).set(p);

                }
        );


        searchBtn.setOnClickListener((v) -> {
            Log.e(">>>","onClick working");
            searchPokemon(searchLbl.getText().toString());
        });
    }

    private void searchPokemon(String pokemonName) {

        Query pokemonReference = db.collection("trainers").document(myUser.getId()).collection("pokemons");
        Query query = pokemonReference.whereEqualTo("name",pokemonName);
        query.get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()){

                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            pokemonSearched = documentSnapshot.toObject(Pokemon.class);
                            Log.e(">>>", "This is the object: " + pokemonSearched.getName());
                            Log.e(">>>", "We are searching: " + documentSnapshot.getData());
                            Intent intent = new Intent(this, PokemonActivity.class);
                            intent.putExtra("pokemonClicked",pokemonSearched);
                            startActivity(intent);
                            break;
                        }
                    }else{
                        Log.e(">>>", "Pokemon doesn´t exist");
                    }
                }
        );

    }

    public void obtainData(){
        PokemonService service = retofit.create(PokemonService.class);
        Call<PokemonAnswer> pokemonAnswerCall = service.obtainPokemonList(50, offset);
        pokemonAnswerCall.enqueue(new Callback<PokemonAnswer>() {
            @Override
            public void onResponse(Call<PokemonAnswer> call, Response<PokemonAnswer> response) {
                pokemonAdapter.clear();
                if (response.isSuccessful()){
                    PokemonAnswer pokemonAnswer = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonAnswer.getResults();
                    pokemonAdapter.addPokemon(pokemonList);
                }else{
                    Log.e(TAG, " on response " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonAnswer> call, Throwable t) {
                //canLoad = true;
                Log.e(TAG, " on failure " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){

            }
    }

    @Override
    public void onUserClick(Pokemon pokemonClicked) {
        Intent intent = new Intent(this, PokemonActivity.class);
        intent.putExtra("myPokemon", this.myPokemon);
        intent.putExtra("myUser", myUser);
        intent.putExtra("pokemonClicked",pokemonClicked);
        startActivity(intent);
    }


}