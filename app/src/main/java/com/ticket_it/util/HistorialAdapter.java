package com.ticket_it.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ticket_it.ticketit.R;
import com.ticket_it.ticketit.TurnoDocument;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private List<TurnoDocument> lista;
    private Context context;
    private int resource;

    public HistorialAdapter(List<TurnoDocument> lista, Context context, int resource) {
        this.lista = lista;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new HistorialViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {

        StringBuilder sb = new StringBuilder();
        String turno = String.valueOf(lista.get(position).getTurnoNumero());
        if (turno.length() < 6) {
            int dif = 6 - turno.length();
            for (int i = 0; i < dif; i++) {
                sb = sb.append("0");
            }
        }
        turno = sb.toString() + turno;

        //DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        LocalDateTime dt = LocalDateTime.ofEpochSecond(lista.get(position).getFechaTurno().getSeconds(),lista.get(position).getFechaTurno().getNanoseconds(), ZoneOffset.UTC);
        /*StringBuilder sbFecha = new StringBuilder().append(dt.getDayOfMonth()).append("/").append(dt.getMonthValue()).append("/").append(dt.getYear())
                .append(" ").append(dt.getHour()).append(":").append(dt.getMinute());
        dt.parse(sbFecha, formateador);
        formateador.format(dt);*/

        holder.txtEntidad.setText(lista.get(position).getNombreEntidad());
        holder.txtServicio.setText("Servicio: " + lista.get(position).getServicio());
        holder.txtTurno.setText("Turno: " + turno);
        holder.txtFecha.setText("Fecha: "+ dt.toString());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class HistorialViewHolder extends RecyclerView.ViewHolder {

        public TextView txtEntidad, txtServicio, txtTurno, txtFecha;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEntidad = itemView.findViewById(R.id.txtNombEntidad);
            txtServicio = itemView.findViewById(R.id.txtServicio);
            txtTurno = itemView.findViewById(R.id.txtTurno);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }
}
