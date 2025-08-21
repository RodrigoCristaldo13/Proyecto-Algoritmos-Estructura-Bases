package tads;

//para comparar elementos entre si, se almacena cualquier tipo de dato si implementa la interfaz Comparable
public class Nodo<T extends Comparable<T>> {

    private T dato;
    private Nodo<T> siguiente;

    //mi ctor de la clase Nodo:
    public Nodo(T dato, Nodo<T> siguiente) {
        this.dato = dato;
        this.siguiente = siguiente;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

}
