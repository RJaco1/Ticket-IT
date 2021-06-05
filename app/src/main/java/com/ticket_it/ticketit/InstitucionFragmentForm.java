package com.ticket_it.ticketit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InstitucionFragmentForm extends Fragment {

    public static final String P_NOMBRE_ENTIDAD = "entidad", WEBSITE = "website",
            TELEFONO = "telefono", ENTIDAD_TIPO = "tipoEntidad", TAG = "MEMSAJE";

    private EditText txtNombEntidad, txtWebsite, txtTelefono, txtEmail, txtPass;
    private Spinner spEntidades;
    private Button btn;

    private CollectionReference entityCollection = FirebaseFirestore.getInstance().collection("Entidad/");
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private String nombEntidad, website, telefono, entidad, email, pass;
    Map<String, Object> datosAGuardar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_institucion_form, container, false);
        txtNombEntidad = v.findViewById(R.id.edtNombEntidad);
        txtWebsite = v.findViewById(R.id.edtWebsite);
        txtTelefono = v.findViewById(R.id.edtTelefono);
        txtEmail = v.findViewById(R.id.edtEmail);
        txtPass = v.findViewById(R.id.edtPass);
        spEntidades = v.findViewById(R.id.spEntidad);
        btn = v.findViewById(R.id.btnRegistrar);

        generarSpinner();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombEntidad = txtNombEntidad.getText().toString();
                website = txtWebsite.getText().toString();
                telefono = txtTelefono.getText().toString();
                email = txtEmail.getText().toString();
                pass = txtEmail.getText().toString();
                crearCuenta();
            }
        });

        spEntidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entidad = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    public void crearCuenta() {
        datosAGuardar = new HashMap<>();
        datosAGuardar.put(P_NOMBRE_ENTIDAD, nombEntidad);
        datosAGuardar.put(WEBSITE, website);
        datosAGuardar.put(TELEFONO, telefono);
        datosAGuardar.put(ENTIDAD_TIPO, entidad);

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener((AppCompatActivity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            guardarEntidadInfo(auth.getUid());
                            Log.d(TAG, "createUserWithEmailAndPassword:Success ");
                        } else {
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void guardarEntidadInfo(String uId) {
        entityCollection.document(uId).set(datosAGuardar).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public void generarSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getContext(), R.array.entidad_spinner, android.R.layout.simple_expandable_list_item_1);
        spEntidades.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            auth.getInstance().signOut();
        }
    }
}