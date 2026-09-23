package co.unicauca.parcial.repositorio;

import co.unicauca.parcial.configuracion.DataBaseInitializer;
import co.unicauca.parcial.configuracion.DataBaseManager;

/**
 * Fábrica que se encarga de instanciar MedicoSqliteRepository o cualquier otro que
 * se cree en el futuro.
 * 
 * Implementa el patrón Singleton.
 */
public class Factory {

    private static Factory instance;

    private Factory() {
    }

    /**
     * Clase singleton
     *
     * @return
     */
    public static Factory getInstance() {

        if (instance == null) {
            instance = new Factory();
        }
        return instance;

    }

    /**
     * Método que crea una instancia concreta de la jerarquía IMedicoRepository
     *
     * @param type cadena que indica qué tipo de clase hija debe instanciar
     * @return una clase hija de la abstracción IMedicoRepository
     */
    public IMedicoRepository getRepository(String type) {

        IMedicoRepository result = null;

        switch (type) {
            case "default":
                DataBaseManager dbManager = new DataBaseManager();
                DataBaseInitializer dbInitializer = new DataBaseInitializer(dbManager);
                dbInitializer.initialize();
                
                result = new MedicoSqliteRepository(dbManager);
                break;
        }

        return result;

    }
}
