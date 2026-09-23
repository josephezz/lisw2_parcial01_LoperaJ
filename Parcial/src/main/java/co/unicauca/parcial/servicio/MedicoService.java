package co.unicauca.parcial.servicio;

import co.unicauca.parcial.modelo.Medico;
import co.unicauca.parcial.repositorio.IMedicoRepository;

import java.util.List;
import java.util.Optional;

//Servicio de lógica de negocio para médicos.

public class MedicoService {

    private final IMedicoRepository repository;

    public MedicoService(IMedicoRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("El repositorio de médicos es obligatorio.");
        }
        this.repository = repository;
    }

    /**
     * Crea un nuevo médico con validaciones de negocio.
     *
     * @param medico el médico a crear
     * @return true si se creó exitosamente, false en caso contrario
     */
    public boolean crearMedico(Medico medico) {
        if (medico == null) {
            return false;
        }
        if (medico.getId() <= 0) {
            return false;
        }
        if (medico.getNombre() == null || medico.getNombre().isBlank()) {
            return false;
        }
        if (medico.getApellido() == null || medico.getApellido().isBlank()) {
            return false;
        }
        if (medico.getTipo() == null) {
            return false;
        }
        return repository.save(medico);
    }

    /**
     * Lista todos los médicos registrados.
     *
     * @return lista de médicos
     */
    public List<Medico> listarMedicos() {
        return repository.findAll();
    }

    /**
     * Busca un médico por su identificación.
     *
     * @param id la identificación del médico
     * @return Optional con el médico encontrado, o vacío si no existe
     */
    public Optional<Medico> buscarMedico(int id) {
        if (id <= 0) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

    /**
     * Actualiza los datos de un médico existente (tipo y atiendeSiempre).
     *
     * @param medico el médico con los datos actualizados
     * @return true si se actualizó exitosamente, false en caso contrario
     */
    public boolean actualizarMedico(Medico medico) {
        if (medico == null) {
            return false;
        }
        if (medico.getId() <= 0) {
            return false;
        }

        Optional<Medico> existente = repository.findById(medico.getId());
        if (existente.isEmpty()) {
            return false;
        }

        return repository.update(medico);
    }

    /**
     * Elimina un médico por su identificación.
     *
     * @param id la identificación del médico a eliminar
     * @return true si se eliminó exitosamente, false si no existe
     */
    public boolean eliminarMedico(int id) {
        if (id <= 0) {
            return false;
        }

        Optional<Medico> existente = repository.findById(id);
        if (existente.isEmpty()) {
            return false;
        }

        return repository.delete(id);
    }
}
