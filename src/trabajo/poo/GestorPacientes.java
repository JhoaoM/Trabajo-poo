package trabajo.poo;

import java.util.ArrayList;

public class GestorPacientes {

    private ArrayList<Paciente> pacientes;

    //Constructor
    public GestorPacientes() {
        pacientes = new ArrayList<>();
    }

    // Lo usan la interfaz grafica y el modulo de citas
    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public boolean registrarPaciente(Paciente paciente) {
        if (buscarPaciente(paciente.getDni()) != null) {
            return false;
        }
        pacientes.add(paciente);
        return true;
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }
        for (Paciente paciente : pacientes) {
            System.out.println("DNI: " + paciente.getDni());
            System.out.println("Nombres: " + paciente.getNombres());
            System.out.println("Apellidos: " + paciente.getApellidos());
            System.out.println("Fecha de nacimiento: " + paciente.getFechaNacimiento());
            System.out.println("Telefono: " + paciente.getTelefono());
            System.out.println("-------------------------");
        }
    }

    public Paciente buscarPaciente(String dni) {
        for (Paciente paciente : pacientes) {
            if (paciente.getDni().equals(dni)) {
                return paciente;
            }
        }

        return null;
    }

    public boolean modificarPaciente(String dni, String nombres, String apellidos,
            String fechaNacimiento, String telefono) {

        Paciente paciente = buscarPaciente(dni);

        if (paciente != null) {
            paciente.setNombres(nombres);
            paciente.setApellidos(apellidos);
            paciente.setFechaNacimiento(fechaNacimiento);
            paciente.setTelefono(telefono);
            return true;
        }

        return false;
    }

    public boolean eliminarPaciente(String dni) {
        Paciente paciente = buscarPaciente(dni);

        if (paciente != null) {
            pacientes.remove(paciente);
            return true;
        }

        return false;
    }
}
