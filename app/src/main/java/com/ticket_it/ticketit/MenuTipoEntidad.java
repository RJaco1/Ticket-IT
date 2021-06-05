package com.ticket_it.ticketit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ticket_it.util.MenuTipoEntidadAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuTipoEntidad extends AppCompatActivity {

    private List<EntidadItem> elemento;
    private RecyclerView rcvEnt;

    private String KEY = "entidad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tipo_entidad);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TICKET IT");

        rcvEnt = findViewById(R.id.rcvEntidad);
        elemento = new ArrayList<>();
        elemento = crearLista();

        rcvEnt.setLayoutManager(new LinearLayoutManager(this));
        MenuTipoEntidadAdapter adapter = new MenuTipoEntidadAdapter(elemento, this);
        rcvEnt.setAdapter(adapter);

        adapter.setOnItemClickListener(new MenuTipoEntidadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MenuTipoEntidad.this, MenuEntidad.class);
                intent.putExtra(KEY, elemento.get(position).getEntidad());
                startActivity(intent);
            }
        });
    }

    public List<EntidadItem> crearLista() {
        List<EntidadItem> lista = new ArrayList<>();
        lista.add(new EntidadItem(R.drawable.ic_finanzas, getString(R.string.Instituciones_financieras)));
        lista.add(new EntidadItem(R.drawable.ic_supermercado, getString(R.string.Supermercados)));
        lista.add(new EntidadItem(R.drawable.ic_salud, getString(R.string.Instituciones_de_salud)));
        lista.add(new EntidadItem(R.drawable.ic_gobierno, getString(R.string.Instituciones_de_gobierno)));
        return lista;
    }
}