package com.example.pokedex.comm;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.pokedex.events.OnRegisterListener;
import com.example.pokedex.events.OnUserListListener;
import com.example.pokedex.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;



public class Actions {

    private HTTPSWebUtilDomi https;
    private Gson gson;
    private OnRegisterListener onRegisterListener;
    private OnUserListListener onUserListListener;

    //METODO DE SUSCRIPCION AL EVENTO
    public void setOnRegisterListener(OnRegisterListener onRegisterListener){
        this.onRegisterListener = onRegisterListener;
    }
    //METODO DE SUSCRIPCION AL EVENTO
    public void onUserListListener(OnUserListListener onUserListListener){
        this.onUserListListener = onUserListListener;
    }

    public Actions(){
        https = new HTTPSWebUtilDomi();
        gson = new Gson();
    }

    //Se pide el usuario. Si es nulo es porque no existe y se crea. Si ya existia no se crea
    public void registerUserIfNotExists(User user){
        new Thread(
                ()->{
                    String url = "https://facelogprueba.firebaseio.com/trainers/"+user.getUsername()+".json";
                    String response = https.GETrequest(url);
                    //SI EL USUARIO NO EXISTE, LO CREAMOS
                    if(response.equals("null")){
                        https.PUTrequest(url,gson.toJson(user));
                        if(onRegisterListener!=null) onRegisterListener.onRegisterUser(false, user);
                    }else{
                        User currentUser = gson.fromJson(response, User.class);
                        if(onRegisterListener!=null) onRegisterListener.onRegisterUser(true, currentUser);
                    }
                }
        ).start();
    }

    //Conseguir todos los usuarios
    public void getAllUsers() {
        new Thread(
                ()->{
                    String url = "https://facelogprueba.firebaseio.com/trainers.json";
                    String response = https.GETrequest(url);
                    Type type = new TypeToken<HashMap<String, User>>(){}.getType();
                    HashMap<String, User> users = gson.fromJson(response, type);
                    ArrayList<User> output = new ArrayList<>();
                    users.forEach( (key, value) -> output.add(value) );
                    if(onUserListListener!=null) onUserListListener.onGetUsers(output);
                }
        ).start();
    }
}
