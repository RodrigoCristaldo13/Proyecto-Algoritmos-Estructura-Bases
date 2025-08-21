
package dominio;


public class Calificacion implements Comparable<Calificacion>{
    
    private Cliente cliente;
    private int puntaje;
    private String comentario;

    public Calificacion(Cliente cliente, int puntaje, String comentario) {
        this.cliente = cliente;
        this.puntaje = puntaje;
        this.comentario = comentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public int compareTo(Calificacion o) {
        
       return Integer.compare(o.puntaje, this.puntaje);
    }
    
    @Override
    public String toString(){
        return " Puntaje: " + puntaje + ", Comentario: " + comentario;
    }
    
    
}
