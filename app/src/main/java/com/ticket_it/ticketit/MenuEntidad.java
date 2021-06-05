package com.ticket_it.ticketit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ticket_it.util.MenuEntidadaAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.firebase.firestore.FirebaseFirestore.getInstance;

public class MenuEntidad extends AppCompatActivity {

    private TextView tituloEntidad;
    private RecyclerView rvEntidad;

    private FirebaseFirestore db = getInstance();

    private final String KEY = "entidad", TAG = "myTag", SERVICIO = "servicio", ENTIDAD_COLLECTION = "entidadColleccion";
    private String entidad;
    private List<EntidadColletion> listaEntidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_entidad);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TICKET IT");

        Bundle bundle = getIntent().getExtras();
        entidad = bundle.getString(KEY);
        tituloEntidad = findViewById(R.id.txtTipoEntidad);
        rvEntidad = findViewById(R.id.rvEntidad);
        tituloEntidad.setText(entidad);

        listaEntidad = new ArrayList<>();

        db.collection("Entidad").whereEqualTo("tipoEntidad", entidad).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                EntidadColletion ent = document.toObject(EntidadColletion.class);
                                EntidadColletion e = new EntidadColletion(ent.getEntidad(), ent.getTelefono(), ent.getTipoEntidad(), ent.getWebsite(), document.getId(), ent.getServicios());
                                listaEntidad.add(e);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                crearListaEntidad(listaEntidad);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void crearListaEntidad(List<EntidadColletion> lista) {
        rvEntidad.setLayoutManager(new LinearLayoutManager(this));
        MenuEntidadaAdapter adapter = new MenuEntidadaAdapter(lista, this, R.layout.elemento_menu_entidad);
        rvEntidad.setAdapter(adapter);
        adapter.setOnItemClickListener(new MenuEntidadaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EntidadColletion ent = new EntidadColletion(lista.get(position).getEntidad(),
                        lista.get(position).getTelefono(),
                        lista.get(position).getTipoEntidad(),
                        lista.get(position).getWebsite(),
                        lista.get(position).getEntidadId(),
                        lista.get(position).getServicios());

                crearDialogoServicios(ent);
            }
        });
    }

    public void crearDialogoServicios(EntidadColletion entidadColletion) {
        List<String> servicios = entidadColletion.getServicios();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View popUpView = getLayoutInflater().inflate(R.layout.pop_up_servicios, null);
        ListView lvServicios = popUpView.findViewById(R.id.lvServicios);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, servicios);
        lvServicios.setAdapter(adapter);
        dialogBuilder.setView(popUpView);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setTitle(entidadColletion.getEntidad());
        dialog.show();

        lvServicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String serv = servicios.get(position);
                Intent intent = new Intent(MenuEntidad.this, ReservaTurno.class);
                intent.putExtra(ENTIDAD_COLLECTION, entidadColletion);
                intent.putExtra(SERVICIO, serv);
                startActivity(intent);
                //Toast.makeText(MenuEntidad.this, servicios.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}