package tads;

public class Cola<T extends Comparable<T>> implements ICola<T> {

    private Nodo<T> frente;
    private Nodo<T> fondo;
    private int cantidad;
    private int cantMax;

    //ctor por defecto sin limite
    public Cola() {
        frente = null;
        fondo = null;
        cantidad = 0;
        cantMax = Integer.MAX_VALUE;
    }
// ctor con limite

    public Cola(int cantidadMax) {
        frente = null;
        fondo = null;
        cantidad = 0;
        cantMax = cantidadMax;
    }

    //get and set:
    public Nodo<T> getFrente() {
        return frente;
    }

    public void setFrente(Nodo<T> frente) {
        this.frente = frente;
    }

    public Nodo<T> getFondo() {
        return fondo;
    }

    public void setFondo(Nodo<T> fondo) {
        this.fondo = fondo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantMax() {
        return cantMax;
    }

    public void setCantMax(int cantMax) {
        this.cantMax = cantMax;
    }

    @Override
    public void encolar(T dato) {
        if (this.getCantidad() < this.getCantMax()) {
            Nodo<T> nuevo = new Nodo<>(dato, null);
            if (this.esVacia()) {
                this.frente = nuevo;
                this.fondo = nuevo;
            } else {
                this.fondo.setSiguiente(nuevo);
                this.fondo = nuevo;
            }
            this.cantidad++;
        }
    }

    @Override
    public T desencolar() {

        if (this.esVacia()) {
            return null;
        }
        Nodo<T> nodoFrente = this.frente;
        T dato = nodoFrente.getDato();
        //si hay un solo elemento en la cola
        if (this.frente == this.fondo) {
            this.frente = null;
            this.fondo = null;
        } else {
            //muevo el inicio al siguiente nodo
            this.frente = this.frente.getSiguiente();
        }
        this.cantidad--;
        return dato;
    }

    @Override
    public Nodo<T> obtenerPorIndice(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return null;
        }
        Nodo<T> aux = frente;
        for (int i = 0; i < indice; i++) {
            aux = aux.getSiguiente();
        }
        return aux;
    }

    @Override
    public boolean esVacia() {
        return this.frente == null;
    }

    @Override
    public boolean esllena() {
        return this.cantElementos() == this.cantMax;
    }

    @Override
    public int cantElementos() {
        return this.cantidad;
    }

    @Override
    public Nodo<T> frente() {
        return this.frente;
    }

    @Override
    public void mostrar() {
        Nodo<T> aux = this.frente;
        while (aux != null) {
            System.out.print(" - " + aux.getDato());
            aux = aux.getSiguiente();
        }
    }

}
