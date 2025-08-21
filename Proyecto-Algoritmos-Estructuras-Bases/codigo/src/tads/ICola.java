package tads;

public interface ICola<T extends Comparable<T>>{

    public void encolar(T dato);

    public T desencolar();

    public boolean esVacia();

    public boolean esllena();

    public int cantElementos();

    public Nodo frente();

    public void mostrar();
    
    public Nodo<T> obtenerPorIndice(int dato);
}
