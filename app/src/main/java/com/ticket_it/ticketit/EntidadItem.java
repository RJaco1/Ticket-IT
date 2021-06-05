package com.ticket_it.ticketit;

public class EntidadItem {
    private int icono;
    private String entidad;

    public EntidadItem(int icono, String entidad) {
        this.icono = icono;
        this.entidad = entidad;
    }

    public int getIcono() {
        return icono;
    }

    public String getEntidad() {
        return entidad;
    }
}
