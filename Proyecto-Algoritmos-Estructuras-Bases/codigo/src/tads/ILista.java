package tads;

import dominio.Sala;
import java.time.LocalDate;

public interface ILista<T extends Comparable<T>> {
    //Metodos de incio y fin

    public void setInicio(Nodo nodo);

    public Nodo getInicio();

    public Nodo getFin();

    public void setFin(Nodo nodo);

    //Metodos basicos 
    public boolean esVacia();

    public void agregarInicio(T n);

    public void agregarFinal(T n);

    //Metodos de obtener
    public boolean existeElemento(T n);

    public T obtenerElemento(T dato);
    
    
    public Nodo<T> obtenerPorIndice(int dato);
     
    public Nodo<T> borrarPorIndice(int n)  ;
    
    public T borrarElemento(T n);

    public T borrarInicio();

    public T borrarFin();

    public String listar();

    public void ordenarLista();

    //public String listarREC();
}
