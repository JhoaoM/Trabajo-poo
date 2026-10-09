package trabajo.poo;

import java.util.ArrayList;

public class GestionMedico {
    private ArrayList<Medico> listaMedicos;

    public GestionMedico() {
        listaMedicos = new ArrayList<>();
    }

    // REGISTRAR MÉDICO
    public boolean registrarMedico(Medico medico) {
        if (buscarPorCodigo(medico.getCodigo()) != null) {
            System.out.println(">> Error: El codigo ya se encuentra registrado.");
            return false;
        }
        if (buscarPorDni(medico.getDni()) != null) {
            System.out.println(">> Error: El DNI ya esta asignado a otro medico.");
            return false;
        }
        listaMedicos.add(medico);
        return true;
    }

    // LISTAR MÉDICOS
    public ArrayList<Medico> obtenerMedicos() {
        return listaMedicos;
    }

    // BUSCAR POR CÓDIGO
    public Medico buscarPorCodigo(String codigo) {
        for (Medico m : listaMedicos) {
            if (m.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return m;
            }
        }
        return null;
    }

    // BUSCAR POR DNI
    public Medico buscarPorDni(String dni) {
        for (Medico m : listaMedicos) {
            if (m.getDni().equalsIgnoreCase(dni.trim())) {
                return m;
            }
        }
        return null;
    }

    // MODIFICAR MÉDICO
    public boolean modificarMedico(String codigo, String nuevosNombres, String nuevaEspecialidad, String nuevoTelefono) {
        Medico m = buscarPorCodigo(codigo);
        if (m != null) {
            m.setNombres(nuevosNombres);
            m.setEspecialidad(nuevaEspecialidad);
            m.setTelefono(nuevoTelefono);
            return true;
        }
        return false;
    }

    // ELIMINAR MÉDICO
    public boolean eliminarMedico(String codigo) {
        Medico m = buscarPorCodigo(codigo);
        if (m != null) {
            listaMedicos.remove(m);
            return true;
        }
        return false;
    }
}