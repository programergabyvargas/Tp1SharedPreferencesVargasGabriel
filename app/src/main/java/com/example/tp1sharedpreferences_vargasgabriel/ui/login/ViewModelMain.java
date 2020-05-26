package com.example.tp1sharedpreferences_vargasgabriel.ui.login;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tp1sharedpreferences_vargasgabriel.model.Usuario;
import com.example.tp1sharedpreferences_vargasgabriel.request.ApiClient;

public class ViewModelMain extends AndroidViewModel {
    private MutableLiveData<Usuario> usuario;
    private Context context;

    public ViewModelMain(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Usuario> getUsuario(){
        if (usuario == null){
            usuario= new MutableLiveData<Usuario>();
        }
        return usuario;
    }

    public Boolean login(String mail, String password){
        Usuario user = ApiClient.login(context.getApplicationContext(),mail,password);
        Log.d("user", String.valueOf(user));
        Boolean esta=false;
        if (user == null){

            return esta;
        }else if(user != null){
            usuario.setValue(user);
            esta = true;
        }
        return esta;
    }
}
