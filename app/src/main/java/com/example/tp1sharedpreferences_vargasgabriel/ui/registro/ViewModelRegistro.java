package com.example.tp1sharedpreferences_vargasgabriel.ui.registro;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tp1sharedpreferences_vargasgabriel.model.Usuario;
import com.example.tp1sharedpreferences_vargasgabriel.request.ApiClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewModelRegistro extends AndroidViewModel {
    private MutableLiveData<Usuario> userLiveData;
    private Usuario user;
    private Context context;

    public ViewModelRegistro(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        user = new Usuario();
        userLiveData = new MutableLiveData<Usuario>();
    }

    public LiveData<Usuario> getUser() {
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<Usuario>();
        }

        return userLiveData;
    }

    public void leer() {
        user = ApiClient.leer(context);
        userLiveData.setValue(user);
    }

    public boolean guardar(Long dni, String apellido, String nombre, String email, String pass) {
        boolean guardarOk = false;
        //Log.i( "dni: " ,""+ dni);
        //Pequeña validación, se puede hacer mejor. Intenté hacer una validacion usando TextInputLayout pero se me complico,por eso me quedé con éste
        if (validarDni(dni)) {
              if (!email.equals("") && !pass.equals("")) {
                    if(validarEmail(email)) {
                        if (validarPassWord(pass)){
                        Usuario usuario = new Usuario(dni, apellido, nombre, email, pass);
                        ApiClient.guardar(context, usuario);
                        guardarOk = true;
                    }
               }
            }

        } else guardarOk = false;
        return guardarOk;
    }

    private boolean validarDni(Long dni) {
        String dnis = dni.toString();
        if (dnis.length() != 8) return false;
        int i = 0;
        boolean valido = true;

        while (i < 7 && valido) {
            if (dnis.charAt(i) < '0' || dnis.charAt(i) > '9')
                valido = false;
            else i++;
        }
        return (valido && dnis.charAt(i) >= '0' && dnis.charAt(i) <= '9');
    }

    private boolean validarPassWord(String pass) {
        //String passW = pass.toString();
        return pass.length() >= 8;
    }

    private boolean validarEmail(String email)
    {  // Patrón para validar el email
         Pattern patronEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
         Matcher mather = patronEmail.matcher(email);
		 return mather.find();
     }
}
