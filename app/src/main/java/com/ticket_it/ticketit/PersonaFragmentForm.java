package com.ticket_it.ticketit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PersonaFragmentForm extends Fragment {

    public static final String P_NOMBRE = "Nombre", P_APELLIDO = "Apellido", DUI = "DUI", TAG = "MEMSAJE";

    private EditText txtNombre, txtApellido, txtDui, txtEmail, txtPass;
    private Button btn;

    private CollectionReference personaCollection = FirebaseFirestore.getInstance().collection("Persona/");
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private String pNombre, pApellido, dui, email, pass;
    Map<String, Object> datosAGuardar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_persona_form, container, false);
        txtNombre = v.findViewById(R.id.edtNombre);
        txtApellido = v.findViewById(R.id.edtApellido);
        txtDui = v.findViewById(R.id.edtDui);
        txtEmail = v.findViewById(R.id.edtEmail);
        txtPass = v.findViewById(R.id.edtPass);
        btn = v.findViewById(R.id.btnRegistrar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pNombre = txtNombre.getText().toString();
                pApellido = txtApellido.getText().toString();
                dui = txtDui.getText().toString();
                email = txtEmail.getText().toString();
                pass = txtPass.getText().toString();
                crearCuenta();
            }
        });
        return v;
    }

    public void crearCuenta() {

        datosAGuardar = new HashMap<>();
        datosAGuardar.put(P_NOMBRE, pNombre);
        datosAGuardar.put(P_APELLIDO, pApellido);
        datosAGuardar.put(DUI, dui);

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener((AppCompatActivity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            guardarPersonaInfo(auth.getUid());
                            Log.d(TAG, "createUserWithEmailAndPassword:Success ");
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void guardarPersonaInfo(String uId) {
        personaCollection.document(uId)
                .set(datosAGuardar)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Documento guardado!!!!!");
                        } else {
                            Log.w(TAG, "Documento no guardado!!!!!", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            auth.getInstance().signOut();
        }
    }

}