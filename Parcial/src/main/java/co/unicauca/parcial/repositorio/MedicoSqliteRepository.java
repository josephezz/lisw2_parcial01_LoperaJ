package co.unicauca.parcial.repositorio;

import co.unicauca.parcial.modelo.Medico;
import co.unicauca.parcial.modelo.TipoMedico;
import co.unicauca.parcial.configuracion.DataBaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

//Implementación concreta del repositorio de médicos usando SQLite.

public class MedicoSqliteRepository implements IMedicoRepository {

    private final DataBaseManager dbManager;

    public MedicoSqliteRepository(DataBaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public boolean save(Medico medico) {
        if (medico == null) {
            return false;
        }

        String sql = "INSERT INTO MEDICO (id, nombre, apellido, tipo, atiendeSiempre) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            dbManager.connect();
            Connection conn = dbManager.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, medico.getId());
                pstmt.setString(2, medico.getNombre());
                pstmt.setString(3, medico.getApellido());
                pstmt.setString(4, medico.getTipo().name());
                pstmt.setString(5, medico.isAtiendeSiempre() ? "Sí" : "No");
                pstmt.executeUpdate();
            }

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(MedicoSqliteRepository.class.getName())
                    .log(Level.SEVERE, "Error al guardar médico", ex);
        } finally {
            dbManager.disconnect();
        }
        return false;
    }

    @Override
    public List<Medico> findAll() {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido, tipo, atiendeSiempre FROM MEDICO";

        try {
            dbManager.connect();
            Connection conn = dbManager.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                    ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Medico medico = new Medico(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            TipoMedico.valueOf(rs.getString("tipo")),
                            "Sí".equalsIgnoreCase(rs.getString("atiendeSiempre")));
                    lista.add(medico);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MedicoSqliteRepository.class.getName())
                    .log(Level.SEVERE, "Error al listar médicos", ex);
        } finally {
            dbManager.disconnect();
        }

        return lista;
    }

    @Override
    public Optional<Medico> findById(int id) {
        String sql = "SELECT id, nombre, apellido, tipo, atiendeSiempre FROM MEDICO WHERE id = ?";

        try {
            dbManager.connect();
            Connection conn = dbManager.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Medico medico = new Medico(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                TipoMedico.valueOf(rs.getString("tipo")),
                                "Sí".equalsIgnoreCase(rs.getString("atiendeSiempre")));
                        return Optional.of(medico);
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MedicoSqliteRepository.class.getName())
                    .log(Level.SEVERE, "Error al buscar médico por id", ex);
        } finally {
            dbManager.disconnect();
        }

        return Optional.empty();
    }

    @Override
    public boolean update(Medico medico) {
        if (medico == null) {
            return false;
        }

        String sql = "UPDATE MEDICO SET nombre = ?, apellido = ?, tipo = ?, atiendeSiempre = ? "
                + "WHERE id = ?";

        try {
            dbManager.connect();
            Connection conn = dbManager.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, medico.getNombre());
                pstmt.setString(2, medico.getApellido());
                pstmt.setString(3, medico.getTipo().name());
                pstmt.setString(4, medico.isAtiendeSiempre() ? "Sí" : "No");
                pstmt.setInt(5, medico.getId());

                int filasAfectadas = pstmt.executeUpdate();
                return filasAfectadas > 0;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MedicoSqliteRepository.class.getName())
                    .log(Level.SEVERE, "Error al actualizar médico", ex);
        } finally {
            dbManager.disconnect();
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM MEDICO WHERE id = ?";

        try {
            dbManager.connect();
            Connection conn = dbManager.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);

                int filasAfectadas = pstmt.executeUpdate();
                return filasAfectadas > 0;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MedicoSqliteRepository.class.getName())
                    .log(Level.SEVERE, "Error al eliminar médico", ex);
        } finally {
            dbManager.disconnect();
        }

        return false;
    }
}
