package dominio;

import java.time.LocalDate;
import tads.Cola;
import tads.ListaSimple;

public class Entrada implements Comparable<Entrada> {

    private String Cedula;
    private String codigoEvento;
    private Cliente Cliente;
    private LocalDate fechaEntrada;

    public Entrada(String Cedula, String codigoEvento, Cliente c, LocalDate fecha) {
        this.Cedula = Cedula;
        this.codigoEvento = codigoEvento;
        this.Cliente = c;
        this.fechaEntrada = fecha;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(String codigoEvento) {
        this.codigoEvento = codigoEvento;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    

    @Override
    public int compareTo(Entrada otraEntrada) {
        int compareToCodigo = this.codigoEvento.compareTo(otraEntrada.getCodigoEvento());
        if(compareToCodigo != 0){
            return compareToCodigo;
        }
        return this.Cedula.compareTo(otraEntrada.getCedula());
        
    }

    public Cliente getCliente() {
        return Cliente;
    }

    public void setCliente(Cliente Cliente) {
        this.Cliente = Cliente;
    }

    @Override
    public String toString() {
        return this.getCodigoEvento() + "-" + this.Cedula;
    }

}
