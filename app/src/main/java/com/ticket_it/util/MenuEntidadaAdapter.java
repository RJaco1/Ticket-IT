package com.ticket_it.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ticket_it.ticketit.EntidadColletion;
import com.ticket_it.ticketit.R;

import java.util.List;

public class MenuEntidadaAdapter extends RecyclerView.Adapter<MenuEntidadaAdapter.EntidadViewHolder> {
    private List<EntidadColletion> lista;
    private Context context;
    private int resource;
    private OnItemClickListener listener;

    public MenuEntidadaAdapter(List<EntidadColletion> lista, Context context, int resource) {
        this.lista = lista;
        this.context = context;
        this.resource = resource;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public EntidadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new EntidadViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EntidadViewHolder holder, int position) {
        holder.txtEntidad.setText(lista.get(position).getEntidad());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class EntidadViewHolder extends RecyclerView.ViewHolder {

        public TextView txtEntidad;

        public EntidadViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txtEntidad = itemView.findViewById(R.id.txt1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
