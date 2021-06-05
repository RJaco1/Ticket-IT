package com.ticket_it.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ticket_it.ticketit.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    static ImageView iconoEntidad;
    static TextView nombreEntidad;

    public ItemViewHolder(@NonNull View itemView, MenuTipoEntidadAdapter.OnItemClickListener listener) {
        super(itemView);
        iconoEntidad = itemView.findViewById(R.id.entidadIcono);
        nombreEntidad = itemView.findViewById(R.id.txtNombEntidad);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}
