package com.ticket_it.ticketit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MenuPrincipal extends AppCompatActivity {

    private Intent intent;
    private String TURNO_ID = "turnoId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TICKET IT");
    }

    public void scan(View v) {
        new IntentIntegrator(this)
                .setCaptureActivity(CaptureAct.class)
                .setOrientationLocked(false)
                .setBeepEnabled(false)
                .initiateScan();
    }

    public void crearTurno(View v) {
        intent = new Intent(MenuPrincipal.this, MenuTipoEntidad.class);
        startActivity(intent);
    }

    public void agenda(View v) {
        intent = new Intent(MenuPrincipal.this, AgendaTurno.class);
        startActivity(intent);
    }

    public void historial(View v) {
        intent = new Intent(MenuPrincipal.this, HistorialTurno.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                intent = new Intent(MenuPrincipal.this, Turno.class);
                intent.putExtra(TURNO_ID, result.getContents());
                startActivity(intent);
            }
        }
    }
}