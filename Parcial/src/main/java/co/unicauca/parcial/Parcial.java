package co.unicauca.parcial;

import co.unicauca.parcial.presentacion.MedicoFrame;
import co.unicauca.parcial.repositorio.DataBaseInitializer;
import co.unicauca.parcial.repositorio.DataBaseManager;
import co.unicauca.parcial.repositorio.IMedicoRepository;
import co.unicauca.parcial.repositorio.MedicoSqliteRepository;
import co.unicauca.parcial.servicio.MedicoService;

import javax.swing.SwingUtilities;

/**
 * Composition Root de la aplicación.
 * 
 * Aquí se instancian todas las dependencias y se inyectan
 * manualmente (inyección de dependencias sin framework).
 * 
 * Principio DIP: este es el único lugar donde se conocen
 * las implementaciones concretas. El resto de la aplicación
 * trabaja con abstracciones.
 */
public class Parcial {

    public static void main(String[] args) {
        // Capa de configuración / acceso a datos
        DataBaseManager dbManager = new DataBaseManager();
        DataBaseInitializer dbInitializer = new DataBaseInitializer(dbManager);
        dbInitializer.initialize();

        // Capa de repositorio (implementación concreta)
        IMedicoRepository medicoRepository = new MedicoSqliteRepository(dbManager);

        // Capa de servicio (lógica de negocio)
        MedicoService medicoService = new MedicoService(medicoRepository);

        // Capa de presentación (GUI)
        SwingUtilities.invokeLater(() -> {
            new MedicoFrame(medicoService).setVisible(true);
        });
    }
}
