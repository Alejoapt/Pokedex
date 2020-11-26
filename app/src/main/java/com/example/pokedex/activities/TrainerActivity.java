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
import java.util.ArrayList;
import java.util.UUID;

import com.example.pokedex.R;
import com.google.firebase.storage.FirebaseStorage;

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
    private Boolean canLoad;
    private int offset;
    private TextView searchLbl;
    private Button searchBtn;
    private TextView catchLbl;
    private Button catchBtn;
    private String path;
    private TextView pokemon_name;
    private Button actionRow;
    private static final String TAG ="POKEMON";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);



        //Configuracion de la lista
        pokemon_name = findViewById(R.id.pokemon_name);
        searchLbl = findViewById(R.id.searchLbl);
        searchBtn = findViewById(R.id.searchBtn);
        catchLbl = findViewById(R.id.catchLbl);
        catchBtn = findViewById(R.id.catchBtn);
        userList = findViewById(R.id.userList);
        actionRow = findViewById(R.id.actionRow);
        pokemonAdapter = new PokemonAdapter(this);
        pokemonAdapter.setListener(this);
        //adapter = new TrainerAdapter();
        userList.setAdapter(pokemonAdapter);
        userList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        userList.setLayoutManager(manager);

        //Recuperar el user de la actividad pasada
       // myUser = (User) getIntent().getExtras().getSerializable("myUser");
        myPokemon = (Pokemon)getIntent().getExtras().getSerializable("myPokemon");
        //Listar usuarios

        db = FirebaseFirestore.getInstance();

        /*
        Query userReference = db.collection("trainers").orderBy("username");
        userReference.get().addOnCompleteListener(
                task -> {
                    adapter.clear();
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        User user = doc.toObject(User.class);
                        adapter.addUser(user);
                    }
                }
        );

         */



        catchBtn.setOnClickListener(
                v->{
                    try {
                        String name = UUID.randomUUID().toString();
                        FileInputStream fis = new FileInputStream(new File(path));
                        storage.getReference().child("pokemons").child(name).putStream(fis).addOnCompleteListener(
                                task -> {
                                    if(task.isSuccessful()){

                                    }
                                }
                        );
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }

                }
        );
        searchBtn.setOnClickListener((v) -> {
            Log.e(">>>","onClick working");
            searchPokemon(searchLbl.getText().toString());
        });



        //Habilitar los clicks a items de la lista
        retofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        canLoad = true;
        offset = 0;

        obtainData();

    }

    public void searchPokemon(String pokemonName){

    }

    public void obtainData(){
        PokemonService service = retofit.create(PokemonService.class);
        Call<PokemonAnswer> pokemonAnswerCall = service.obtainPokemonList(20, offset);
        pokemonAnswerCall.enqueue(new Callback<PokemonAnswer>() {
            @Override
            public void onResponse(Call<PokemonAnswer> call, Response<PokemonAnswer> response) {
                //canLoad = true;
                pokemonAdapter.clear();
                if (response.isSuccessful()){
                    PokemonAnswer pokemonAnswer = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonAnswer.getResults();
                    pokemonAdapter.addPokemon(pokemonList);
                    /*
                    for (int i = 0; i < pokemonList.size(); i++){
                        Pokemon p = pokemonList.get(i);
                        Log.e(TAG, "Pokemon: " + p.getName());
                    }

                     */

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

    //Se activa cuando damos click a un item de la lista
    /*
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //User userClicked = users.get(position);
        Intent intent = new Intent(this, ChatActivity.class);

        intent.putExtra("myUser",this.myUser);
        //intent.putExtra("userClicked",userClicked);

        startActivity(intent);
    }

     */

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                /*
                case R.id.actionRow:
                    Log.e(">>>" , "click on a pokemon");

                    Intent intent = new Intent(this, ProfileActivity.class);
                    intent.putExtra("myUser",this.myUser);
                    startActivity(intent);


                    break;

                 */

            }
    }

    @Override
    public void onUserClick(Pokemon pokemonClicked) {
        Intent intent = new Intent(this, PokemonActivity.class);
        intent.putExtra("myPokemon", this.myPokemon);
        intent.putExtra("pokemonClicked",pokemonClicked);

        //intent.putExtra("userClicked", pokemonClicked);

        startActivity(intent);
    }
}