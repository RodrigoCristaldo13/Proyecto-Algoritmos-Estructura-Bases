package tads;

import dominio.Evento;
import dominio.Sala;
import java.time.LocalDate;

public class ListaSimple<T extends Comparable<T>> implements ILista<T> {

    private Nodo inicio;
    private Nodo fin;
    private int cantidad;
    private int tope;

    @Override
    public void setInicio(Nodo nodo) {
        this.inicio = nodo;
    }

    @Override
    public Nodo getInicio() {
        return this.inicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getTope() {
        return tope;
    }

    public void setTope(int tope) {
        this.tope = tope;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public Nodo getFin() {
        return this.fin;
    }

    @Override
    public void setFin(Nodo nodo) {
        this.fin = nodo;
    }

    @Override
    public boolean esVacia() {
        return (this.getInicio() == null);
    }

    @Override
    public void agregarInicio(T n) {
        Nodo<T> nuevo = new Nodo(n, this.getInicio());
        inicio = nuevo;
        cantidad++;
    }

    @Override
    public void agregarFinal(T n) {
        if (esVacia()) {
            agregarInicio(n);
        } else {
            Nodo<T> nuevo = new Nodo(n, null);
            Nodo<T> aux = getInicio();

            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }

            aux.setSiguiente(nuevo);
            fin = nuevo; //ojo
            cantidad++;
        }
    }

    @Override
    public boolean existeElemento(T n) {
        //si necesito comparar varios elementos
        if (this.esVacia()) {
            return false;
        }
        Nodo<T> aux = this.getInicio();
        while (aux != null) {
            if (aux.getDato().equals(n)) {
                return true;
            }
            aux = aux.getSiguiente();
        }
        return false;
    }

    @Override
    public T obtenerElemento(T dato) {
        if (this.esVacia()) {
            return null;
        }
        Nodo<T> aux = this.getInicio();
        while (aux != null && !aux.getDato().equals(dato)) {
            aux = aux.getSiguiente();
        }
        if (aux != null) {
            return aux.getDato();
        }
        return null;
    }
    
    @Override
   public Nodo<T> obtenerPorIndice(int indice) {
    if (indice < 0 || indice >= cantidad) return null;

    Nodo<T> aux = inicio;
    for (int i = 0; i < indice; i++) {
        aux = aux.getSiguiente();
    }

    return aux;
    }

   @Override

   public Nodo<T> borrarPorIndice(int indice) {
    if (indice < 0 || indice >= cantidad) return null;

    if (indice == 0) {
        Nodo<T> borrado = inicio;
        inicio = inicio.getSiguiente();
        cantidad--;
        if (inicio == null) fin = null;
        borrado.setSiguiente(null);
        return borrado;
    }

    Nodo<T> aux = inicio;
    for (int i = 0; i < indice - 1; i++) {
        aux = aux.getSiguiente();
    }

    Nodo<T> aBorrar = aux.getSiguiente();
    aux.setSiguiente(aBorrar.getSiguiente());
    if (aBorrar == fin) {
        fin = aux;
    }

    cantidad--;
    aBorrar.setSiguiente(null);
    return aBorrar;
}

    
    @Override

public T borrarElemento(T n) {
    if (esVacia()) return null;

    // Si es el primer elemento
    if (inicio.getDato().equals(n)) {
        return borrarInicio();
    } else {
        Nodo<T> aux = inicio;
        while (aux.getSiguiente() != null && !aux.getSiguiente().getDato().equals(n)) {
            aux = aux.getSiguiente();
        }

        if (aux.getSiguiente() != null) {
            Nodo<T> aBorrar = aux.getSiguiente();
            T dato = aBorrar.getDato();
            aux.setSiguiente(aBorrar.getSiguiente());

            // Si se borró el último nodo, actualizar fin
            if (aBorrar == fin) {
                fin = aux;
            }

            aBorrar.setSiguiente(null);
            cantidad--;
            return dato;
        }
    }

    return null;  // No se encontró el elemento
}


    @Override
 public T borrarInicio() {
    if (esVacia()) return null;
    
    T dato = (T) inicio.getDato();  // guardar el dato antes de eliminar el nodo
    inicio = inicio.getSiguiente();
    cantidad--;
    
    if (inicio == null) {
        fin = null;
    }
    
    return dato;  // devolvemos el dato eliminado
}


    @Override
   public T borrarFin() {
    if (esVacia()) return null;

    // Solo hay un nodo
    if (inicio.getSiguiente() == null) {
        return borrarInicio();  // ya devuelve el dato
    } else {
        Nodo<T> aux = inicio;
        while (aux.getSiguiente().getSiguiente() != null) {
            aux = aux.getSiguiente();
        }

        T dato = aux.getSiguiente().getDato();  // el dato que vamos a borrar
        aux.setSiguiente(null);
        fin = aux;
        cantidad--;

        return dato;
    }
}


    @Override
    public String listar() {
        Nodo<T> actual = this.getInicio();
        String retorno = "";
        while (actual != null) {
            if (actual.getSiguiente() != null) {
                retorno += actual.getDato().toString() + "#";
//            if(actual.getSiguiente() != null){
//                //agrego salto de linea si no es el ultimo en la lista
//                retorno = retorno + '\n';
//            }
            } else {
                retorno += actual.getDato().toString();
            }
            actual = actual.getSiguiente();
        }
        return retorno;
    }

    @Override
    public void ordenarLista() {
        if (this.esVacia()) {
            return; // No hay nada que ordenar
        }
        Nodo<T> actual = this.getInicio();
        while (actual != null) {
            Nodo<T> siguiente = actual.getSiguiente();
            while (siguiente != null) {
                if (actual.getDato().compareTo(siguiente.getDato()) > 0) {

                    T aux = actual.getDato();
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(aux);
                }
                siguiente = siguiente.getSiguiente();
            }
            actual = actual.getSiguiente();
        }
    }
    
    

//    @Override
//    public String listarREC() {
//        String retorno = "";
//        return listarREC(this.getInicio(), retorno, null); //null para listar todos
//    }
//
//    private String listarREC(Nodo inicio, String retorno, Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}
