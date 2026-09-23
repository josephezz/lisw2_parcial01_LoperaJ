package co.unicauca.parcial.repositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

//Se encarga de crear las tablas de la base de datos si no existen.

public class DataBaseInitializer {

    private final DataBaseManager dbManager;

    public DataBaseInitializer(DataBaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void initialize() {
        String sql = """
                CREATE TABLE IF NOT EXISTS MEDICO (
                    id INTEGER PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    apellido VARCHAR(100) NOT NULL,
                    tipo VARCHAR(50) NOT NULL,
                    atiendeSiempre VARCHAR(10) NOT NULL
                );
                """;

        try {
            dbManager.connect();
            Connection conn = dbManager.getConnection();

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseInitializer.class.getName())
                    .log(Level.SEVERE, "Error al inicializar la base de datos", ex);
        } finally {
            dbManager.disconnect();
        }
    }
}
