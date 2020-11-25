package com.example.pokedex.lists.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.lists.viewmodel.TrainerViewModel;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.User;
import com.example.pokedex.R;

import java.util.ArrayList;

public class TrainerAdapter extends RecyclerView.Adapter<TrainerViewModel>{

    private ArrayList<User> trainers;
    private Context context;
    public TrainerAdapter(){
        //this.context = context;
        trainers = new ArrayList<>();
    }

    public void addUser(User user){
        trainers.add(user);
        notifyDataSetChanged();
    }

    public void clear(){
        trainers.clear();
        notifyDataSetChanged();
    }




    @Override
    public TrainerViewModel onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.userrow, parent, false);
        TrainerViewModel trainerViewModel = new TrainerViewModel(view);

        return trainerViewModel;
    }

    @Override
    public void onBindViewHolder(TrainerViewModel holder, int position) {

        //holder.getNameRow().setText(trainers.get(position).getUsername());



        /*
        Glide.with(context).load("https://pokeapi.co/media/sprites/pokemon/" + p.getNumber() + ".png").centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImageRow());

         */
    }

    @Override
    public int getItemCount() {
        return trainers.size();
    }


}
