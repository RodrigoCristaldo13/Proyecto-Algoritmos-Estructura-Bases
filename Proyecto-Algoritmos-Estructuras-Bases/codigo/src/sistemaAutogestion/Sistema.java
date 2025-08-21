package sistemaAutogestion;

import dominio.Calificacion;
import dominio.Cliente;
import dominio.Entrada;
import dominio.Evento;
import dominio.Sala;
import java.time.LocalDate;
import tads.Cola;
import tads.ListaSimple;

//Aca se implementa la interfaz con los metodos
public class Sistema implements IObligatorio {

    //Listas y colas privadas:
    private ListaSimple<Cliente> listaClientes;
    private ListaSimple<Evento> listaEventos;
    private ListaSimple<Sala> listaSalas;
    private ListaSimple<Entrada> historialGlobalDeVentas;

    // Constructor Sistema inicializa las listas:
    public Sistema() {
        listaClientes = new ListaSimple<>();
        listaEventos = new ListaSimple<>();
        listaSalas = new ListaSimple<>();
        historialGlobalDeVentas = new ListaSimple<>();
    }

    public static void main(String[] args) {
        Sistema Obligatorio2025 = new Sistema();
    }


    /*
    1.1)
    - PRECONDICIONES:
        1. No debe existir otro sistema de gestión previamente inicializado.
    - POSTCONDICIONES: 
        1. Se crea una instancia del sistema con las siguientes listas:
        2. Devuelve un objeto 'Retorno' con el resultado 'ok()' si se inicia correctamente el sistema.
     */
    @Override
    public Retorno crearSistemaDeGestion() {
        listaClientes = new ListaSimple<>();
        listaEventos = new ListaSimple<>();
        listaSalas = new ListaSimple<>();
        return Retorno.ok("Se pudo inicializar el sistema correctamente");
    }

    /*
    1.2)
    - PRECONDICIONES:
        1. El metodo debe recibir 2 parametros (String nombre, int capacidad)
        2. El sistema debe estar inicializado
    - POSTCONDICIONES: 
        1. Devuelve un objeto 'Retorno' con el resultado ok() si se agrega una nueva sala con éxito.
        2. Si ya existe en el sistema una sala con el mismo nombre, devuelve un objeto 'Retorno' con el resultado 'error1()' y no se agrega la sala
        3. Si la capacidad es menor o igual a 0, devuelve un objeto 'Retorno' con el resultado 'error2()' y no se agrega la sala.
        
     */
    @Override
    public Retorno registrarSala(String nombre, int capacidad) {
        if (listaSalas.existeElemento(new Sala(nombre, 0))) {
            return Retorno.error1();
        }
        if (capacidad <= 0) {
            return Retorno.error2();
        }
        Sala nuevaSala = new Sala(nombre, capacidad);
        listaSalas.agregarInicio(nuevaSala);
        return Retorno.ok();

    }

    /*
    1.3)
    - PRECONDICIONES:
        1. El metodo debe recibir un parametro (String nombre).
        2. El sistema debe estar inicializado.
    - POSTCONDICIONES: 
        1. Si se encuentra la sala en el sistema, se elimina de la lista.
        2. Devuelve un objeto 'Retorno' con el resultado 'ok()' si se elimina la sala exitosamente.
        3. Si no existe una sala con ese nombre en el sistema, devuelve un objeto 'Retorno' con el resultado 'error1()'.
     */
    @Override
    public Retorno eliminarSala(String nombre) {
        Sala actualSala = listaSalas.obtenerElemento(new Sala(nombre, 0));
        if (actualSala == null) {
            return Retorno.error1();
        }
        listaSalas.borrarElemento(actualSala);
        return Retorno.ok();
    }

    /*
    1.4)
    - PRECONDICIONES:
        1. El metodo debe recibir 4 parametros (String codigo, String descripcion, int aforoNecesario, LocalDate fecha).
        2. El sistema debe estar inicializado.
    - POSTCONDICIONES: 
        1. Si el evento tiene los parametros validos, no existe otro con el mismo codigo, el aforo es valido y existe una sala disponible, se agrega el evento a la lista de eventos
        2. Devuelve un objeto 'Retorno' con el resultado 'ok()' si se agrega el evento con exito.
        3. Si ya existe un evento con el mismo codigo, devuelve un objeto 'Retorno' con el resultado 'error1()' y no se agrega.
        4. Si el aforo necesario es menor o igual a 0, devuelve un objeto 'Retorno' con el resultado 'error2()' y no se agrega.
        5. Si no hay un salas disponibles para la fecha del evento con aforo suficiente, devuelve un objero 'Retorno' con el resultado 'error3()' y no se agrega.
     */
    @Override
    public Retorno registrarEvento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha) {

        // Verificar si el evento ya existe recorriendo la lista de eventos sin usar Nodo directamente
        for (int i = 0; i < listaEventos.getCantidad(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i).getDato(); // Suponiendo que obtenerPorIndice devuelve un Nodo<T>
            if (evento.getCodigo().equals(codigo)) {
                return Retorno.error1(); // evento ya existe
            }
        }

        if (aforoNecesario <= 0) { // Verificamos que el aforo sea positivo
            return Retorno.error2();
        }

        // Buscar sala disponible sin usar Nodo directamente
        Sala salaAsignada = null;
        int menorCapacidadPosible = Integer.MAX_VALUE;

        for (int i = 0; i < listaSalas.getCantidad(); i++) {
            Sala sala = listaSalas.obtenerPorIndice(i).getDato();
            if (sala.getCapacidad() >= aforoNecesario && sala.getCapacidad() < menorCapacidadPosible && salaDisponible(sala, fecha)) {
                salaAsignada = sala;
                menorCapacidadPosible = sala.getCapacidad();

            }
        }

        if (salaAsignada == null) { // Si no encontró sala
            return Retorno.error3();
        }
        //System.out.println("Sala asignada: " + salaAsignada.getNombre());

        Evento nuevoEvento = new Evento(codigo, descripcion, aforoNecesario, fecha);
        nuevoEvento.setSalaAsignada(salaAsignada); // ahora que ya existe el setSalaAsignada
        listaEventos.agregarFinal(nuevoEvento);

        return Retorno.ok();
    }

    /*
    1.5)
    - PRECONDICIONES:
        1. El metodo debe recibir 2 parámetros (String ceula, String nombre)
        2. El sistema debe estar inicializado
    - POSTCONDICIONES: 
        1. Si el cliente tiene los parametros validos, la cedula con el formato correcto, al igual que su nombre. Se agrega a la lista de clientes del sistema.
        2. Devuelve un objeto 'Retorno' con el resultado 'ok()' si se agrega un cliente con exito.
        3. Si la cedula presenta un formato invalido distinto a 8 digitos, devuelve un objeto 'Retorno' con el resultado 'error1()' y no se agrega.
        4. Si ya existe un cliente registrado en el sistema con esa cedula, devuelve un objeto 'Retorno' con el resultado 'error2()' y no se agrega.
     */
    @Override
    public Retorno registrarCliente(String cedula, String nombre) {
        if (cedula == null || cedula.length() != 8) {
            return Retorno.error1();
        }
        if (listaClientes.existeElemento(new Cliente(cedula, ""))) {
            return Retorno.error2();
        }
        Cliente nuevoCliente = new Cliente(cedula, nombre);
        listaClientes.agregarFinal(nuevoCliente);
        return Retorno.ok();

    }

    /* no se puede pasar parametro fecha aca  no esa en la letra*/
 /*
    1.6)
    -PRECONDICIONES:
    1.cedula no debe ser null ni vacío: el método asume que recibe una cédula válida como parámetro.
    2.codigoEvento no debe ser null ni vacío: el método asume que recibe un código válido como parámetro.
    3.listaClientes y listaEventos deben estar inicializadas (no ser null).
    4.La estructura de datos de listaClientes y listaEventos debe ser capaz de encontrar un cliente o evento por igualdad, es decir, el método equals de Cliente y Evento debe estar bien implementado.
    
    -POSTCONDICIONES:
    1.Si hay Retorno.ok():
    Si el cliente y el evento existen:
    Se habrá intentado la compra de una entrada.
    2.Si el cliente no existe:
    El método retorna Retorno.error1() y no realiza ninguna otra acción.
    3.Si el evento no existe:
    El método retorna Retorno.error2() y no realiza ninguna otra acción.
     */
    @Override
    public Retorno comprarEntrada(String cedula, String codigoEvento) {

        Cliente cliente = listaClientes.obtenerElemento(new Cliente(cedula, ""));
        if (cliente == null) {
            return Retorno.error1(); // Cliente no existe
        }

        Evento evento = listaEventos.obtenerElemento(new Evento(codigoEvento, "", 0, LocalDate.now()));
        if (evento == null) {
            return Retorno.error2(); // Evento no existe
        }

        Entrada entrada = evento.ComprarEntrada(cliente);

        if (entrada != null) {
            agregarEntradaAlHistorialDelCliente(cliente, entrada);
            historialGlobalDeVentas.agregarFinal(entrada);
        }
        return Retorno.ok();
    }

    private void agregarEntradaAlHistorialDelCliente(Cliente cliente, Entrada entrada) {
        cliente.getHistorialEntradas().agregarFinal(entrada);
    }

    /*
1.7)
    -PRECONDICIONES:
    1.codigo no debe ser null ni vacío → el método asume que se recibe un código válido.
    2.listaEventos debe estar inicializada (no null).
    3.El método equals de Evento debe estar correctamente implementado para permitir la búsqueda del evento por su código.
    4.Si el evento tiene sala asignada, el objeto Sala también debe estar correctamente inicializado.

    -POSTCONDICIONES:
    1.Si el evento con el código indicado no existe, el método retorna Retorno.error1() y no realiza ninguna otra acción.
    2.Si el evento tiene entradas vendidas, el método retorna Retorno.error2() y no realiza ninguna otra acción (el evento permanece en el sistema).
    3.Si el evento existe y no tiene entradas vendidas:
    El evento se elimina de listaEventos.
    Si el evento tenía sala asignada, la sala se marca como no ocupada (setOcupada(false)).
    El método retorna Retorno.ok().

     */
    @Override
    public Retorno eliminarEvento(String codigo
    ) {

        Evento evento = listaEventos.obtenerElemento(new Evento(codigo, "", 0, LocalDate.now()));
        if (evento == null) {
            return Retorno.error1(); // Evento no existe
        }

        if (evento.hayEntradasVendidas()) {
            return Retorno.error2();
        }

        Sala sala = evento.getSalaAsignada();

        if (sala != null) {
            sala.setOcupada(false);
        }

        listaEventos.borrarElemento(evento);
        return Retorno.ok();

    }

    @Override
    public Retorno eliminarCliente(String cedula) {
        if (cedula == null || cedula.length() != 8) {
            return Retorno.error1();
        }
        Cliente clienteNuevo = listaClientes.obtenerElemento(new Cliente(cedula, ""));

        if (clienteNuevo == null) {
            return Retorno.error2(); //el cliente no existe
        }
        if (!clienteNuevo.getHistorialEntradas().esVacia()) {
            return Retorno.error3(); //si tiene entradas asociadas
        }
        listaClientes.borrarElemento(clienteNuevo);
        return Retorno.ok();
    }

    /*
    1.8)
    PRECONDICIONES:
    1.cedula no debe ser null ni una cadena vacía: el método asume que se recibe una cédula válida.
    2.codigoEvento no debe ser null ni una cadena vacía: se espera un código de evento válido.
    3.listaClientes y listaEventos deben estar inicializadas (no null).
    4.La clase Cliente y Evento deben tener correctamente implementado el método equals para que obtenerElemento funcione.
    5.El método evento.devolverEntrada(String cedula) debe estar implementado y ser capaz de gestionar la devolución de la entrada (por ejemplo, moverla a la lista de devueltas y liberar lugar).
    
    POSTCONDICIONES:
    1.Si el cliente no existe en listaClientes:
    El método retorna Retorno.error1().
    No realiza ningún cambio en los eventos ni en las entradas.
    2.Si el evento no existe en listaEventos:
    El método retorna Retorno.error2().
    No realiza ningún cambio en las entradas.
    3.Si el cliente y el evento existen:
    Se ejecuta evento.devolverEntrada(cedula).
    El método retorna Retorno.ok()
    El estado del evento puede cambiar: la entrada del cliente puede haber pasado a la lista de devueltas y liberado aforo.
     */
    @Override
    public Retorno devolverEntrada(String cedula, String codigoEvento
    ) {

        Cliente cliente = listaClientes.obtenerElemento(new Cliente(cedula, ""));
        if (cliente == null) {
            return Retorno.error1(); // Cliente no existe
        }

        Evento evento = listaEventos.obtenerElemento(new Evento(codigoEvento, "", 0, LocalDate.now()));
        if (evento == null) {
            return Retorno.error2(); // Evento no existe
        }
        evento.devolverEntrada(cedula);
        return Retorno.ok();

    }

    /*
    1.9)
    PRECONDICIONES:
    1.cedula no debe ser null ni estar vacía: se asume que se pasa un valor válido.
    2.codigoEvento no debe ser null ni estar vacío:se asume un código válido.
    3.puntaje debe ser un entero .
    4.listaClientes y listaEventos están inicializadas y no son null.
    5.El método equals de Cliente y Evento funciona correctamente (porque obtenerElemento depende de eso).
    6.El método yaFueCalificadoPor(String cedula) está correctamente implementado en Evento.
    7.El método agregarCalificacion(Calificacion) de Evento está correctamente implementado.
    
    POSTCONDICIONES:
    1.Si el cliente no existe:
    Se retorna Retorno.error1().
    El sistema no cambia (no se agrega calificación, no se altera el evento).
    2.Si el evento no existe:
    Se retorna Retorno.error2().
    El sistema no cambia.
    3.Si el puntaje es menor a 1 o mayor a 10:
    Se retorna Retorno.error3().
    El sistema no cambia.
    4.Si el evento ya fue calificado por ese cliente:
    Se retorna Retorno.error4().
    El sistema no cambia.
    5.Si todo es correcto:
    Se crea una nueva calificación con el cliente, el puntaje y el comentario.
    La calificación es agregada a la lista de calificaciones del evento.
    Se retorna Retorno.ok().
    
     */
    @Override
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {

        Cliente cliente = listaClientes.obtenerElemento(new Cliente(cedula, ""));
        if (cliente == null) {
            return Retorno.error1();
        }

        Evento evento = listaEventos.obtenerElemento(new Evento(codigoEvento, "", 0, LocalDate.now()));
        if (evento == null) {
            return Retorno.error2();
        }

        if (puntaje < 1 || puntaje > 10) {
            return Retorno.error3();
        }

        if (evento.yaFueCalificadoPor(cedula)) {
            return Retorno.error4();
        }
        Calificacion calificacion = new Calificacion(cliente, puntaje, comentario);
        evento.agregarCalificacion(calificacion);

        return Retorno.ok();
    }

    //OPCION SIN HARDCODEO:
    @Override
    public Retorno modificarCalificacion(String cedula, String codigoEvento, int nota) { //int nuevoPuntaje, String nuevoComentario

        if (cedula.length() != 8) {
            return Retorno.error1();
        }
        Cliente clienteNuevo = listaClientes.obtenerElemento(new Cliente(cedula, ""));
        if (clienteNuevo == null) {
            return Retorno.error2();
        }
        Evento nuevoEvento = listaEventos.obtenerElemento(new Evento(codigoEvento, "", 0, LocalDate.now()));

        if (nuevoEvento == null) {
            return Retorno.error3();
        }
        if (nota < 0 || nota > 10) {
            return Retorno.error4(); // Puntaje inválido
        }
        Calificacion calificacion = nuevoEvento.obtenerCalificacionDe(cedula);

        calificacion.setPuntaje(nota);
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        retorno.valorString = calificacion.toString();

        return retorno;
    }

    //para acceder al dato desde el testing:
    public Calificacion obtenerCalificacion(String cedula, String codigoEvento) {
        Evento evento = listaEventos.obtenerElemento(new Evento(codigoEvento, "", 0, LocalDate.now()));
        if (evento != null) {
            return evento.obtenerCalificacionDe(cedula);
        }
        return null;
    }


    /*
    2.1)
    - PRECONDICIONES:
        1. El atributo listaSalas debe estar correctamente inicializado (no debe ser null).
        2. listaSalas debe tener implementado un método listar() que retorne una representación en String de las salas (puede estar vacía, pero el método debe estar definido y operativo).
        3. El sistema debe estar inicializado
    - POSTCONDICIONES: 
        1. Se devuelve un objeto de tipo Retorno con resultado 'ok()'.
        2. El atributo valorString del objeto Retorno contiene el resultado del método listar() aplicado a listaSalas.
        3. Se imprime en consola el valor de valorString.
     */
    @Override
    public Retorno listarSalas() {
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        retorno.valorString = listaSalas.listar();
        System.out.println(retorno.valorString);
        return retorno;

    }

    /*
    2.2)
    - PRECONDICIONES:
        1. listaEventos debe estar correctamente inicializada (no debe ser null).
        2. El método ordenarLista() debe estar implementado y funcional en listaEventos.
        3. El método listar() debe estar implementado y retornar un String representando los eventos (puede estar vacío).
        4. El sistema debe estar inicializado
    - POSTCONDICIONES: 
        1. Se ejecuta el método ordenarLista() sobre listaEventos, por lo que la lista queda ordenada (según el criterio definido internamente).
        2. Se devuelve un objeto de tipo Retorno con: resultado 'ok()', valorString = listaEventos.listar() (el contenido de la lista de eventos ya ordenada)
        3. Se imprime en consola el contenido de valorString.
     */
    @Override
    public Retorno listarEventos() {
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        listaEventos.ordenarLista();
        retorno.valorString = listaEventos.listar();
        System.out.println(retorno.valorString);
        return retorno;
    }

    /*
    2.3)
    - PRECONDICIONES:
        1. listaClientes debe estar inicializada (no debe ser null).
        2. listaClientes debe tener implementado correctamente el método ordenarLista(), que debe dejar la lista en orden según el criterio definido .
        3. listaClientes debe tener implementado el método listar(), que devuelve una cadena String con la representación textual de los clientes (aunque esté vacía).
        4. El sistema debe estar inicializado
    - POSTCONDICIONES: 
        1. La lista de clientes queda ordenada tras la ejecución de ordenarLista().
        2. Se devuelve un objeto retorno con : reultado 'ok()', valorString = listaClientes.listar()  que contiene la lista de clientes ya ordenada en formato String.
        3. Se imprime en consola el contenido de valorString.
     */
    @Override
    public Retorno listarClientes() {
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        listaClientes.ordenarLista();
        retorno.valorString = listaClientes.listar();
        System.out.println(retorno.valorString);
        return retorno;
    }

    /*
    2.4)
    - PRECONDICIONES:
        1. vistaSala debe estar inicializado (no debe ser null).
        2. vistaSala debe ser una matriz cuadrada: todas las filas deben tener la misma cantidad de columnas .
        3. Los valores dentro de la matriz deben ser exclusivamente: "O" (ocupado),"X" (libre),"#" (límite o marcador especial).
        4. El sistema debe estar inicializado
    - POSTCONDICIONES: 
        1.  Se analiza cada columna de la matriz vistaSala. Por cada columna: 
            Se calcula el máximo número de asientos ocupados consecutivos ("O"). Se cuenta la cantidad total de asientos libres ("X").
            Si el máximo de ocupados consecutivos es mayor que la cantidad de libres en esa columna, la columna se considera "óptima".
            resultado = OK y muestra el mensaje  "Es la sala optima?, muestra la sala".
            Si al menos 2 columnas cumplen esta condición: me retorna valorString = "Es optimo", En caso contrario: me retorna valorString = "No es optima"
     */
    @Override
    public Retorno esSalaOptima(String[][] vistaSala
    ) {

        int filas = vistaSala.length;
        int columnas = vistaSala[0].length;
        int columnasOptima = 0;// Contador de columnas óptimas

        for (int c = 0; c < columnas; c++) { // Recorro cada columna
            int ocupadosConsecutivos = 0;
            int libres = 0;
            int ocupadosMaximosConsecutivos = 0;// Me interesa el máximo consecutivo de ocupados

            for (int f = 0; f < filas; f++) { // Recorro cada fila de la columna

                String valor = vistaSala[f][c];
                if (valor.equals("#")) {
                    // No hago nada con los límites
                    continue;

                } else if (valor.equals("O")) {
                    ocupadosConsecutivos++;
                    ocupadosMaximosConsecutivos = Math.max(ocupadosMaximosConsecutivos, ocupadosConsecutivos);

                } else if (valor.equals("X")) {
                    libres++;
                    ocupadosConsecutivos = 0;// Corto la racha de consecutivos
                }
            }
            // Después de recorrer toda la columna:
            if (ocupadosMaximosConsecutivos > libres) {
                columnasOptima++;
            }
        }
        Retorno ret = new Retorno(Retorno.Resultado.OK);
        if (columnasOptima >= 2) {
            ret.valorString = "Es óptimo";
        } else {
            ret.valorString = "No es óptimo";
        }
        System.out.println("Es la sala optima?: " + ret.valorString);
        return ret;
    }

    /*
    2.5)
    - PRECONDICIONES:
        1. El metodo debe recibir dos parametros: un String 'codigo' y un entero 'n'.
        2. El sistema debe estar inicializado correctamente
        3. La lista de eventos debe estar inicializada correctamente
        4. El sistema debe estar inicializado
    - POSTCONDICIONES: 
        1. Se listan hasta los ultimos 'n'n clientes que compraron entradas para el evento.
        2. Si hay menos de 'n' entradas vendidas, se listan todos los clientes disponibles
        3. Se retorna un objeto retorno con resultado 'ok()' y valorString con el formato adecuado.
        4. Si el evento no existe, se retorna un objeto Retorno con el resultado 'error1()'.
        5. Si n < 1 se retorna un objeto retorno con resultado 'error2()'.
     */
    @Override
    public Retorno listarClientesDeEvento(String código, int n
    ) {
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        Evento evento = listaEventos.obtenerElemento(new Evento(código, "", 0, null));

        if (evento == null) {
            return Retorno.error1(); //aca el evento no existe
        }

        if (n < 1) {
            return Retorno.error2(); //numero n menor que 1
        }
        int totalEntradasVendidasDelEvento = evento.getEntradasVendidas().getCantidad();
        int desde = Math.max(0, totalEntradasVendidasDelEvento - n);

        String resultado = "";

        for (int i = desde; i < totalEntradasVendidasDelEvento; i++) {
            Entrada entrada = evento.getEntradasVendidas().obtenerPorIndice(i).getDato();
            Cliente cliente = entrada.getCliente();
            if (cliente != null) {
                if (!resultado.isEmpty()) {
                    resultado += "#";
                }
                resultado += cliente.toString();
            }
        }
        retorno.valorString = resultado;
        System.out.println(retorno.valorString);
        return retorno;
    }

    /*
    2.6)
    - PRECONDICIONES:
        1. Cada evento debe tener una cola de espera inicializada
        2. La lista de eventos debe estar inicializada correctamente
        3. El sistema debe estar inicializado
    - POSTCONDICIONES: 
        1. Se muestran los eventos que tienen clientes en la lista de espera.
        2. Se ordenan los eventos alfabéticamente por su código.
        3. Para cada evento con espera se ordenan los clientes por cédula.
        4. Se genera una cadena con el formato deseado incluyendo solo los eventos con clientes en espera.
        5. Se retorna un objeto Retorno con resultado 'ok()' y valorString con el formato adecuado.
     */
    @Override
    public Retorno listarEsperaEvento() {
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        if (listaEventos.esVacia()) {
            return Retorno.ok();
        }

        ListaSimple<Evento> eventosConEspera = new ListaSimple<>();

        for (int i = 0; i < listaEventos.getCantidad(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i).getDato();
            if (!evento.getListaEspera().esVacia()) {
                eventosConEspera.agregarFinal(evento);
            }
        }
        if (eventosConEspera.esVacia()) {
            return Retorno.ok();
        }
        eventosConEspera.ordenarLista();

        String resultado = "";

        for (int i = 0; i < eventosConEspera.getCantidad(); i++) {
            Evento evento = eventosConEspera.obtenerPorIndice(i).getDato();
            Cola<Cliente> colaEspera = evento.getListaEspera();

            if (!colaEspera.esVacia()) {
                ListaSimple<Cliente> clientesEspera = new ListaSimple<>();

                // Recorremos la cola usando índices y obtenerPorIndice para evitar usar Nodo
                for (int k = 0; k < colaEspera.getCantidad(); k++) {
                    Cliente cliente = colaEspera.obtenerPorIndice(k).getDato();
                    clientesEspera.agregarFinal(cliente);
                }
                clientesEspera.ordenarLista();
                for (int j = 0; j < clientesEspera.getCantidad(); j++) {
                    Cliente cliente = clientesEspera.obtenerPorIndice(j).getDato();

                    if (!resultado.isEmpty()) {
                        resultado += "#"; // solo si ya hay contenido
                    }
                    resultado += evento.getCodigo() + "-" + cliente.getCedula();
                }
            }
        }
        retorno.valorString = resultado;
        System.out.println(retorno.valorString);
        return retorno;
    }

    /*
    2.7)
    - PRECONDICIONES:
        1. n debe ser mayor o igual a 0.
        2. La lista de eventos deber estar inicializada correctamente.
        3. El sistema debe estar inicializado.
    - POSTCONDICIONES: 
        1. Se eliminan hasta n entradas del historial global de ventas.
        2. Las entradas eliminadas se devuelven al evento correspondiente.
        3. Se ordenan las entradas deshechas por código de evento y cédula.
        4. Se genera una cadena con el formato especificado.
        5. Se retorna un objeto Retorno con resultado 'ok()' y la cadena generada en valorString.
        6. Se imprime el valorString en consola.
     */
    @Override
    public Retorno deshacerUtimasCompras(int n
    ) {
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        ListaSimple<Entrada> entradasDeshechas = new ListaSimple<>();

        int contador = 0;

        while (contador < n && !historialGlobalDeVentas.esVacia()) {
            Entrada entrada = historialGlobalDeVentas.borrarFin();
            entradasDeshechas.agregarFinal(entrada);
            contador++;

            Evento evento = listaEventos.obtenerElemento(new Evento(entrada.getCodigoEvento(), "", 0, null));
            if (evento != null) {
                evento.devolverEntrada(entrada.getCliente().getCedula());
            }

        }
        entradasDeshechas.ordenarLista();

        String resultado = "";

        for (int i = 0; i < entradasDeshechas.getCantidad(); i++) {
            Entrada e = entradasDeshechas.obtenerPorIndice(i).getDato();
            resultado += e.getCodigoEvento() + "-" + e.getCliente().getCedula();
            if (i < entradasDeshechas.getCantidad() - 1) {
                resultado += "#";
            }
        }
        retorno.valorString = resultado;
        System.out.println(retorno.valorString);
        return retorno;
    }

    /*
    2.8)
    - PRECONDICIONES:
        1. La lista de eventos deber estar inicializada correctamente.
        2. Cada evento puede o no tener calificaciones registradas.
        3. El sistema debe estar inicializado.
    - POSTCONDICIONES: 
        1. Se listan todos los eventos con el mejor puntaje promedio de calificaciones.
        2. El puntaje se muestra redondeando a un decimal.
        3. Si hay mas de un evento con el mismo puntaje promedio, se listan todos ordenados alfabeticamente por codigo.
        4. Si no hay eventos o ningun evento tiene calificaciones, valorString sera vacio y se retorna 'ok()'.
     */
    @Override
    public Retorno eventoMejorPuntuado() {
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        ListaSimple<Evento> eventos = listaEventos;

        double mejorPuntaje = -1;
        ListaSimple<Evento> mejoresEventos = new ListaSimple<>();

        for (int i = 0; i < eventos.getCantidad(); i++) {
            Evento evento = eventos.obtenerPorIndice(i).getDato();
            double puntajePromedio = evento.getPuntajePromedio();

            if (puntajePromedio > mejorPuntaje) {
                mejorPuntaje = puntajePromedio;
                mejoresEventos = new ListaSimple<>();
                mejoresEventos.agregarFinal(evento);
            } else if (puntajePromedio == mejorPuntaje) {
                mejoresEventos.agregarFinal(evento);
            }
        }
        mejoresEventos.ordenarLista();

        String resultado = "";
        for (int i = 0; i < mejoresEventos.getCantidad(); i++) {
            Evento e = mejoresEventos.obtenerPorIndice(i).getDato();
            double calificacionConDecimal = Math.round(mejorPuntaje * 10) / 10.0;
            resultado += e.getCodigo() + "-" + calificacionConDecimal; //para no mostrar decimales
            if (i < mejoresEventos.getCantidad() - 1) {
                resultado += "#";
            }
        }
        retorno.valorString = resultado;
        //System.out.println(retorno.valorString);
        return retorno;
    }

    @Override
    public Retorno darSalaMayor() {
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        double mayorCapacidad = -1;
        String resultado = "";

        if (listaSalas.esVacia()) {
            return Retorno.error1();
        }
        //aca encuentro la mayor
        for (int i = 0; i < listaSalas.getCantidad(); i++) {
            Sala salaMayor = listaSalas.obtenerPorIndice(i).getDato();
            if (salaMayor.getCapacidad() > mayorCapacidad) {
                mayorCapacidad = salaMayor.getCapacidad();
                resultado = salaMayor.toString();
            } else if (salaMayor.getCapacidad() == mayorCapacidad) {
                resultado += "#" + salaMayor.toString();
            }
        }
        retorno.valorString = resultado;
        //System.out.println(retorno.valorString);
        return retorno;
    }

    @Override
    public Retorno eventosConAsistentesMayoresA(int n) {
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        String resultado = "";
        boolean hayAlMenosUno = false;

        for (int i = 0; i < listaEventos.getCantidad(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i).getDato();
            int asistentes = evento.getEntradasVendidas().getCantidad();

            if (asistentes > n) {
                if (!resultado.isEmpty()) {
                    resultado += "#";
                }
                resultado += evento.toString();
                hayAlMenosUno = true;
            }
        }

        if (!hayAlMenosUno) {
            retorno.valorString = "No hay eventos con mas de " + n + " asistentes";
        } else {
            retorno.valorString = resultado;
        }
        return retorno;
    }

    /*
    2.9)
    - PRECONDICIONES:
        1. La lista de clientes deber estar inicializada correctamente.
        2. El sistema debe estar inicializado.
    - POSTCONDICIONES: 
        1. Si el cliente existe se muestran todas las entradas compradas por el en orden cronologico.
        2. Cada entrada se detalla con el codigo del evento y si fue devuelta (-D) o no (-N).
        3. Si el cliente no existe, se retorna 'error1()'.
        4. Si el cliente existe pero no tiene compras se retorna 'ok()' con valorString vacio.
        5. El formato del strinf sigue un patron definido por la letra del problema.
     */
    @Override
    public Retorno comprasDeCliente(String cedula
    ) {

        Cliente cliente = listaClientes.obtenerElemento(new Cliente(cedula, ""));

        if (cliente == null) {
            return Retorno.error1();
        }

        ListaSimple<Entrada> historialCliente = cliente.getHistorialEntradas();
        Retorno retorno = new Retorno(Retorno.Resultado.OK);

        if (historialCliente == null || historialCliente.getCantidad() == 0) {
            retorno.valorString = "No hay compras";
            return retorno;
        }

        String resultado = "";
        boolean esPrimero = true;

        for (int i = 0; i < historialCliente.getCantidad(); i++) {
            Entrada entrada = historialCliente.obtenerPorIndice(i).getDato();
            Evento evento = listaEventos.obtenerElemento(new Evento(entrada.getCodigoEvento(), "", 0, LocalDate.now()));
            if (evento == null) {
                continue;
            }
            boolean esDevuelta = false;

            // Busco la entrada en la lista de devueltas
            for (int j = 0; j < evento.getEntradasDevueltas().getCantidad(); j++) {
                Entrada eDevuelta = evento.getEntradasDevueltas().obtenerPorIndice(j).getDato();
                if (eDevuelta.equals(entrada)) {  // o comparar algún id único
                    esDevuelta = true;
                    break;
                }
            }
            if (!esPrimero) {
                resultado += "#";
            }

            if (esDevuelta) {
                resultado += entrada.getCodigoEvento() + "-D";
            } else {
                resultado += entrada.getCodigoEvento() + "-N";
            }

            esPrimero = false;
        }

        retorno.valorString = resultado;
        System.out.println(retorno.valorString);
        return retorno;
    }

    /*
    2.10)
    - PRECONDICIONES:
        1. La lista de eventos deber estar inicializada correctamente.
        2. El sistema debe estar inicializado.
        3. 'mes' debe estar en un rango valido.
        4. Cada evento debe tener cargado correctamente su lista de fechas de compra.
    - POSTCONDICIONES: 
        1. Si el parametro 'mes' no esta entre 1 y 12 inclusive, se retorna 'error1()'.
        2. Se recorre cada evento del sistema, todas las fechas de compra asociadas a cada evento, se cuentan cuantas ocurrieron en cada dia del mes indicado.
        3. Se arma un string con el formato indicado en la letra del problema.
     */
    @Override
    public Retorno comprasXDia(int mes
    ) {
        if (mes < 1 || mes > 12) {
            return Retorno.error1();
        }

        int[] comprasPorDia = new int[32];

        for (int i = 0; i < listaEventos.getCantidad(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i).getDato();

            for (int j = 0; j < evento.getFechasDeCompra().getCantidad(); j++) {
                Entrada entrada = evento.getFechasDeCompra().obtenerPorIndice(j).getDato();
                LocalDate fechaCompra = entrada.getFechaEntrada();
                if (fechaCompra.getMonthValue() == mes) {
                    int dia = fechaCompra.getDayOfMonth();
                    comprasPorDia[dia]++;
                }
            }
        }
        String resultado = "";
        boolean esPrimero = true;

        for (int dia = 1; dia <= 31; dia++) {
            if (comprasPorDia[dia] > 0) {
                if (!esPrimero) {
                    resultado += "#";
                }
                resultado += dia + "-" + comprasPorDia[dia];
                esPrimero = false;
            }
        }
        Retorno retorno = new Retorno(Retorno.Resultado.OK);
        retorno.valorString = resultado;
        System.out.println(retorno.valorString);
        return retorno;
    }

    private boolean salaDisponible(Sala sala, LocalDate fecha) {
        for (int i = 0; i < listaEventos.getCantidad(); i++) {
            Evento evento = listaEventos.obtenerPorIndice(i).getDato();
            if (evento.getSalaAsignada() != null
                    && evento.getSalaAsignada().equals(sala)
                    && evento.getFecha().equals(fecha)) {
                return false; // Ya hay un evento en esa sala en esa fecha
            }
        }
        return true; // No hay conflicto
    }

}
