package trabajo.poo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.text.Normalizer;
import java.time.format.ResolverStyle;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

// Panel base: formulario arriba, tabla en el centro y botones abajo.
// Cada modulo (pacientes, medicos, etc.) solo define sus datos y sus reglas.
public abstract class PanelCrud extends JPanel {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    protected final JTextField[] campos;
    protected final DefaultTableModel modelo;
    protected final JTable tabla;

    private final JTextField txtBuscar = new JTextField(20);
    private String filtro = "";

    public PanelCrud(String[] columnas, String[] etiquetas, int campoContrasena) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ----- Formulario -----
        JPanel formulario = new JPanel(new GridLayout(etiquetas.length, 2, 6, 6));
        campos = new JTextField[etiquetas.length];
        for (int i = 0; i < etiquetas.length; i++) {
            campos[i] = (i == campoContrasena) ? new JPasswordField() : new JTextField();
            formulario.add(new JLabel(etiquetas[i] + ":"));
            formulario.add(campos[i]);
        }

        // ----- Barra de busqueda -----
        JButton btnBuscar = new JButton("Buscar");
        JButton btnTodos = new JButton("Mostrar todos");

        btnBuscar.addActionListener(e -> accionBuscar());
        txtBuscar.addActionListener(e -> accionBuscar()); // Enter tambien busca
        btnTodos.addActionListener(e -> {
            txtBuscar.setText("");
            buscar("");
        });

        JPanel barraBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        barraBusqueda.add(new JLabel("Buscar:"));
        barraBusqueda.add(txtBuscar);
        barraBusqueda.add(btnBuscar);
        barraBusqueda.add(btnTodos);

        JPanel norte = new JPanel(new BorderLayout(0, 10));
        norte.add(barraBusqueda, BorderLayout.NORTH);
        norte.add(formulario, BorderLayout.CENTER);
        add(norte, BorderLayout.NORTH);

        // ----- Tabla -----
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                cargarEnCampos(filaSeleccionada());
            }
        });
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ----- Botones -----
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnRegistrar.addActionListener(e -> accionRegistrar());
        btnModificar.addActionListener(e -> accionModificar());
        btnEliminar.addActionListener(e -> accionEliminar());
        btnLimpiar.addActionListener(e -> limpiar());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        botones.add(btnRegistrar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        add(botones, BorderLayout.SOUTH);
    }

    // ---------- LO QUE DEFINE CADA MODULO ----------

    // Filas que se muestran en la tabla
    protected abstract List<String[]> filas();

    // Devuelven un mensaje de error, o null si todo salio bien
    protected abstract String registrar(String[] valores);

    protected abstract String modificar(String[] valores, String[] filaSeleccionada);

    protected abstract String eliminar(String[] filaSeleccionada);

    // Pasa los datos de la fila seleccionada a los campos del formulario
    protected String[] camposDesdeFila(String[] fila) {
        return fila;
    }

    // ---------- FUNCIONES COMUNES ----------

    public void refrescar() {
        modelo.setRowCount(0);
        for (String[] fila : filas()) {
            if (coincide(fila)) {
                modelo.addRow(fila);
            }
        }
    }

    // Muestra solo las filas que contienen el texto en cualquiera de sus columnas.
    // Con texto vacio muestra todas. Devuelve cuantas filas quedaron en la tabla.
    public int buscar(String texto) {
        filtro = normalizar(texto);
        refrescar();
        return modelo.getRowCount();
    }

    private boolean coincide(String[] fila) {
        if (filtro.isEmpty()) {
            return true;
        }
        for (String celda : fila) {
            if (celda != null && normalizar(celda).contains(filtro)) {
                return true;
            }
        }
        return false;
    }

    // Minusculas y sin tildes, para que "perez" encuentre "Pérez"
    private static String normalizar(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    private void accionBuscar() {
        int encontrados = buscar(txtBuscar.getText());
        if (encontrados == 0) {
            aviso("No se encontraron resultados.");
        }
    }

    protected void limpiar() {
        tabla.clearSelection();
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    protected String[] valores() {
        String[] v = new String[campos.length];
        for (int i = 0; i < campos.length; i++) {
            if (campos[i] instanceof JPasswordField) {
                v[i] = new String(((JPasswordField) campos[i]).getPassword()).trim();
            } else {
                v[i] = campos[i].getText().trim();
            }
        }
        return v;
    }

    private String[] filaSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return null;
        }
        String[] datos = new String[modelo.getColumnCount()];
        for (int c = 0; c < datos.length; c++) {
            datos[c] = String.valueOf(modelo.getValueAt(fila, c));
        }
        return datos;
    }

    private void cargarEnCampos(String[] fila) {
        String[] v = camposDesdeFila(fila);
        for (int i = 0; i < campos.length; i++) {
            campos[i].setText(i < v.length ? v[i] : "");
        }
    }

    private void accionRegistrar() {
        resolver(registrar(valores()), "Registro guardado correctamente.");
    }

    private void accionModificar() {
        String[] fila = filaSeleccionada();
        if (fila == null) {
            aviso("Seleccione una fila de la tabla.");
            return;
        }
        resolver(modificar(valores(), fila), "Datos modificados correctamente.");
    }

    private void accionEliminar() {
        String[] fila = filaSeleccionada();
        if (fila == null) {
            aviso("Seleccione una fila de la tabla.");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this,
                "Desea eliminar el registro seleccionado?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        resolver(eliminar(fila), "Registro eliminado correctamente.");
    }

    private void resolver(String error, String exito) {
        if (error != null) {
            aviso(error);
        } else {
            txtBuscar.setText("");
            buscar("");
            limpiar();
            JOptionPane.showMessageDialog(this, exito);
        }
    }

    private void aviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    // ---------- VALIDACIONES COMUNES ----------

    // Devuelve el primer mensaje de error que encuentre, o null si no hay ninguno
    protected static String primerError(String... errores) {
        for (String error : errores) {
            if (error != null) {
                return error;
            }
        }
        return null;
    }

    protected static String exigir(String valor, String campo) {
        return valor.isEmpty() ? campo + " es obligatorio." : null;
    }

    protected static String validarDni(String dni) {
        return dni.matches("\\d{8}") ? null : "El DNI debe tener exactamente 8 numeros.";
    }

    protected static String validarTelefono(String telefono) {
        return telefono.matches("\\d{9}") ? null : "El telefono debe tener exactamente 9 numeros.";
    }

    protected static String validarLetras(String texto, String campo) {
        // \\u00e1... son las letras con tilde y la ñ (asi no dependen de la codificacion del archivo)
        return texto.matches("[a-zA-Z\\u00e1\\u00e9\\u00ed\\u00f3\\u00fa\\u00c1\\u00c9\\u00cd\\u00d3\\u00da\\u00f1\\u00d1 ]+")
                ? null
                : campo + ": solo se permiten letras y espacios.";
    }

    // Devuelve la fecha si es real (no 31/02) y tiene formato DD/MM/AAAA; si no, null
    protected static LocalDate parseFecha(String texto) {
        try {
            return LocalDate.parse(texto, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
