package nico.gestionturnos;

import java.util.Date;

public class Turno {
    private String nombre;
    private String apellido;
    private int dni;
    private String obraSocial;
    private Date fecha;

    public Turno(String nombre, String apellido, int dni, String obraSocial, Date fecha) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.obraSocial = obraSocial;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - DNI: " + dni + " - " + fecha;
    }
}
