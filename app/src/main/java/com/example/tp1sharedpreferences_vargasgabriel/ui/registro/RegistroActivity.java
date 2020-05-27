package com.example.tp1sharedpreferences_vargasgabriel.ui.registro;

import android.os.Bundle;
import android.util.Log;
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

public class RegistroActivity extends AppCompatActivity {
  private EditText etDni, etApellido, etNombre, etMail, etPassword;
  private Button btnGuardar;
  private TextView tvMsj;
  private ViewModelRegistro viewModelRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        configView();
    }

    private void configView(){

        etDni = findViewById(R.id.etDni);
        etApellido = findViewById(R.id.etApellido);
        etNombre = findViewById(R.id.etNombre);
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        tvMsj = findViewById(R.id.tvMsj);
        btnGuardar = findViewById(R.id.btnGuardar);
        viewModelRegistro = ViewModelProviders.of(this).get(ViewModelRegistro.class);
        String clave = getIntent().getStringExtra("clave");

        viewModelRegistro.ValidarEntrada(clave);

        final Observer<Usuario> observerUsuario = new Observer<Usuario>() { //Creo el observer
            @Override
            public void onChanged(Usuario usuario) { //@Nullable final Usuario usuario
                etDni.setText(""+ usuario.getDni());
                etApellido.setText(usuario.getApellido());
                etNombre.setText(usuario.getNombre());
                etMail.setText(usuario.getMail());
                etPassword.setText(usuario.getPassword());
                tvMsj.setText("");
            }
        };

        viewModelRegistro.getUser().observe(this, observerUsuario); //asigno,vinculo el observador

        //Guardar los datos, por si el usuario modifica datos
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //guardar objet user en preferencias
                Long dni = Long.valueOf(0);
                if (etDni.getText().toString().length()!= 0){
                    dni = (Long.parseLong(etDni.getText().toString())); //cuando etDni viene vacio tengo error en la validacion con la cadena vacia
                  }
                String apellido = etApellido.getText().toString();
                String nombre = etNombre.getText().toString();
                String mail = etMail.getText().toString();
                String password = etPassword.getText().toString();

               viewModelRegistro.guardar(dni,apellido,nombre,mail,password);
            }
        });

    }

    private void setearFormulario(){ //
        etDni.setText("");
        etApellido.setText("");
        etNombre.setText("");
        etMail.setText("");
        etPassword.setText("");
    }
}
