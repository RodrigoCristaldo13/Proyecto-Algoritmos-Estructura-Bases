package dominio;

import java.time.LocalDate;
import tads.Cola;
import tads.ListaSimple;

public class Evento implements Comparable<Evento> {

    private String codigo;
    private String descripcion;
    private int aforoNecesario;
    private LocalDate fecha;
    private Sala salaAsignada;

    private ListaSimple<Calificacion> calificaciones;
    private ListaSimple<Entrada> EntradasVendidas;
    private ListaSimple<Entrada> EntradasDevueltas;
    private Cola<Cliente> ListaEspera;
    private ListaSimple<Entrada> fechaDeCompra;

    //ctor
    public Evento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.aforoNecesario = aforoNecesario;
        this.fecha = fecha;
        this.calificaciones = new ListaSimple<Calificacion>();
        this.EntradasVendidas = new ListaSimple<Entrada>();
        this.EntradasDevueltas = new ListaSimple<Entrada>();
        this.ListaEspera = new Cola<Cliente>();
        this.fechaDeCompra = new ListaSimple<Entrada>();
    }

    public ListaSimple<Entrada> getFechasDeCompra() {
        return fechaDeCompra;
    }

    public void setFechasDeCompra(ListaSimple<Entrada> fechasDeCompra) {
        this.fechaDeCompra = fechasDeCompra;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAforoNecesario() {
        return aforoNecesario;
    }

    public void setAforoNecesario(int aforoNecesario) {
        this.aforoNecesario = aforoNecesario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setSalaAsignada(Sala salaAsignada) {
        this.salaAsignada = salaAsignada;
    }

    public Sala getSalaAsignada() {
        return this.salaAsignada;
    }

    public ListaSimple<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(ListaSimple<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public ListaSimple<Entrada> getEntradasVendidas() {
        return EntradasVendidas;
    }

    public void setEntradasVendidas(ListaSimple<Entrada> EntradasVendidas) {
        this.EntradasVendidas = EntradasVendidas;
    }

    public ListaSimple<Entrada> getEntradasDevueltas() {
        return EntradasDevueltas;
    }

    public void setEntradasDevueltas(ListaSimple<Entrada> EntradasDevueltas) {
        this.EntradasDevueltas = EntradasDevueltas;
    }

    public ListaSimple<Entrada> getFechaDeCompra() {
        return fechaDeCompra;
    }

    public void setFechaDeCompra(ListaSimple<Entrada> fechaDeCompra) {
        this.fechaDeCompra = fechaDeCompra;
    }

    public Cola<Cliente> getListaEspera() {
        return ListaEspera;
    }

    public void setListaEspera(Cola<Cliente> ListaEspera) {
        this.ListaEspera = ListaEspera;
    }

    @Override
    public String toString() {
        int totalEntradas = this.aforoNecesario - (this.EntradasVendidas.getCantidad() + this.EntradasDevueltas.getCantidad());
        return this.getCodigo() + "-" + this.getDescripcion() + "-" + this.salaAsignada.getNombre() + "-" + totalEntradas + "-" + this.EntradasVendidas.getCantidad();

    }
//CUC22-Tango Azul-11-20-20#TEC43-Seminario de Tecnología-45-5-67#KAK34-Noche de Rock-45-10-30

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Evento evento = (Evento) obj;
        return this.getCodigo().equals(evento.getCodigo());
    }

    @Override
    public int compareTo(Evento otroEvento) {
        return this.getCodigo().compareTo(otroEvento.getCodigo());
    }

//    ---------------------METODOS SEGUNDA ENTREGA----------------
    public Entrada ComprarEntrada(Cliente cliente) {
        if (!EntradasDevueltas.esVacia()) {

            // Reutilizar entrada devuelta
            Entrada entradaDevuelta = EntradasDevueltas.borrarInicio();
            entradaDevuelta.setCliente(cliente); // Asignar el nuevo cliente
            entradaDevuelta.setFechaEntrada(this.fecha);
            EntradasVendidas.agregarFinal(entradaDevuelta);
            fechaDeCompra.agregarFinal(entradaDevuelta);
            // System.out.println("Entrada reutilizada para el cliente: " + cliente.getNombre());
            return entradaDevuelta;
        } else if (EntradasVendidas.getCantidad() < aforoNecesario) {
            // Vender nueva entrada
            Entrada nuevaEntrada = new Entrada("", this.codigo, cliente, this.fecha);
            EntradasVendidas.agregarFinal(nuevaEntrada);
            fechaDeCompra.agregarFinal(nuevaEntrada);
            //System.out.println("Entrada vendida al cliente: " + cliente.getNombre());
            return nuevaEntrada;
        } else {
            // Aforo completo, agregar a lista de espera
            ListaEspera.encolar(cliente);
            //System.out.println("Aforo completo, el cliente " + cliente.getNombre() + " queda en cola de espera");
            return null;
        }

    }

    public boolean hayEntradasVendidas() {

        if (!EntradasVendidas.esVacia()) {
            return true;
        }
        return false;
    }

    public void devolverEntrada(String cedula) {
        for (int i = 0; i < EntradasVendidas.getCantidad(); i++) {
            Entrada entrada = EntradasVendidas.obtenerPorIndice(i).getDato();

            if (entrada.getCliente() != null && entrada.getCliente().getCedula().equals(cedula)) {
                // Guardamos antes de borrar
                Cliente devuelto = entrada.getCliente();
                Entrada entradaDevuelta = EntradasVendidas.borrarPorIndice(i).getDato();

                //System.out.println("Entrada devuelta por: " + devuelto.getNombre());
                // Guardamos la entrada devuelta para reuso
                EntradasDevueltas.agregarFinal(entradaDevuelta);

                // Si hay alguien en la lista de espera, reasignamos
                if (!ListaEspera.esVacia()) {
                    Cliente nuevo = (Cliente) ListaEspera.desencolar();

                    // Actualizamos la entrada para el nuevo cliente
                    entradaDevuelta.setCliente(nuevo); // si tenés un método así
                    //System.out.println("Entrada reasignada a: " + nuevo.getNombre() + ", que estaba en lista de espera");

                    EntradasVendidas.agregarFinal(entradaDevuelta);
                }

                return;
            }
        }
        // Si no se encontró la entrada
        System.out.println("No se encontró entrada con la cédula: " + cedula);
        System.out.println("Entradas vendidas:");
        for (int i = 0; i < EntradasVendidas.getCantidad(); i++) {
            Cliente c = EntradasVendidas.obtenerPorIndice(i).getDato().getCliente();
            System.out.println("- " + (c != null ? c.getNombre() : "Cliente desconocido"));
        }
    }

    public boolean yaFueCalificadoPor(String cedula) {
        for (int i = 0; i < calificaciones.getCantidad(); i++) {
            Calificacion cal = calificaciones.obtenerPorIndice(i).getDato();
            if (cal.getCliente().getCedula().equals(cedula)) {
                return true;
            }
        }
        return false;
    }

    public void agregarCalificacion(Calificacion cal) {
        calificaciones.agregarFinal(cal);
    }

    public double getPuntajePromedio() {
        if (calificaciones == null || calificaciones.esVacia()) {
            return 0;  // Si no tiene calificaciones, el promedio es 0 (o podés decidir otro valor)
        }
        double suma = 0;
        for (int i = 0; i < calificaciones.getCantidad(); i++) {
            Calificacion cal = calificaciones.obtenerPorIndice(i).getDato();
            suma += cal.getPuntaje();  // Asumo que Calificacion tiene getPuntaje()
        }
        return suma / calificaciones.getCantidad();
    }

    public Calificacion obtenerCalificacionDe(String cedula) {
        for (int i = 0; i < calificaciones.getCantidad(); i++) {
            Calificacion calificacionBuscada = calificaciones.obtenerPorIndice(i).getDato();
            if (calificacionBuscada.getCliente().getCedula().equals(cedula)) {
                return calificacionBuscada;
            }
        }
        return null;
    }
}
