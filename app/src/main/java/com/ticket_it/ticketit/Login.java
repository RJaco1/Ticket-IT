package com.ticket_it.ticketit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Login extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtClave;
    private Button btnAcceder, btnCuenta;
    private CheckBox chkGuardar;
    private ProgressDialog progressDialog;
    private String email;
    private String clave;
    private int intentos = 0;

    //Declaramos un objeto FirebaseAuth
    private FirebaseAuth firebaseAuth;

    // la clase "sharePreferences" tiene 2 metodos  "getPreferences y getSharePreferences"
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.txtEmail);
        txtClave = findViewById(R.id.txtClave);
        btnAcceder = findViewById(R.id.btnAcceder);
        btnCuenta = findViewById(R.id.btnCuenta);
        chkGuardar = findViewById(R.id.chk_guardar);

        preferences = getPreferences(MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);


        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtEmail.getText().toString().trim();
                clave = txtClave.getText().toString().trim();
                if (!email.isEmpty() && !clave.isEmpty()) {

                    if (clave.length() >= 6) {

                        acceder();
                    } else {
                        Toast.makeText(Login.this, "La clave debe ser al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
                progressDialog.setMessage("Accediendo a su cuenta...");
                progressDialog.show();
            }
        });

        btnCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Form.class);
                startActivity(intent);
            }
        });
    }

    private void acceder() {
        firebaseAuth.signInWithEmailAndPassword(email, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Bienvenido: " + txtEmail.getText(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Login.this, MenuPrincipal.class);
                    startActivity(intent);

                    SharedPreferences.Editor editor = preferences.edit(); //Este es el objeto de tipo editor que nos permite guardar los datos ingresados por el usuario
                    if (chkGuardar.isChecked()) { // despues de validar el usuario correcto, la app validara si el usuario ha decidido guardar las credenciales, si
                        //si "chkGuardar.isChecked" (si esta checkeado) entonces se guardaran las credenciales de lo contrario el usuario ha decidido ya no guardarlas

                        editor.putString("email", email); // entonces en este editor guarda el valor de usuraio, la variable declara arriba,
                        editor.putString("clave", clave); // y este guarda el valor de clave, en caso no este checkeado
                    } else { // sino entonce
                        editor.putString("email", ""); // se le asignara informacion en blanco
                        editor.putString("clave", ""); // y lo mismo para la clave
                    }
                    editor.putBoolean("guardar", chkGuardar.isChecked()); // independientemente si el ususario marcó el check o no lo haya marcado
                    // entonces se le puede decir que use un boolean  con el nombre guardar, y el valor sera loque tanga el valor de la cajita del "chkGuardar.isChecked"
                    editor.commit(); // y se usa "commit" para guardar los cambios sino no se guardán

                } else {
                    Toast.makeText(Login.this, "createUserWithEmail:failure " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                intentos++; // y la varible "intentos" acumulara los intentos equivocados
                if (intentos == 4)
                    finish(); //hasta que se cumplan los establecidos, para este caso son 4, luego de eso se ejecuta el metodo "finish" y se cierra la app
                progressDialog.dismiss();
            }
        });
    }
}