package trabajo.poo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PanelPacientes extends PanelCrud {

    private static final String[] ETIQUETAS = {
        "DNI", "Nombres", "Apellidos", "Fecha de nacimiento (DD/MM/AAAA)", "Telefono"
    };

    private final GestorPacientes gestor;

    public PanelPacientes(GestorPacientes gestor) {
        super(ETIQUETAS, ETIQUETAS, -1);
        this.gestor = gestor;
        refrescar();
    }

    @Override
    protected List<String[]> filas() {
        List<String[]> filas = new ArrayList<>();
        for (Paciente p : gestor.getPacientes()) {
            filas.add(new String[]{p.getDni(), p.getNombres(), p.getApellidos(),
                p.getFechaNacimiento(), p.getTelefono()});
        }
        return filas;
    }

    private String validar(String[] v) {
        return primerError(
                validarDni(v[0]),
                validarLetras(v[1], "Los nombres"),
                validarLetras(v[2], "Los apellidos"),
                validarNacimiento(v[3]),
                validarTelefono(v[4]));
    }

    private String validarNacimiento(String texto) {
        LocalDate fecha = parseFecha(texto);
        if (fecha == null) {
            return "Fecha no valida. Use DD/MM/AAAA con una fecha real (ejemplo: 15/03/1990).";
        }
        if (fecha.isAfter(LocalDate.now())) {
            return "La fecha de nacimiento no puede ser futura.";
        }
        return null;
    }

    @Override
    protected String registrar(String[] v) {
        String error = validar(v);
        if (error != null) {
            return error;
        }
        boolean ok = gestor.registrarPaciente(new Paciente(v[0], v[1], v[2], v[3], v[4]));
        return ok ? null : "El DNI ya se encuentra registrado.";
    }

    @Override
    protected String modificar(String[] v, String[] fila) {
        if (!v[0].equals(fila[0])) {
            return "No se puede cambiar el DNI. Elimine el paciente y registrelo de nuevo.";
        }
        String error = validar(v);
        if (error != null) {
            return error;
        }
        boolean ok = gestor.modificarPaciente(fila[0], v[1], v[2], v[3], v[4]);
        return ok ? null : "No se encontro el paciente.";
    }

    @Override
    protected String eliminar(String[] fila) {
        return gestor.eliminarPaciente(fila[0]) ? null : "No se encontro el paciente.";
    }
}
