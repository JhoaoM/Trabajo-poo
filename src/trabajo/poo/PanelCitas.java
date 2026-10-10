package trabajo.poo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanelCitas extends PanelCrud {

    private final GestionCitas gestion;

    public PanelCitas(GestionCitas gestion) {
        super(new String[]{"Nro", "DNI paciente", "Cod. medico", "Fecha", "Hora", "Motivo"},
              new String[]{"DNI del paciente", "Codigo del medico", "Fecha (DD/MM/AAAA)",
                  "Hora (HH:MM, 24 horas)", "Motivo"},
              -1);
        this.gestion = gestion;
        refrescar();
    }

    @Override
    protected List<String[]> filas() {
        List<String[]> filas = new ArrayList<>();
        for (Cita c : gestion.getCitas()) {
            filas.add(new String[]{String.valueOf(c.getId()), c.getDniPaciente(),
                c.getCodigoMedico(), c.getFecha(), c.getHora(), c.getMotivo()});
        }
        return filas;
    }

    // La primera columna (Nro) no es un campo del formulario
    @Override
    protected String[] camposDesdeFila(String[] fila) {
        return Arrays.copyOfRange(fila, 1, fila.length);
    }

    @Override
    protected String registrar(String[] v) {
        return gestion.registrarCita(v[0], v[1], v[2], v[3], v[4]);
    }

    @Override
    protected String modificar(String[] v, String[] fila) {
        if (!v[0].equals(fila[1]) || !v[1].equalsIgnoreCase(fila[2])) {
            return "Para cambiar el paciente o el medico, elimine la cita y registre una nueva.";
        }
        return gestion.modificarCita(Integer.parseInt(fila[0]), v[2], v[3], v[4]);
    }

    // "Eliminar" en este modulo cancela la cita
    @Override
    protected String eliminar(String[] fila) {
        return gestion.cancelarCita(Integer.parseInt(fila[0]));
    }
}
