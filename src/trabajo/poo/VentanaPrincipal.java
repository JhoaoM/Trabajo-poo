package trabajo.poo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal(GestionUsuarios usuarios, GestorPacientes pacientes,
                            GestionMedico medicos, GestionCitas citas) {
        super("Sistema de Gestion");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Pacientes", new PanelPacientes(pacientes));
        pestanas.addTab("Personal medico", new PanelMedicos(medicos));
        pestanas.addTab("Medicamentos", new PanelMedicamentos());
        pestanas.addTab("Citas", new PanelCitas(citas));
        pestanas.addTab("Usuarios", new PanelUsuarios(usuarios));

        // Al cambiar de pestana se actualiza la tabla
        pestanas.addChangeListener(e -> {
            if (pestanas.getSelectedComponent() instanceof PanelCrud) {
                ((PanelCrud) pestanas.getSelectedComponent()).refrescar();
            }
        });

        JButton btnCerrarSesion = new JButton("Cerrar sesion");
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new VentanaLogin(usuarios, pacientes, medicos, citas).setVisible(true);
        });

        JPanel inferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        inferior.add(btnCerrarSesion);

        add(pestanas, BorderLayout.CENTER);
        add(inferior, BorderLayout.SOUTH);
    }
}
