package com.example.tp1sharedpreferences_vargasgabriel.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.tp1sharedpreferences_vargasgabriel.model.Usuario;

public class ApiClient {
    public static SharedPreferences sp;

    private static SharedPreferences conectar(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("datos", 0);
        }
        return sp;
    }

    public static void guardar(Context context, Usuario usuario) {

        SharedPreferences sp = conectar(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("dni", usuario.getDni());
        editor.putString("apellido", usuario.getApellido());
        editor.putString("nombre", usuario.getNombre());
        editor.putString("mail", usuario.getMail());
        editor.putString("password", usuario.getPassword());
        editor.commit();

    }

    public static Usuario leer(Context context) {

        SharedPreferences sp = conectar(context);
        Long dni = sp.getLong("dni", -1);
        String apellido = sp.getString("apellido", "-1");
        String nombre = sp.getString("nombre", "-1");
        String email = sp.getString("mail", "-1");
        String pass = sp.getString("password", "-1");

        Usuario usuario = new Usuario(dni, apellido, nombre, email, pass);
        return usuario;
    }

    public static Usuario login(Context context, String mail, String password) {
        Usuario usuario = null;
        SharedPreferences sp = conectar(context);
        Log.d("context: ",""+context);
        Long dni = sp.getLong("dni", -1);
        String apellido = sp.getString("apellido", "-1");
        String nombre = sp.getString("nombre", "-1");
        String email = sp.getString("mail", "-1");
        String pass = sp.getString("password", "-1");
        Log.d("lugar ","estoy en login");
        if (mail.equals(email) && password.equals(pass)) {

            usuario = new Usuario(dni, apellido, nombre, email, pass);
        }
        return usuario;

    }
}
