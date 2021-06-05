package com.ticket_it.ticketit;

import java.io.Serializable;
import java.util.List;

public class EntidadColletion implements Serializable {

    private String entidad, telefono, tipoEntidad, website, entidadId;
    private List<String> servicios;

    public EntidadColletion() {
    }

    public EntidadColletion(String entidadId) {
        this.entidadId = entidadId;
    }


    public EntidadColletion(String entidad, String telefono, String tipoEntidad, String website, String entidadId, List<String> servicios) {
        this.entidad = entidad;
        this.telefono = telefono;
        this.tipoEntidad = tipoEntidad;
        this.website = website;
        this.entidadId = entidadId;
        this.servicios = servicios;
    }

    public String getEntidad() {
        return entidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public String getWebsite() {
        return website;
    }

    public String getEntidadId() {
        return entidadId;
    }

    public List<String> getServicios() {
        return servicios;
    }
}
