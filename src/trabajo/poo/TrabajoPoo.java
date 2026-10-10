package trabajo.poo;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TrabajoPoo {

    public static void main(String[] args) {

        // Aspecto de Windows para las ventanas
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // si falla, se usa el aspecto por defecto
        }

        // Los datos se crean una sola vez y se comparten entre todas las ventanas
        GestionUsuarios usuarios = new GestionUsuarios();
        GestorPacientes pacientes = new GestorPacientes();
        GestionMedico medicos = new GestionMedico();
        GestionCitas citas = new GestionCitas(pacientes, medicos);

        SwingUtilities.invokeLater(() ->
                new VentanaLogin(usuarios, pacientes, medicos, citas).setVisible(true));
    }
}
