package trabajo.poo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaLogin extends JFrame {

    private final GestionUsuarios usuarios;
    private final GestorPacientes pacientes;
    private final GestionMedico medicos;
    private final GestionCitas citas;

    private final JTextField txtUsuario = new JTextField(15);
    private final JPasswordField txtContrasena = new JPasswordField(15);

    public VentanaLogin(GestionUsuarios usuarios, GestorPacientes pacientes,
                        GestionMedico medicos, GestionCitas citas) {
        super("Iniciar sesion");
        this.usuarios = usuarios;
        this.pacientes = pacientes;
        this.medicos = medicos;
        this.citas = citas;

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("Sistema de Gestion", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(18f));

        JPanel formulario = new JPanel(new GridLayout(2, 2, 8, 8));
        formulario.add(new JLabel("Usuario:"));
        formulario.add(txtUsuario);
        formulario.add(new JLabel("Contrasena:"));
        formulario.add(txtContrasena);

        JButton btnIngresar = new JButton("Iniciar sesion");
        JButton btnRegistrarse = new JButton("Registrarse");
        JButton btnSalir = new JButton("Salir");

        btnIngresar.addActionListener(e -> ingresar());
        btnRegistrarse.addActionListener(e -> registrarse());
        btnSalir.addActionListener(e -> System.exit(0));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        botones.add(btnIngresar);
        botones.add(btnRegistrarse);
        botones.add(btnSalir);

        JPanel contenido = new JPanel(new BorderLayout(10, 15));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        contenido.add(titulo, BorderLayout.NORTH);
        contenido.add(formulario, BorderLayout.CENTER);
        contenido.add(botones, BorderLayout.SOUTH);

        setContentPane(contenido);
        getRootPane().setDefaultButton(btnIngresar); // Enter = iniciar sesion
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void ingresar() {
        if (!usuarios.hayUsuarios()) {
            JOptionPane.showMessageDialog(this,
                    "No hay usuarios registrados. Use el boton Registrarse.");
            return;
        }

        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();

        if (usuarios.validarCredenciales(usuario, contrasena)) {
            new VentanaPrincipal(usuarios, pacientes, medicos, citas).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contrasena incorrectos.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            txtContrasena.setText("");
        }
    }

    private void registrarse() {
        JTextField nombre = new JTextField();
        JTextField usuario = new JTextField();
        JPasswordField contrasena = new JPasswordField();

        Object[] campos = {"Nombre:", nombre, "Usuario:", usuario, "Contrasena:", contrasena};

        int respuesta = JOptionPane.showConfirmDialog(this, campos, "Registrarse",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (respuesta != JOptionPane.OK_OPTION) {
            return;
        }

        String error = usuarios.registrarDatos(
                nombre.getText().trim(),
                usuario.getText().trim(),
                new String(contrasena.getPassword()).trim());

        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario registrado. Ya puede iniciar sesion.");
        }
    }
}
