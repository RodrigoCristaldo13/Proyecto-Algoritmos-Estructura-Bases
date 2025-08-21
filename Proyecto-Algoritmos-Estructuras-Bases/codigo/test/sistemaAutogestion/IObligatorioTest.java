package sistemaAutogestion;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IObligatorioTest {

    private Sistema miSistema;

    public IObligatorioTest() {
    }

    @Before
    public void setUp() {
        miSistema = new Sistema();
    }

    @Test
    public void testCrearSistemaDeGestionOK() {
        Retorno retorno = miSistema.crearSistemaDeGestion();
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void testRegistrarSalaOK() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.registrarSala("Sala 2", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void testRegistrarSalaError1() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void testRegistrarSalaError2() {
        Retorno retorno = miSistema.registrarSala("Sala 1", -20);
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void testEliminarSalaOK() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.eliminarSala("Sala 1");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void testEliminarSalaError1() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.eliminarSala("Sala 2");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void testRegistrarEventoOK() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.registrarEvento("C01", "DESCRIPCION 1", 500, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void testRegistrarEventoError1() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.registrarEvento("C01", "DESCRIPCION 1", 500, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.registrarEvento("C01", "DESCRIPCION 2", 1500, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void testRegistrarEventoError2() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.registrarEvento("C01", "DESCRIPCION 1", -500, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void testRegistrarEventoError3() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sala 2", 400);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "DESCRIPCION 1", 500, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C02", "DESCRIPCION 2", 1000, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.error3().resultado, retorno.resultado);
    }

    @Test
    public void testRegistrarClienteOK() {
        Retorno retorno = miSistema.registrarCliente("12345678", "Rodrigo");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void testRegistrarClienteError1() {
        Retorno retorno = miSistema.registrarCliente("1234567890", "Rodrigo");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void testRegistrarClienteError2() {
        Retorno retorno = miSistema.registrarCliente("12345678", "Rodrigo");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12345678", "Carlos");
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void testListarSalas() {
        Retorno retorno = miSistema.registrarSala("Arbol", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Espejo", 600);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.listarSalas();
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void testListarEventos() {
        Retorno retorno = miSistema.registrarSala("Arbol", 600);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.registrarSala("Flor", 700);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.registrarSala("Espejo", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.registrarEvento("Z01", "Evento 1", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        retorno = miSistema.registrarEvento("A01", "Evento 2", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.listarEventos();
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void testListarClientes() {
        Retorno retorno = miSistema.registrarCliente("22345678", "Rodrigo");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343678", "Laura");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.listarClientes();
        assertEquals(Retorno.ok().resultado, retorno.resultado);

    }

    @Test
    public void testEsSalaOptima() {
        String[][] vistaSala = {
            //ejemplo obligatorio
            {"#", "#", "#", "#", "#", "#", "#"},
            {"#", "#", "X", "X", "X", "X", "#"},
            {"#", "O", "O", "X", "X", "X", "#"},
            {"#", "O", "O", "O", "O", "X", "#"},
            {"#", "O", "O", "X", "O", "O", "#"},
            {"#", "O", "O", "O", "O", "O", "#"},
            {"#", "X", "X", "O", "O", "O", "O"},
            {"#", "X", "X", "O", "O", "O", "X"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "#", "#", "O", "#", "#", "#"},
            {"#", "#", "#", "O", "#", "#", "#"}
        };

        Retorno retorno = miSistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, retorno.resultado);
        assertEquals("Es óptimo", retorno.valorString);

    }

    @Test
    public void testNoEsSalaOptima() {
        String[][] vistaSala = {
            //ejemplo de no optima
            {"#", "#", "#", "#", "#", "#", "#"},
            {"#", "#", "X", "X", "X", "X", "#"},
            {"#", "O", "O", "X", "X", "X", "#"},
            {"#", "O", "O", "O", "O", "X", "#"},
            {"#", "X", "X", "X", "X", "O", "#"},
            {"#", "O", "O", "O", "O", "O", "#"},
            {"#", "X", "X", "O", "O", "O", "O"},
            {"#", "X", "X", "O", "X", "X", "#"},
            {"#", "X", "X", "X", "X", "O", "#"},
            {"#", "O", "X", "O", "X", "X", "#"},
            {"#", "#", "#", "O", "#", "#", "#"},
            {"#", "#", "#", "O", "#", "#", "#"}
        };

        Retorno retorno = miSistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, retorno.resultado);
        assertEquals("No es óptimo", retorno.valorString);

    }
//------------------------------SEGUNDA ENTREGA-----------------------------------

    @Test
    public void TestComprarEntradaOK() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

    }

    @Test
    public void TestComprarEntradaERROR1() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("1254557", "C01");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void TestComprarEntradaERROR2() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C02");
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void TestEliminarEventoOK() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.eliminarEvento("C01");
        assertEquals(Retorno.Resultado.OK, retorno.resultado);
    }

    @Test
    public void TestEliminarEventoERROR1() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.eliminarEvento("C02");
        assertEquals(Retorno.Resultado.ERROR_1, retorno.resultado);
    }

    @Test
    public void TestEliminarEventoERROR2() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.eliminarEvento("C01");
        assertEquals(Retorno.Resultado.ERROR_2, retorno.resultado);
    }

    @Test
    public void TestDevolverEntradaOK() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.devolverEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void TestDevolverEntradaERROR1() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.devolverEntrada("1254557", "C01");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void TestDevolverEntradaERROR2() {
        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.devolverEntrada("12545578", "C02");
        assertEquals(Retorno.error2().resultado, retorno.resultado);

    }

    @Test
    public void TestCalificarEventoOK() {

        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.calificarEvento("12545578", "C01", 8, "Muy bueno el evento");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void TestCalificarEventoERROR1() {

        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.calificarEvento("1254557", "C01", 8, "Muy bueno el evento");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void TestCalificarEventoERROR2() {

        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.calificarEvento("12545578", "C02", 8, "Muy bueno el evento");
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void TestCalificarEventoERROR3() {

        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.calificarEvento("12545578", "C01", 11, "Muy bueno el evento");
        assertEquals(Retorno.error3().resultado, retorno.resultado);
    }

    @Test
    public void TestCalificarEventoERROR4() {

        Retorno retorno = miSistema.registrarSala("Sala 1", 500);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("C01", "Evento 3", 400, LocalDate.of(2025, 5, 3));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "C01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.calificarEvento("12545578", "C01", 8, "Muy bueno el evento");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.calificarEvento("12545578", "C01", 8, "Muy bueno el evento");
        assertEquals(Retorno.error4().resultado, retorno.resultado);
    }

    @Test
    public void listarClientesDeEventoOK() {
        //Registro de salas:
        Retorno retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sol", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de eventos:
        retorno = miSistema.registrarEvento("001", "Evento1", 500, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("002", "Evento2", 750, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de clientes:
        retorno = miSistema.registrarCliente("22345678", "Raul Perez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel Gimenez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343648", "Maxi Roble");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("11515879", "Daniel Juarez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("82363699", "Ivan Urtiga");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("62346585", "Florencia Sanchez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("10843050", "Eugenia Etchenique");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Compra de entradas:
        retorno = miSistema.comprarEntrada("22345678", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343648", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("11515879", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("82363699", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("62346585", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("10843050", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Listar:
        retorno = miSistema.listarClientesDeEvento("001", 10);
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void listarClientesDeEventoError1() {
        //Registro de salas:
        Retorno retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sol", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de eventos:
        retorno = miSistema.registrarEvento("001", "Evento1", 500, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("002", "Evento2", 750, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de clientes:
        retorno = miSistema.registrarCliente("22345678", "Raul Perez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel Gimenez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343648", "Maxi Roble");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("11515879", "Daniel Juarez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("82363699", "Ivan Urtiga");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("62346585", "Florencia Sanchez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("10843050", "Eugenia Etchenique");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Compra de entradas:
        retorno = miSistema.comprarEntrada("22345678", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343648", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("11515879", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("82363699", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("62346585", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("10843050", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Listar:
        retorno = miSistema.listarClientesDeEvento("003", 10);
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void listarClientesDeEventoError2() {
        //Registro de salas:
        Retorno retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sol", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de eventos:
        retorno = miSistema.registrarEvento("001", "Evento1", 500, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("002", "Evento2", 750, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de clientes:
        retorno = miSistema.registrarCliente("22345678", "Raul Perez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel Gimenez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343648", "Maxi Roble");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("11515879", "Daniel Juarez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("82363699", "Ivan Urtiga");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("62346585", "Florencia Sanchez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("10843050", "Eugenia Etchenique");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Compra de entradas:
        retorno = miSistema.comprarEntrada("22345678", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343648", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("11515879", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("82363699", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("62346585", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("10843050", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Listar:
        retorno = miSistema.listarClientesDeEvento("002", -8);
        assertEquals(Retorno.error2().resultado, retorno.resultado);
    }

    @Test
    public void ListaEsperaPorEventoOK() {
        //Registro de salas:
        Retorno retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sol", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de eventos:
        retorno = miSistema.registrarEvento("A01", "Evento1", 1, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("F35", "Evento2", 1, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de clientes:
        retorno = miSistema.registrarCliente("22345678", "Raul Perez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel Gimenez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343648", "Maxi Roble");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343649", "Cliente Prueba");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Compra de entradas:
        retorno = miSistema.comprarEntrada("22345678", "A01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "F35");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343648", "F35");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343649", "A01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Listar:
        retorno = miSistema.listarEsperaEvento();
        assertEquals(Retorno.ok().resultado, retorno.resultado);

    }

    @Test
    public void DeshacerUltimasComprasOK() {

        //Registro de salas:
        Retorno retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sol", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de eventos:
        retorno = miSistema.registrarEvento("001", "Evento1", 200, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("002", "Evento2", 250, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de clientes:
        retorno = miSistema.registrarCliente("22345678", "Raul Perez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel Gimenez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343648", "Maxi Roble");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("11515879", "Daniel Juarez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("82363699", "Ivan Urtiga");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("62346585", "Florencia Sanchez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("10843050", "Eugenia Etchenique");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Compra de entradas:
        retorno = miSistema.comprarEntrada("22345678", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343648", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("11515879", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("62346585", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("82363699", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("10843050", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Listar:
        retorno = miSistema.deshacerUtimasCompras(2);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

    }

    @Test
    public void eventoMejorPuntuadoOK() {

        //Registro de salas:
        Retorno retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sol", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de eventos:
        retorno = miSistema.registrarEvento("A01", "Evento1", 200, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("J18", "Evento2", 250, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de clientes:
        retorno = miSistema.registrarCliente("22345678", "Raul Perez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel Gimenez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343648", "Maxi Roble");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("11515879", "Daniel Juarez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("82363699", "Ivan Urtiga");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("62346585", "Florencia Sanchez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("10843050", "Eugenia Etchenique");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Compra de entradas:
        retorno = miSistema.comprarEntrada("22345678", "A01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "A01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343648", "A01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("11515879", "A01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("82363699", "A01");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("62346585", "J18");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("10843050", "J18");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Calificaciones: 
        retorno = miSistema.calificarEvento("22345678", "A01", 6, "Bueno");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        
        retorno = miSistema.calificarEvento("12545578", "A01", 5, "Medio");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        
        retorno = miSistema.calificarEvento("33343648", "A01", 7, "Re bueno");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        
        retorno = miSistema.calificarEvento("11515879", "A01", 8, "Me gusto");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        
        retorno = miSistema.calificarEvento("82363699", "A01", 9, "Me gusto");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        
        retorno = miSistema.calificarEvento("62346585", "J18", 5, "Me gusto");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
        
        retorno = miSistema.calificarEvento("10843050", "J18", 5, "Me gusto");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.eventoMejorPuntuado();
        assertEquals(Retorno.ok().resultado, retorno.resultado);

    }

    @Test
    public void comprasDeClienteOK() {

        //Registro de salas:
        Retorno retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sol", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de eventos:
        retorno = miSistema.registrarEvento("001", "Evento1", 200, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("002", "Evento2", 250, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de clientes:
        retorno = miSistema.registrarCliente("22345678", "Raul Perez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel Gimenez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343648", "Maxi Roble");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("11515879", "Daniel Juarez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("82363699", "Ivan Urtiga");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("62346585", "Florencia Sanchez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("10843050", "Eugenia Etchenique");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Compra de entradas:
        retorno = miSistema.comprarEntrada("22345678", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343648", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("11515879", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("82363699", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("22345678", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("62346585", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("10843050", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Entradas Devueltas:
        retorno = miSistema.devolverEntrada("22345678", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.devolverEntrada("62346585", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprasDeCliente("22345678");
        assertEquals(Retorno.ok().resultado, retorno.resultado);
    }

    @Test
    public void comprasDeClienteError1() {

        //Registro de salas:
        Retorno retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sol", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de eventos:
        retorno = miSistema.registrarEvento("001", "Evento1", 200, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("002", "Evento2", 250, LocalDate.of(2025, 1, 1));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de clientes:
        retorno = miSistema.registrarCliente("22345678", "Raul Perez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel Gimenez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343648", "Maxi Roble");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("11515879", "Daniel Juarez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("82363699", "Ivan Urtiga");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("62346585", "Florencia Sanchez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("10843050", "Eugenia Etchenique");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Compra de entradas:
        retorno = miSistema.comprarEntrada("22345678", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343648", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("11515879", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("82363699", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("22345678", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("62346585", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("10843050", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Entradas Devueltas:
        retorno = miSistema.devolverEntrada("22345678", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.devolverEntrada("62346585", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprasDeCliente("00000008");
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }

    @Test
    public void comprasXDiaOK() {

        //Registro de salas:
        Retorno retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sol", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de eventos:
        retorno = miSistema.registrarEvento("001", "Evento1", 200, LocalDate.of(2025, 5, 2));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("002", "Evento2", 250, LocalDate.of(2025, 4, 4));
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de clientes:
        retorno = miSistema.registrarCliente("22345678", "Raul Perez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel Gimenez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343648", "Maxi Roble");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("11515879", "Daniel Juarez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("82363699", "Ivan Urtiga");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("62346585", "Florencia Sanchez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("10843050", "Eugenia Etchenique");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Compra de entradas:
        retorno = miSistema.comprarEntrada("22345678", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343648", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("11515879", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("82363699", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("22345678", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("62346585", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("10843050", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprasXDia(5);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

    }

    @Test
    public void comprasXDiaError1() {

        //Registro de salas:
        Retorno retorno = miSistema.registrarSala("Flor", 800);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarSala("Sol", 1000);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de eventos:
        retorno = miSistema.registrarEvento("001", "Evento1", 200, LocalDate.EPOCH);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarEvento("002", "Evento2", 250, LocalDate.EPOCH);
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Registro de clientes:
        retorno = miSistema.registrarCliente("22345678", "Raul Perez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("12545578", "Miguel Gimenez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("33343648", "Maxi Roble");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("11515879", "Daniel Juarez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("82363699", "Ivan Urtiga");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("62346585", "Florencia Sanchez");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.registrarCliente("10843050", "Eugenia Etchenique");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        //Compra de entradas:
        retorno = miSistema.comprarEntrada("22345678", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("12545578", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("33343648", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("11515879", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("82363699", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("22345678", "001");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("62346585", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprarEntrada("10843050", "002");
        assertEquals(Retorno.ok().resultado, retorno.resultado);

        retorno = miSistema.comprasXDia(0);
        assertEquals(Retorno.error1().resultado, retorno.resultado);
    }
}
