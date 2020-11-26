package com.example.pokedex.lists.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.R;
import com.example.pokedex.lists.viewmodel.TrainerViewModel;
import com.example.pokedex.model.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<TrainerViewModel>{

    private ArrayList<Pokemon> pokemons;
    private Context context;
    private OnUserClickListener listener;

    public PokemonAdapter(Context context){
        this.context = context;
        pokemons = new ArrayList<>();
    }

    public void clear(){
        pokemons.clear();
        notifyDataSetChanged();
    }

    public void addPokemon(ArrayList<Pokemon> pokemonList) {
        pokemons.addAll(pokemonList);
        notifyDataSetChanged();
    }

    @Override
    public TrainerViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.userrow, parent, false);
        TrainerViewModel trainerViewModel = new TrainerViewModel(view);

        return trainerViewModel;
    }

    @Override
    public void onBindViewHolder(TrainerViewModel holder, int position) {
            Pokemon p = pokemons.get(position);
            holder.getNameRow().setText(p.getName());
            Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png").centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImageRow());
            holder.getActionRow().setOnClickListener(
                    v->{
                        Pokemon pokemon = pokemons.get(position);
                        listener.onUserClick(pokemon);
                    }
            );
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void setListener(OnUserClickListener listener){
        this.listener = listener;
    }

    public interface OnUserClickListener{
        void onUserClick(Pokemon pokemon);

    }
}
