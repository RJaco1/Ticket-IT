package com.ticket_it.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ticket_it.ticketit.EntidadItem;
import com.ticket_it.ticketit.R;

import java.util.List;

public class MenuTipoEntidadAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<EntidadItem> elemento;
    private Context context;
    private MenuTipoEntidadAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MenuTipoEntidadAdapter(List<EntidadItem> elemento, Context context) {
        this.elemento = elemento;
        this.context = context;
    }

    public void setOnItemClickListener(MenuTipoEntidadAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_menu_tipo_entidad, parent, false);
        ItemViewHolder holder = new ItemViewHolder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.iconoEntidad.setImageResource(elemento.get(position).getIcono());
        holder.nombreEntidad.setText(elemento.get(position).getEntidad());
    }

    @Override
    public int getItemCount() {
        return elemento.size();
    }
}
