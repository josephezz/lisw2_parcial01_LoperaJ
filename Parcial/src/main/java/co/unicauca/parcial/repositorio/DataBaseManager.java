package co.unicauca.parcial.repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//Gestiona la conexión a la base de datos SQLite.

public class DataBaseManager {

    private static final String URL = "jdbc:sqlite:./medicos.db";
    private Connection conn;

    public void connect() {
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseManager.class.getName())
                    .log(Level.SEVERE, "Error al conectar con la base de datos", ex);
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseManager.class.getName())
                    .log(Level.WARNING, "Error al desconectar", ex);
        }
    }
}
