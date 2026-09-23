package co.unicauca.parcial;

import co.unicauca.parcial.presentacion.MedicoFrame;
import co.unicauca.parcial.repositorio.Factory;
import co.unicauca.parcial.repositorio.IMedicoRepository;
import co.unicauca.parcial.servicio.MedicoService;

import javax.swing.SwingUtilities;

//Clase principal (Contiene el main).
public class Parcial {

    public static void main(String[] args) {
        // Obtenemos el repositorio mediante la fábrica (aplica DIP y patrón Factory)
        IMedicoRepository medicoRepository = Factory.getInstance().getRepository("default");

        // Capa de servicio (lógica de negocio)
        MedicoService medicoService = new MedicoService(medicoRepository);

        // Capa de presentación (GUI)
        SwingUtilities.invokeLater(() -> {
            new MedicoFrame(medicoService).setVisible(true);
        });
    }
}
