package trabajo.poo;

import java.util.ArrayList;
import java.util.List;

public class PanelUsuarios extends PanelCrud {

    private final GestionUsuarios gestion;

    public PanelUsuarios(GestionUsuarios gestion) {
        super(new String[]{"ID", "Nombre", "Usuario"},
              new String[]{"Nombre", "Usuario", "Contrasena (vacia = no cambiar)"},
              2);
        this.gestion = gestion;
        refrescar();
    }

    @Override
    protected List<String[]> filas() {
        List<String[]> filas = new ArrayList<>();
        for (Usuario u : gestion.getLista()) {
            // La contrasena no se muestra en la tabla
            filas.add(new String[]{String.valueOf(u.getId()), u.getNombre(), u.getUsuario()});
        }
        return filas;
    }

    @Override
    protected String[] camposDesdeFila(String[] fila) {
        return new String[]{fila[1], fila[2], ""};
    }

    @Override
    protected String registrar(String[] v) {
        return gestion.registrarDatos(v[0], v[1], v[2]);
    }

    @Override
    protected String modificar(String[] v, String[] fila) {
        if (!v[1].equalsIgnoreCase(fila[2])) {
            return "No se puede cambiar el nombre de usuario.";
        }
        return gestion.modificarDatos(fila[2], v[0], v[2]);
    }

    @Override
    protected String eliminar(String[] fila) {
        return gestion.eliminarUsuario(fila[2]);
    }
}
