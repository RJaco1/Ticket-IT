package com.ticket_it.ticketit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ticket_it.util.HistorialAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.firestore.FirebaseFirestore.getInstance;

public class HistorialTurno extends AppCompatActivity {

    private RecyclerView rvHistorial;

    private String TAG = "myTag";
    private List<TurnoDocument> listaTurno;
    private CollectionReference db = getInstance().collection("Turno/");
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_turno);

        rvHistorial = findViewById(R.id.rcvHistorial);

        listaTurno = new ArrayList<>();

        db.whereEqualTo("usuarioId", auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        TurnoDocument td = document.toObject(TurnoDocument.class);
                        TurnoDocument t = new TurnoDocument(td.getEntidadId(), td.getNombreEntidad(), td.getServicio(), td.getUsuarioId(), document.getId(), td.getTurnoNumero(), td.getFechaTurno());
                        listaTurno.add(t);
                        crearListaTurno(listaTurno);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void crearListaTurno(List<TurnoDocument> lista){
        rvHistorial.setLayoutManager(new LinearLayoutManager(this));
        HistorialAdapter adapter = new HistorialAdapter(lista,this, R.layout.elemento_historial);
        rvHistorial.setAdapter(adapter);
    }
}