/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea;

import java.time.LocalDate;

/**
 *
 * @author Cris
 */
public class POJO {

    private String empleado;
    private String dni;
    private String puesto;
    private String telefono;
    private LocalDate fInicio;
    private LocalDate fFin;
    private boolean evaluador;
    private boolean coordinador;

    public POJO() {
    }

    
    public POJO(String empleado, String dni, String puesto, String telefono, LocalDate fInicio, LocalDate fFin, boolean evaluador, boolean coordinador) {
        this.empleado = empleado;
        this.dni = dni;
        this.puesto = puesto;
        this.telefono = telefono;
        this.fInicio = fInicio;
        this.fFin = fFin;
        this.evaluador = evaluador;
        this.coordinador = coordinador;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getfInicio() {
        return fInicio;
    }

    public void setfInicio(LocalDate fInicio) {
        this.fInicio = fInicio;
    }

    public LocalDate getfFin() {
        return fFin;
    }

    public void setfFin(LocalDate fFin) {
        this.fFin = fFin;
    }

    public boolean isEvaluador() {
        return evaluador;
    }

    public void setEvaluador(boolean evaluador) {
        this.evaluador = evaluador;
    }

    public boolean isCoordinador() {
        return coordinador;
    }

    public void setCoordinador(boolean coordinador) {
        this.coordinador = coordinador;
    }

    @Override
    public String toString() {
        return empleado + "," + dni + "," + puesto + "," + fInicio + "," + fFin + "," + telefono + "," + evaluador + "," + coordinador;
    }

}
