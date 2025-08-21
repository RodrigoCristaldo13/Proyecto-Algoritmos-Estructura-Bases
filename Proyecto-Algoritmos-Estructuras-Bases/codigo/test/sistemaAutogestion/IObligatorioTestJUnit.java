package sistemaAutogestion;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IObligatorioTestJUnit {

    private Sistema sistema;

    @Before
    public void setUp() {
        sistema = new Sistema();
        assertEquals(Retorno.Resultado.OK, sistema.crearSistemaDeGestion().resultado);
    }

    @Test
    public void testJuegoDePrueba2() {
        // 1.2 - Registrar Salas
        assertEquals(Retorno.Resultado.OK, sistema.registrarSala("Sala 1", 10).resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.registrarSala("Sala 1", 10).resultado);
        assertEquals(Retorno.Resultado.ERROR_2, sistema.registrarSala("sala 2", -10).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarSala("Sala 2", 20).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarSala("Sala 3", 30).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarSala("Sala 4", 40).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarSala("Sala 5", 50).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarSala("Sala 6", 50).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarSala("Sala 7", 50).resultado);

        // 2.1 - Listar Salas
        assertEquals(Retorno.Resultado.OK, sistema.listarSalas().resultado);

        // 1.3 - Eliminar Sala
        assertEquals(Retorno.Resultado.OK, sistema.eliminarSala("Sala 4").resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.eliminarSala("Sala 4").resultado);

        // 2.1 - Listar Salas luego de eliminación
        assertEquals(Retorno.Resultado.OK, sistema.listarSalas().resultado);

        // 1.4 - Registrar Evento
        LocalDate fecha = LocalDate.of(2025, 6, 10);
        assertEquals(Retorno.Resultado.OK, sistema.registrarEvento("E001", "Evento A", 3, fecha).resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.registrarEvento("E001", "Evento A", 30, fecha).resultado);
        assertEquals(Retorno.Resultado.ERROR_2, sistema.registrarEvento("E002", "Evento B", -10, fecha).resultado);
        assertEquals(Retorno.Resultado.ERROR_3, sistema.registrarEvento("E003", "Evento C", 3000, fecha).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarEvento("E004", "Evento D", 20, fecha).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarEvento("E005", "Evento E", 30, fecha).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarEvento("E006", "Evento F", 20, fecha).resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarEvento("E007", "Evento G", 20, fecha).resultado);

        // 2.2 - Listar Eventos
        assertEquals(Retorno.Resultado.OK, sistema.listarEventos().resultado);

        // 1.5 - Registrar Cliente
        assertEquals(Retorno.Resultado.OK, sistema.registrarCliente("12345678", "Juan").resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.registrarCliente("123456", "Pedro").resultado);
        assertEquals(Retorno.Resultado.ERROR_2, sistema.registrarCliente("12345678", "Juan").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarCliente("12345123", "Ana").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarCliente("12345456", "Maria").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.registrarCliente("12345789", "Carlos").resultado);

        // 2.3 - Listar Clientes
        assertEquals(Retorno.Resultado.OK, sistema.listarClientes().resultado);

        // 1.6 - Comprar Entrada
        assertEquals(Retorno.Resultado.OK, sistema.comprarEntrada("12345678", "E001").resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.comprarEntrada("12345999", "E001").resultado);
        assertEquals(Retorno.Resultado.ERROR_2, sistema.comprarEntrada("12345678", "E999").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.comprarEntrada("12345123", "E001").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.comprarEntrada("12345456", "E001").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.comprarEntrada("12345789", "E001").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.comprarEntrada("12345123", "E004").resultado);

        // 1.7 - Eliminar Evento
        assertEquals(Retorno.Resultado.OK, sistema.eliminarEvento("E007").resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.eliminarEvento("E999").resultado);
        assertEquals(Retorno.Resultado.ERROR_2, sistema.eliminarEvento("E001").resultado);

        // 1.8 - Devolver Entrada
        assertEquals(Retorno.Resultado.OK, sistema.devolverEntrada("12345678", "E001").resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.devolverEntrada("99999999", "E001").resultado);
        assertEquals(Retorno.Resultado.ERROR_2, sistema.devolverEntrada("12345678", "E999").resultado);

        // 1.9 - Calificar Evento
        assertEquals(Retorno.Resultado.OK, sistema.calificarEvento("12345456", "E001", 8, "Me gusto").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.calificarEvento("12345123", "E001", 8, "Me gusto").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.calificarEvento("12345789", "E001", 8, "Me gusto").resultado);
        assertEquals(Retorno.Resultado.OK, sistema.calificarEvento("12345123", "E004", 8, "Me gusto").resultado);
        assertEquals(Retorno.Resultado.ERROR_1, sistema.calificarEvento("99999999", "E001", 8, "Me gusto").resultado);
        assertEquals(Retorno.Resultado.ERROR_2, sistema.calificarEvento("12345789", "E999", 8, "Me gusto").resultado);
        assertEquals(Retorno.Resultado.ERROR_3, sistema.calificarEvento("12345678", "E001", 20, "Me gusto").resultado);
        assertEquals(Retorno.Resultado.ERROR_4, sistema.calificarEvento("12345123", "E001", 7, "Me gusto").resultado);

    }
}
