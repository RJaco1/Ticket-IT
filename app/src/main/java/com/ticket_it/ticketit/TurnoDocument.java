package com.ticket_it.ticketit;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class TurnoDocument {
    private String entidadId, nombreEntidad, servicio, usuarioId, turnoId;
    private int turnoNumero;
    private Timestamp fechaTurno;

    public TurnoDocument() {
    }

    public TurnoDocument(String entidadId, String nombreEntidad, String servicio, String usuarioId, String turnoId, int turnoNumero, Timestamp fechaTurno) {
        this.entidadId = entidadId;
        this.nombreEntidad = nombreEntidad;
        this.servicio = servicio;
        this.usuarioId = usuarioId;
        this.turnoId = turnoId;
        this.turnoNumero = turnoNumero;
        this.fechaTurno = fechaTurno;
    }

    public String getEntidadId() {
        return entidadId;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public String getServicio() {
        return servicio;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public int getTurnoNumero() {
        return turnoNumero;
    }

    public Timestamp getFechaTurno() {
        return fechaTurno;
    }

    public String getTurnoId() {
        return turnoId;
    }
}
