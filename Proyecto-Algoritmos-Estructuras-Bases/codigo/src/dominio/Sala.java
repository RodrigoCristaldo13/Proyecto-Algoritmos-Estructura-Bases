package dominio;

public class Sala implements Comparable<Sala> {

    private String nombre;
    private int capacidad;
    private boolean ocupada;

    //ctor
    public Sala(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    //get and set
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public boolean isOcupada() {
        return ocupada;
    }

   public void setOcupada(boolean ocupada){
       this.ocupada=ocupada;
   }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String toString() {
        return "Sala: " + this.getNombre() + "-" + this.getCapacidad();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Sala otra = (Sala) obj;
        return this.nombre.equals(otra.nombre); // BIEN: compara contenido textual
    }

    @Override
    public int compareTo(Sala otraSala) {
        if (this.getCapacidad() < otraSala.getCapacidad()) {
            return -1;//el actual tiene menor capacidad
        } else if (this.getCapacidad() > otraSala.getCapacidad()) {
            return 1;//el actual tiene mayor capacidad
        } else {
            return 0;//son iguales
        }
    }

      
       
           
    }

