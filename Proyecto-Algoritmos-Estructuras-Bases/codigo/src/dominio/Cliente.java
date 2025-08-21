package dominio;

import java.util.Objects;
import tads.ListaSimple;

public class Cliente implements Comparable<Cliente> {

    private String cedula;
    private String nombre;
    
    private ListaSimple<Entrada> historialEntradas;
    
    public Cliente(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.historialEntradas = new ListaSimple<>(); //idea
    }

    public ListaSimple<Entrada> getHistorialEntradas() {
        return historialEntradas;
    }

    
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cliente cliente = (Cliente) obj;
        return this.getCedula().equals(cliente.getCedula());
    }

    @Override
    public String toString() {
        return this.getCedula() + "-" + this.getNombre();
    }

    @Override
    public int compareTo(Cliente otroCliente) {
        return this.getCedula().compareTo(otroCliente.getCedula());
    }

}
