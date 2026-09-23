package co.unicauca.parcial.repositorio;

import co.unicauca.parcial.modelo.Medico;

import java.util.List;
import java.util.Optional;

//Contrato del repositorio de médicos.

public interface IMedicoRepository {

    boolean save(Medico medico);

    List<Medico> findAll();

    Optional<Medico> findById(int id);

    boolean update(Medico medico);

    boolean delete(int id);
}
