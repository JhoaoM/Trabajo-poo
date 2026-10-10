package trabajo.poo;

import java.util.ArrayList;
import java.util.List;

// Usa la lista y las reglas de GestionMedicamentos (el codigo de Leonel)
public class PanelMedicamentos extends PanelCrud {

    private static final String[] ETIQUETAS = {
        "Codigo", "Nombre", "Descripcion (opcional)", "Cantidad", "Vencimiento (DD/MM/AAAA)"
    };

    public PanelMedicamentos() {
        super(new String[]{"Codigo", "Nombre", "Descripcion", "Cantidad", "Vencimiento", "Estado"},
              ETIQUETAS, -1);
        refrescar();
    }

    @Override
    protected List<String[]> filas() {
        List<String[]> filas = new ArrayList<>();
        for (Medicamentos m : GestionMedicamentos.getMedicamentos()) {
            filas.add(new String[]{
                m.getCodigo(),
                m.getNombre(),
                m.getDescripcion(),
                String.valueOf(m.getCantidad()),
                m.getFechaVencimiento().format(Medicamentos.FORMATO),
                m.estaVencido() ? "VENCIDO" : "Vigente"});
        }
        return filas;
    }

    @Override
    protected String registrar(String[] v) {
        return GestionMedicamentos.registrarDatos(v[0], v[1], v[2], v[3], v[4]);
    }

    @Override
    protected String modificar(String[] v, String[] fila) {
        if (!v[0].equalsIgnoreCase(fila[0])) {
            return "No se puede cambiar el codigo. Elimine el medicamento y registrelo de nuevo.";
        }
        return GestionMedicamentos.modificarDatos(fila[0], v[1], v[2], v[3], v[4]);
    }

    @Override
    protected String eliminar(String[] fila) {
        return GestionMedicamentos.eliminarMedicamento(fila[0]);
    }
}
