package com.example.tp1sharedpreferences_vargasgabriel.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tp1sharedpreferences_vargasgabriel.R;
import com.example.tp1sharedpreferences_vargasgabriel.model.Usuario;
import com.example.tp1sharedpreferences_vargasgabriel.ui.registro.RegistroActivity;

public class MainActivity extends AppCompatActivity {
    private Button btlogin, btRegistrarse;
    private EditText etUsuario;
    private EditText password;
    private Usuario user;
    private TextView tvMsjLogin;
    private ViewModelMain viewModelMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configView();
    }

    private void configView(){
        btlogin = findViewById(R.id.login);
        etUsuario = findViewById(R.id.usuario);
        password = findViewById(R.id.password);
        btRegistrarse = findViewById(R.id.btRegistrarse);
        tvMsjLogin = findViewById(R.id.tvMsjLogin);
        viewModelMain = ViewModelProviders.of(this).get(ViewModelMain.class);

        viewModelMain.getUsuario().observe(this, new Observer<Usuario>() {//observador
            @Override
            public void onChanged(Usuario usuario) {
             // para mostrar los campos vacios, cuando hago back (atras) desde el formulario registrar, como muestra el video explicativo
               etUsuario.setText("");
               password.setText("");
            }
        });

        btlogin.setOnClickListener(new View.OnClickListener() { //podria usar este codigo en onChanged, cuando asigno el observador al objeto observado
                                       @Override
                                       public void onClick(View view) {
                                           String mail = etUsuario.getText().toString();
                                           String pass = password.getText().toString();
                                           if(viewModelMain.login(mail, pass)) {
                                               Intent i = new Intent(getApplicationContext(), RegistroActivity.class);
                                               i.putExtra("clave", "l"); // entra por login, user y pass valido, mostrar datos
                                               startActivity(i);
                                           }else {
                                               tvMsjLogin.setText("Usuario y/o Password son incorrectas");
                                               Toast.makeText(getApplicationContext(), "Usuario y/o Password son incorrectas", Toast.LENGTH_LONG).show();
                                                 }
                                       }
                                   });

        btRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(), RegistroActivity.class);
                i.putExtra("clave", "r"); // entra por el botón registrarse, mostrar campos vacios
                startActivity(i);
            }
        });

    }
}
