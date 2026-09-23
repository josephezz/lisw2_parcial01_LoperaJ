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

    // Crea un nuevo médico con validaciones de negocio.
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

    // Lista todos los médicos registrados.
    public List<Medico> listarMedicos() {
        return repository.findAll();
    }

    // Busca un médico por su identificación
    public Optional<Medico> buscarMedico(int id) {
        if (id <= 0) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

    // Actualiza los datos de un médico si ya existe
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

    // Elimina un médico por su identificación
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
