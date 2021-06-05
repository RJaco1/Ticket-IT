package com.ticket_it.ticketit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservaTurno extends AppCompatActivity {

    private final String SERVICIO = "servicio", ENTIDAD_COLLECTION = "entidadColleccion", P_NOMBRE_ENTIDAD = "nombreEntidad",
            TURNO_NUMERO = "turnoNumero", FECHA_TURNO = "fechaTurno", USUARIO_ID = "usuarioId", ENTIDAD_ID = "entidadId",
            TURNO_ID = "turnoId", TAG = "myTag";
    private String servicio;
    private LocalDateTime fechaHora;
    private EntidadColletion entidadColletion;

    private EditText edtFecha, edtHora;
    private DocumentReference turnoCollection = FirebaseFirestore.getInstance().collection("Turno/").document();
    private Map<String, Object> datosAGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_turno);

        fechaHora = LocalDateTime.now();

        edtFecha = findViewById(R.id.edtFecha);
        edtHora = findViewById(R.id.edtHora);
        TextView txtEntidad = findViewById(R.id.txtEntidad);

        servicio = getIntent().getExtras().getString(SERVICIO);
        entidadColletion = (EntidadColletion) getIntent().getExtras().getSerializable(ENTIDAD_COLLECTION);

        txtEntidad.setText(entidadColletion.getEntidad());

    }

    public void opcionFecha(View v) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate fecha = LocalDate.of(year, (month + 1), dayOfMonth);
                LocalDate hoy = LocalDate.of(fechaHora.getYear(), fechaHora.getMonthValue(), fechaHora.getDayOfMonth());
                if (fecha.isBefore(hoy)) {
                    Toast.makeText(ReservaTurno.this, "Fecha no es válida", Toast.LENGTH_LONG).show();
                } else {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    edtFecha.setText(dateFormatter.format(fecha));
                }
            }
        }, fechaHora.getYear(), (fechaHora.getMonthValue() - 1), fechaHora.getDayOfMonth());
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void opcionHora(View v) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                LocalTime hora = LocalTime.of(hourOfDay, minute);
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
                edtHora.setText(timeFormatter.format(hora));
            }
        }, fechaHora.getHour(), fechaHora.getMinute(), false);
        timePickerDialog.show();
    }

    public void reservarTurno(View v) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        StringBuilder sb = new StringBuilder().append(edtFecha.getText()).append(" ").append(edtHora.getText());
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        fechaHora = LocalDateTime.parse(sb, formateador);

        Date dt = Date.from(fechaHora.atZone(ZoneId.systemDefault()).toInstant());

        Timestamp ts = new Timestamp(dt);

        int numTurno = numeroAleatorio();

        datosAGuardar = new HashMap<>();
        datosAGuardar.put(P_NOMBRE_ENTIDAD, entidadColletion.getEntidad());
        datosAGuardar.put(SERVICIO, servicio);
        datosAGuardar.put(TURNO_NUMERO, numTurno);
        datosAGuardar.put(USUARIO_ID, auth.getCurrentUser().getUid());
        datosAGuardar.put(ENTIDAD_ID, entidadColletion.getEntidadId());
        datosAGuardar.put(FECHA_TURNO, ts);

        turnoCollection.set(datosAGuardar).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Documento guardado!!!!!" + turnoCollection.getId());
                    Intent intent = new Intent(ReservaTurno.this, QrTurno.class);
                    intent.putExtra(TURNO_ID, turnoCollection.getId());
                    startActivity(intent);
                } else {
                    Log.w(TAG, "Documento no guardado!!!!!", task.getException());
                }
            }
        });
    }

    public int numeroAleatorio(){
        return (int) (Math.random() * 100000) +1;
    }
}