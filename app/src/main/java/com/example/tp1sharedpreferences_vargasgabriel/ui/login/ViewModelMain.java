package com.example.tp1sharedpreferences_vargasgabriel.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tp1sharedpreferences_vargasgabriel.model.Usuario;
import com.example.tp1sharedpreferences_vargasgabriel.request.ApiClient;
import com.example.tp1sharedpreferences_vargasgabriel.ui.registro.RegistroActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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

    public void login(String mail, String password){
        Usuario user = ApiClient.login(context,mail,password);

        if (user == null){
            Toast.makeText(getApplication(),  "Usuario y/o Password son incorrectas", Toast.LENGTH_LONG).show();
        }else {usuario.setValue(user);}
    }

    public void Registrar(){
        Intent i = new Intent(context, RegistroActivity.class);
        i.putExtra("clave", "r"); // entra por login, user y pass valido, mostrar datos
        i.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }


}
