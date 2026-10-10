package trabajo.poo;

import java.util.ArrayList;
import java.util.List;

public class GestionMedico {

    private static final String ARCHIVO = "medicos.txt";

    private ArrayList<Medico> listaMedicos;

    public GestionMedico() {
        listaMedicos = new ArrayList<>();
        cargar();
    }

    // 1. REGISTRAR (Con validación de Código y DNI no repetidos)
    public boolean registrarMedico(Medico medico) {
        if (buscarPorCodigo(medico.getCodigo()) != null) {
            System.out.println("Error: El código ya se encuentra registrado.");
            return false;
        }
        if (buscarPorDni(medico.getDni()) != null) {
            System.out.println("Error: El DNI ya pertenece a otro médico.");
            return false;
        }
        listaMedicos.add(medico);
        guardar();
        return true;
    }

    // 2. LISTAR
    public ArrayList<Medico> obtenerMedicos() {
        return listaMedicos;
    }

    // 3. BUSCAR POR CÓDIGO
    public Medico buscarPorCodigo(String codigo) {
        for (Medico m : listaMedicos) {
            if (m.getCodigo().equalsIgnoreCase(codigo)) {
                return m;
            }
        }
        return null;
    }

    // BUSCAR POR DNI
    public Medico buscarPorDni(String dni) {
        for (Medico m : listaMedicos) {
            if (m.getDni().equalsIgnoreCase(dni)) {
                return m;
            }
        }
        return null;
    }

    // 4. MODIFICAR
    public boolean modificarMedico(String codigo, String nuevosNombres, String nuevaEspecialidad, String nuevoTelefono) {
        Medico m = buscarPorCodigo(codigo);
        if (m != null) {
            m.setNombres(nuevosNombres);
            m.setEspecialidad(nuevaEspecialidad);
            m.setTelefono(nuevoTelefono);
            guardar();
            return true;
        }
        return false;
    }

    // 5. ELIMINAR
    public boolean eliminarMedico(String codigo) {
        Medico m = buscarPorCodigo(codigo);
        if (m != null) {
            listaMedicos.remove(m);
            guardar();
            return true;
        }
        return false;
    }

    // ---------- GUARDAR Y CARGAR EN ARCHIVO (medicos.txt) ----------

    private void guardar() {
        List<String> lineas = new ArrayList<>();
        for (Medico m : listaMedicos) {
            lineas.add(Archivo.limpiar(m.getCodigo()) + ";"
                    + Archivo.limpiar(m.getDni()) + ";"
                    + Archivo.limpiar(m.getNombres()) + ";"
                    + Archivo.limpiar(m.getEspecialidad()) + ";"
                    + Archivo.limpiar(m.getTelefono()));
        }
        Archivo.escribir(ARCHIVO, lineas);
    }

    private void cargar() {
        for (String[] c : Archivo.leer(ARCHIVO, 5)) {
            listaMedicos.add(new Medico(c[0], c[1], c[2], c[3], c[4]));
        }
    }
}
