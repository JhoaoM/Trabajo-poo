package trabajo.poo;

import java.util.ArrayList;
import java.util.List;

public class GestorPacientes {

    private static final String ARCHIVO = "pacientes.txt";

    private ArrayList<Paciente> pacientes;

    //Constructor
    public GestorPacientes() {
        pacientes = new ArrayList<>();
        cargar();
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
        guardar();
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
            guardar();
            return true;
        }

        return false;
    }

    public boolean eliminarPaciente(String dni) {
        Paciente paciente = buscarPaciente(dni);

        if (paciente != null) {
            pacientes.remove(paciente);
            guardar();
            return true;
        }

        return false;
    }

    // ---------- GUARDAR Y CARGAR EN ARCHIVO (pacientes.txt) ----------

    private void guardar() {
        List<String> lineas = new ArrayList<>();
        for (Paciente p : pacientes) {
            lineas.add(Archivo.limpiar(p.getDni()) + ";"
                    + Archivo.limpiar(p.getNombres()) + ";"
                    + Archivo.limpiar(p.getApellidos()) + ";"
                    + Archivo.limpiar(p.getFechaNacimiento()) + ";"
                    + Archivo.limpiar(p.getTelefono()));
        }
        Archivo.escribir(ARCHIVO, lineas);
    }

    private void cargar() {
        for (String[] c : Archivo.leer(ARCHIVO, 5)) {
            pacientes.add(new Paciente(c[0], c[1], c[2], c[3], c[4]));
        }
    }
}
