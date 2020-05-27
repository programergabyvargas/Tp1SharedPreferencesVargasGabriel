package com.example.tp1sharedpreferences_vargasgabriel.ui.registro;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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

    //para saber por donde entra, si por login o por registrar
    public void ValidarEntrada(String clave){
        if(clave.equals("l") ) {
            leer(); // si el usuario existe, mostrar sus datos
        }
    }

    public void validarFormu(){

    }

    public void leer() {
        user = ApiClient.leer(context);
        userLiveData.setValue(user);
    }

    public void guardar(Long dni, String apellido, String nombre, String email, String pass) {
        //Pequeña validación, se puede hacer mejor. Intenté hacer una validacion usando TextInputLayout pero se me complico,por eso me quedé con éste
        if (dni.toString().length() <= 0){
            dni = Long.valueOf(0); //cuando etDni viene vacio tengo error en la validacion con la cadena vacia
        }

        if (validarDni(dni)) {
              if (!email.equals("") && !pass.equals("")) {
                    if(validarEmail(email)) {
                        if (validarPassWord(pass)){
                             Usuario usuario = new Usuario(dni, apellido, nombre, email, pass);
                             ApiClient.guardar(context, usuario);
                        }
                     }
               }
              Toast.makeText(getApplication(), "Los datos se han guardado Correctamente", Toast.LENGTH_LONG).show();
         } else  Toast.makeText(getApplication(), "No se pudo guardar los datos", Toast.LENGTH_LONG).show();
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
