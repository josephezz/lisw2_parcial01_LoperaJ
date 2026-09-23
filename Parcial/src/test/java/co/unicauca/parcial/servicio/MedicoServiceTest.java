package co.unicauca.parcial.servicio;

import co.unicauca.parcial.modelo.Medico;
import co.unicauca.parcial.modelo.TipoMedico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//Pruebas unitarias
class MedicoServiceTest {

    private MedicoService service;

    @BeforeEach
    void setUp() {
        FakeMedicoRepository fakeRepo = new FakeMedicoRepository();
        service = new MedicoService(fakeRepo);
    }

    // Crear

    @Test
    void testCrearMedicoExitosamente() {
        Medico medico = new Medico(1, "Juan", "Pérez", TipoMedico.TIPO_1, true);

        boolean resultado = service.crearMedico(medico);

        assertTrue(resultado);
        assertEquals(1, service.listarMedicos().size());
    }

    @Test
    void testCrearMedicoConDatosNulos() {
        assertFalse(service.crearMedico(null));

        Medico sinNombre = new Medico(2, "", "López", TipoMedico.TIPO_2, false);
        assertFalse(service.crearMedico(sinNombre));

        Medico sinApellido = new Medico(3, "Ana", "", TipoMedico.TIPO_3, true);
        assertFalse(service.crearMedico(sinApellido));

        Medico sinTipo = new Medico(4, "Carlos", "García", null, false);
        assertFalse(service.crearMedico(sinTipo));
    }

    @Test
    void testCrearMedicoConIdInvalido() {
        Medico medico = new Medico(0, "Juan", "Pérez", TipoMedico.TIPO_1, true);
        assertFalse(service.crearMedico(medico));

        Medico medicoNeg = new Medico(-1, "Juan", "Pérez", TipoMedico.TIPO_1, true);
        assertFalse(service.crearMedico(medicoNeg));
    }

    // Listar

    @Test
    void testListarMedicos() {
        service.crearMedico(new Medico(1, "Juan", "Pérez", TipoMedico.TIPO_1, true));
        service.crearMedico(new Medico(2, "Ana", "López", TipoMedico.TIPO_2, false));
        service.crearMedico(new Medico(3, "Carlos", "García", TipoMedico.TIPO_3, true));

        List<Medico> lista = service.listarMedicos();

        assertEquals(3, lista.size());
    }

    @Test
    void testListarMedicosVacio() {
        List<Medico> lista = service.listarMedicos();
        assertTrue(lista.isEmpty());
    }

    // Actualizar

    @Test
    void testActualizarTipoYEstado() {
        service.crearMedico(new Medico(1, "Juan", "Pérez", TipoMedico.TIPO_1, true));

        Medico actualizado = new Medico(1, "Juan", "Pérez", TipoMedico.TIPO_3, false);
        boolean resultado = service.actualizarMedico(actualizado);

        assertTrue(resultado);

        Optional<Medico> encontrado = service.buscarMedico(1);
        assertTrue(encontrado.isPresent());
        assertEquals(TipoMedico.TIPO_3, encontrado.get().getTipo());
        assertFalse(encontrado.get().isAtiendeSiempre());
    }

    @Test
    void testActualizarMedicoInexistente() {
        Medico medico = new Medico(999, "Fantasma", "NoExiste", TipoMedico.TIPO_1, false);

        boolean resultado = service.actualizarMedico(medico);

        assertFalse(resultado);
    }

    @Test
    void testActualizarMedicoNulo() {
        assertFalse(service.actualizarMedico(null));
    }

    // Eliminar
    @Test
    void testEliminarMedicoPorId() {
        service.crearMedico(new Medico(1, "Juan", "Pérez", TipoMedico.TIPO_1, true));
        service.crearMedico(new Medico(2, "Ana", "López", TipoMedico.TIPO_2, false));

        boolean resultado = service.eliminarMedico(1);

        assertTrue(resultado);
        assertEquals(1, service.listarMedicos().size());
        assertTrue(service.buscarMedico(1).isEmpty());
    }

    @Test
    void testEliminarMedicoInexistente() {
        boolean resultado = service.eliminarMedico(999);
        assertFalse(resultado);
    }

    @Test
    void testEliminarConIdInvalido() {
        assertFalse(service.eliminarMedico(0));
        assertFalse(service.eliminarMedico(-5));
    }

    // Buscar

    @Test
    void testBuscarMedicoExistente() {
        service.crearMedico(new Medico(10, "María", "Torres", TipoMedico.TIPO_2, true));

        Optional<Medico> resultado = service.buscarMedico(10);

        assertTrue(resultado.isPresent());
        assertEquals("María", resultado.get().getNombre());
        assertEquals("Torres", resultado.get().getApellido());
    }

    @Test
    void testBuscarMedicoInexistente() {
        assertTrue(service.buscarMedico(999).isEmpty());
    }
}
