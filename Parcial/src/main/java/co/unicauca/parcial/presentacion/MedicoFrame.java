package co.unicauca.parcial.presentacion;

import co.unicauca.parcial.modelo.Medico;
import co.unicauca.parcial.modelo.TipoMedico;
import co.unicauca.parcial.servicio.MedicoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Solo se encarga de la presentación e interacción.
// con el usuario. Toda la lógica se delega al MedicoService.

public class MedicoFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JComboBox<String> cbTipo;
    private JCheckBox chkAtiendeSiempre;
    private JButton btnCrear;
    private JButton btnListar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    private final MedicoService medicoService;

    public MedicoFrame(MedicoService medicoService) {
        this.medicoService = medicoService;

        setTitle("CRUD Médicos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        initListeners();
        
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Médico"));

        panelFormulario.add(new JLabel("  Identificación:"));
        txtId = new JTextField();
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("  Nombre(s):"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("  Apellido(s):"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("  Tipo de Médico:"));
        String[] etiquetasTipos = new String[TipoMedico.values().length];
        for (int i = 0; i < TipoMedico.values().length; i++) {
            etiquetasTipos[i] = TipoMedico.values()[i].getEtiqueta();
        }
        cbTipo = new JComboBox<>(etiquetasTipos);
        panelFormulario.add(cbTipo);

        panelFormulario.add(new JLabel("  ¿Atiende Siempre?:"));
        chkAtiendeSiempre = new JCheckBox("Sí");
        panelFormulario.add(chkAtiendeSiempre);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 8, 8));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        btnCrear = new JButton("Registrar");
        btnListar = new JButton("Listar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnCrear);
        panelBotones.add(btnListar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        // Panel superior (formulario + botones)
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        // Layout principal
        setLayout(new BorderLayout(5, 5));
        add(panelSuperior, BorderLayout.CENTER);
    }

    private void initListeners() {
        btnCrear.addActionListener(e -> crearMedico());
        btnListar.addActionListener(e -> mostrarListaMedicos());
        btnActualizar.addActionListener(e -> actualizarMedico());
        btnEliminar.addActionListener(e -> eliminarMedico());
    }

    private void crearMedico() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            TipoMedico tipo = TipoMedico.desdeEtiqueta((String) cbTipo.getSelectedItem());
            boolean atiende = chkAtiendeSiempre.isSelected();

            Medico medico = new Medico(id, nombre, apellido, tipo, atiende);
            boolean exito = medicoService.crearMedico(medico);

            if (exito) {
                JOptionPane.showMessageDialog(this,
                        "Médico registrado exitosamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar el médico. Verifique los datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La identificación debe ser un número entero.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarListaMedicos() {
        JDialog dialog = new JDialog(this, "Lista de Médicos", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        
        String[] columnas = { "ID", "Nombre", "Apellido", "Tipo", "Atiende Siempre" };
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        
        List<Medico> medicos = medicoService.listarMedicos();
        for (Medico m : medicos) {
            model.addRow(new Object[] {
                    m.getId(),
                    m.getNombre(),
                    m.getApellido(),
                    m.getTipo().getEtiqueta(),
                    m.isAtiendeSiempre() ? "Sí" : "No"
            });
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(scrollPane);
        
        if (medicos.isEmpty()) {
            JOptionPane.showMessageDialog(dialog,
                    "No hay médicos registrados.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        
        dialog.setVisible(true);
    }

    private void actualizarMedico() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            TipoMedico tipo = TipoMedico.desdeEtiqueta((String) cbTipo.getSelectedItem());
            boolean atiende = chkAtiendeSiempre.isSelected();

            Medico medico = new Medico(id, nombre, apellido, tipo, atiende);
            boolean exito = medicoService.actualizarMedico(medico);

            if (exito) {
                JOptionPane.showMessageDialog(this,
                        "Médico actualizado exitosamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo actualizar. Verifique que el ID exista.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La identificación debe ser un número entero.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarMedico() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar el médico con ID " + id + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean exito = medicoService.eliminarMedico(id);

                if (exito) {
                    JOptionPane.showMessageDialog(this,
                            "Médico eliminado exitosamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se encontró un médico con ese ID.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La identificación debe ser un número entero.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        cbTipo.setSelectedIndex(0);
        chkAtiendeSiempre.setSelected(false);
    }
}
