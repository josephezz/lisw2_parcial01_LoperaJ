package co.unicauca.parcial.servicio;

import co.unicauca.parcial.modelo.Medico;
import co.unicauca.parcial.repositorio.IMedicoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación falsa (in-memory) de IMedicoRepository para pruebas unitarias.
 * 
 * Principio LSP: sustituye a MedicoSqliteRepository en los tests
 * sin alterar el comportamiento esperado por MedicoService.
 * 
 * Principio DIP: demuestra que el servicio funciona correctamente
 * con cualquier implementación del contrato IMedicoRepository.
 */
class FakeMedicoRepository implements IMedicoRepository {

    private final List<Medico> medicos = new ArrayList<>();

    @Override
    public boolean save(Medico medico) {
        if (medico == null) {
            return false;
        }
        // Verificar que no exista ya un médico con el mismo id
        for (Medico m : medicos) {
            if (m.getId() == medico.getId()) {
                return false;
            }
        }
        medicos.add(medico);
        return true;
    }

    @Override
    public List<Medico> findAll() {
        return new ArrayList<>(medicos);
    }

    @Override
    public Optional<Medico> findById(int id) {
        for (Medico m : medicos) {
            if (m.getId() == id) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Medico medico) {
        if (medico == null) {
            return false;
        }
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId() == medico.getId()) {
                medicos.set(i, medico);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return medicos.removeIf(m -> m.getId() == id);
    }
}
