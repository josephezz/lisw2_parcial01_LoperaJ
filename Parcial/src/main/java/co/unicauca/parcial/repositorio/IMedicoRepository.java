package co.unicauca.parcial.repositorio;

import co.unicauca.parcial.modelo.Medico;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del repositorio de médicos.
 * 
 * Principios DIP + ISP: la capa de servicio depende de esta abstracción,
 * no de una implementación concreta. Define solo las operaciones CRUD
 * necesarias, sin métodos innecesarios.
 */
public interface IMedicoRepository {

    boolean save(Medico medico);

    List<Medico> findAll();

    Optional<Medico> findById(int id);

    boolean update(Medico medico);

    boolean delete(int id);
}
