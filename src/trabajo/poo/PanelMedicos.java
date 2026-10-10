package trabajo.poo;

import java.util.ArrayList;
import java.util.List;

public class PanelMedicos extends PanelCrud {

    private static final String[] ETIQUETAS = {
        "Codigo", "DNI", "Nombres completos", "Especialidad", "Telefono"
    };

    private final GestionMedico gestion;

    public PanelMedicos(GestionMedico gestion) {
        super(ETIQUETAS, ETIQUETAS, -1);
        this.gestion = gestion;
        refrescar();
    }

    @Override
    protected List<String[]> filas() {
        List<String[]> filas = new ArrayList<>();
        for (Medico m : gestion.obtenerMedicos()) {
            filas.add(new String[]{m.getCodigo(), m.getDni(), m.getNombres(),
                m.getEspecialidad(), m.getTelefono()});
        }
        return filas;
    }

    private String validar(String[] v) {
        return primerError(
                validarCodigo(v[0], "El codigo"),
                validarDni(v[1]),
                validarLetras(v[2], "Los nombres", 3, 50),
                validarLetras(v[3], "La especialidad", 3, 30),
                validarTelefono(v[4]));
    }

    @Override
    protected String registrar(String[] v) {
        String error = validar(v);
        if (error != null) {
            return error;
        }
        boolean ok = gestion.registrarMedico(new Medico(v[0], v[1], v[2], v[3], v[4]));
        return ok ? null : "El codigo o el DNI ya pertenecen a otro medico.";
    }

    @Override
    protected String modificar(String[] v, String[] fila) {
        if (!v[0].equalsIgnoreCase(fila[0]) || !v[1].equals(fila[1])) {
            return "No se puede cambiar el codigo ni el DNI. Elimine el medico y registrelo de nuevo.";
        }
        String error = validar(v);
        if (error != null) {
            return error;
        }
        boolean ok = gestion.modificarMedico(fila[0], v[2], v[3], v[4]);
        return ok ? null : "No se encontro el medico.";
    }

    @Override
    protected String eliminar(String[] fila) {
        return gestion.eliminarMedico(fila[0]) ? null : "No se encontro el medico.";
    }
}
