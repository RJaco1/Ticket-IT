package com.ticket_it.ticketit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Turno extends AppCompatActivity {

    private String TURNO_ID = "turnoId", TAG = "myTag";
    private String turnoId;

    private TextView txEntidad, txServicio, txTurno, txEstado;

    private CollectionReference turnoDoc = FirebaseFirestore.getInstance().collection("Turno/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turno);

        turnoId = getIntent().getExtras().getString(TURNO_ID);

        txEntidad = findViewById(R.id.txtEntidad);
        txServicio = findViewById(R.id.txtServicio);
        txTurno = findViewById(R.id.txtTurn);
        txEstado = findViewById(R.id.txtEstado);

        turnoDoc.document(turnoId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    TurnoDocument td = documentSnapshot.toObject(TurnoDocument.class);
                    mostrarDatos(td);
                } else {
                    Intent intent = new Intent(Turno.this, MenuPrincipal.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void cancelarTurno(View v) {

    }

    public void mostrarDatos(TurnoDocument td) {
        StringBuilder sb = new StringBuilder();
        String turno = String.valueOf(td.getTurnoNumero());
        if (turno.length() < 6) {
            int dif = 6 - turno.length();
            for (int i = 0; i < dif; i++) {
                sb = sb.append("0");
            }
        }
        turno = sb.toString() + turno;

        txEntidad.setText(td.getNombreEntidad());
        txServicio.setText("Servicio solicitado: " + td.getServicio());
        txTurno.setText("Turno: " + turno);
    }
}