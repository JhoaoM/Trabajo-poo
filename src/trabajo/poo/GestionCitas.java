package trabajo.poo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionCitas {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter
            .ofPattern("HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private static final String ARCHIVO = "citas.txt";

    private ArrayList<Cita> citas = new ArrayList<>();
    private int siguienteId = 1;

    // Se usan las mismas listas de pacientes y medicos que ya existen en el sistema
    private GestorPacientes pacientes;
    private GestionMedico medicos;
    private Scanner sc;

    public GestionCitas(GestorPacientes pacientes, GestionMedico medicos) {
        this.pacientes = pacientes;
        this.medicos = medicos;
        cargar();
    }

    // Recibe el Scanner del Menu principal (asi no hay dos Scanner)
    public void menu(Scanner scanner) {
        sc = scanner;
        int opcion;

        do {
            System.out.println("\n===== GESTION DE CITAS =====");
            System.out.println("1. Registrar cita");
            System.out.println("2. Listar citas");
            System.out.println("3. Buscar cita");
            System.out.println("4. Modificar cita");
            System.out.println("5. Cancelar cita");
            System.out.println("6. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    registrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    modificar();
                    break;
                case 5:
                    cancelar();
                    break;
                case 6:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 6);
    }

    // ---------- OPERACIONES ----------

    private void registrar() {
        System.out.println("\n--- REGISTRAR CITA ---");

        String dni = leerDni("DNI del paciente (8 digitos): ");
        Paciente paciente = pacientes.buscarPaciente(dni);
        if (paciente == null) {
            System.out.println("No existe un paciente con ese DNI. Registrelo primero en Pacientes.");
            return;
        }

        System.out.print("Codigo del medico: ");
        String codigo = sc.nextLine().trim();
        Medico medico = medicos.buscarPorCodigo(codigo);
        if (medico == null) {
            System.out.println("No existe un medico con ese codigo. Registrelo primero en Personal medico.");
            return;
        }

        String fecha = leerFecha("Fecha de la cita (DD/MM/AAAA): ");
        String hora = leerHora("Hora de la cita (HH:MM, ejemplo 09:30): ");

        String choque = buscarChoque(dni, medico.getCodigo(), fecha, hora, -1);
        if (choque != null) {
            System.out.println(choque);
            return;
        }

        String motivo = leerTextoObligatorio("Motivo de la cita: ");

        Cita cita = new Cita(siguienteId++, dni, medico.getCodigo(), fecha, hora, motivo);
        citas.add(cita);
        guardar();

        System.out.println("Cita registrada correctamente. Numero de cita: " + cita.getId());
    }

    private void listar() {
        System.out.println("\n--- LISTA DE CITAS ---");

        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }

        for (Cita c : citas) {
            mostrar(c);
        }
    }

    private void buscar() {
        System.out.println("\n--- BUSCAR CITA ---");

        int id = leerEntero("Numero de cita: ");
        Cita cita = buscarPorId(id);

        if (cita == null) {
            System.out.println("Cita no encontrada.");
            return;
        }

        mostrar(cita);
    }

    private void modificar() {
        System.out.println("\n--- MODIFICAR CITA ---");

        int id = leerEntero("Numero de cita: ");
        Cita cita = buscarPorId(id);

        if (cita == null) {
            System.out.println("Cita no encontrada.");
            return;
        }

        mostrar(cita);
        System.out.println("(Para cambiar el paciente o el medico, cancele la cita y registre una nueva.)");

        String fecha = leerFecha("Nueva fecha (DD/MM/AAAA): ");
        String hora = leerHora("Nueva hora (HH:MM): ");

        String choque = buscarChoque(cita.getDniPaciente(), cita.getCodigoMedico(), fecha, hora, cita.getId());
        if (choque != null) {
            System.out.println(choque);
            return;
        }

        String motivo = leerTextoObligatorio("Nuevo motivo: ");

        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setMotivo(motivo);
        guardar();

        System.out.println("Cita modificada correctamente.");
    }

    private void cancelar() {
        System.out.println("\n--- CANCELAR CITA ---");

        int id = leerEntero("Numero de cita: ");
        Cita cita = buscarPorId(id);

        if (cita == null) {
            System.out.println("Cita no encontrada.");
            return;
        }

        citas.remove(cita);
        guardar();
        System.out.println("Cita cancelada correctamente.");
    }

    // ---------- METODOS PARA LA INTERFAZ GRAFICA ----------
    // Devuelven un mensaje de error, o null si todo salio bien

    public ArrayList<Cita> getCitas() {
        return citas;
    }

    public String registrarCita(String dni, String codigoMedico, String fecha,
                                String hora, String motivo) {
        if (!dni.matches("\\d{8}")) {
            return "El DNI debe tener exactamente 8 numeros.";
        }
        if (pacientes.buscarPaciente(dni) == null) {
            return "No existe un paciente con ese DNI. Registrelo primero en Pacientes.";
        }
        Medico medico = medicos.buscarPorCodigo(codigoMedico);
        if (medico == null) {
            return "No existe un medico con ese codigo. Registrelo primero en Personal medico.";
        }
        String error = validarFechaHora(fecha, hora);
        if (error != null) {
            return error;
        }
        error = validarMotivo(motivo);
        if (error != null) {
            return error;
        }
        String choque = buscarChoque(dni, medico.getCodigo(), fecha, hora, -1);
        if (choque != null) {
            return choque;
        }
        citas.add(new Cita(siguienteId++, dni, medico.getCodigo(), fecha, hora, motivo));
        guardar();
        return null;
    }

    public String modificarCita(int id, String fecha, String hora, String motivo) {
        Cita cita = buscarPorId(id);
        if (cita == null) {
            return "Cita no encontrada.";
        }
        String error = validarFechaHora(fecha, hora);
        if (error != null) {
            return error;
        }
        error = validarMotivo(motivo);
        if (error != null) {
            return error;
        }
        String choque = buscarChoque(cita.getDniPaciente(), cita.getCodigoMedico(),
                fecha, hora, cita.getId());
        if (choque != null) {
            return choque;
        }
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setMotivo(motivo);
        guardar();
        return null;
    }

    public String cancelarCita(int id) {
        Cita cita = buscarPorId(id);
        if (cita == null) {
            return "Cita no encontrada.";
        }
        citas.remove(cita);
        guardar();
        return null;
    }

    // Motivo: de 3 a 100 caracteres
    private String validarMotivo(String motivo) {
        if (motivo.isEmpty()) {
            return "El motivo es obligatorio.";
        }
        if (motivo.contains(";")) {
            return "El motivo no puede contener el caracter ;";
        }
        if (motivo.length() < 3 || motivo.length() > 100) {
            return "El motivo debe tener entre 3 y 100 caracteres.";
        }
        return null;
    }

    // ---------- GUARDAR Y CARGAR EN ARCHIVO (citas.txt) ----------

    private void guardar() {
        List<String> lineas = new ArrayList<>();
        for (Cita c : citas) {
            lineas.add(c.getId() + ";"
                    + Archivo.limpiar(c.getDniPaciente()) + ";"
                    + Archivo.limpiar(c.getCodigoMedico()) + ";"
                    + Archivo.limpiar(c.getFecha()) + ";"
                    + Archivo.limpiar(c.getHora()) + ";"
                    + Archivo.limpiar(c.getMotivo()));
        }
        Archivo.escribir(ARCHIVO, lineas);
    }

    private void cargar() {
        for (String[] c : Archivo.leer(ARCHIVO, 6)) {
            try {
                int id = Integer.parseInt(c[0]);
                citas.add(new Cita(id, c[1], c[2], c[3], c[4], c[5]));
                if (id >= siguienteId) {
                    siguienteId = id + 1;
                }
            } catch (NumberFormatException e) {
                // linea danada: se ignora
            }
        }
    }

    private String validarFechaHora(String fecha, String hora) {
        try {
            LocalDate f = LocalDate.parse(fecha, FORMATO_FECHA);
            if (f.isBefore(LocalDate.now())) {
                return "La fecha de la cita no puede ser pasada.";
            }
        } catch (DateTimeParseException e) {
            return "Fecha no valida. Use DD/MM/AAAA (ejemplo: 25/12/2026).";
        }
        try {
            LocalTime.parse(hora, FORMATO_HORA);
        } catch (DateTimeParseException e) {
            return "Hora no valida. Use HH:MM de 24 horas (ejemplo: 09:30).";
        }
        return null;
    }

    // ---------- METODOS AUXILIARES ----------

    private Cita buscarPorId(int id) {
        for (Cita c : citas) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // Devuelve un mensaje de error si el medico o el paciente ya tienen una cita
    // a esa misma fecha y hora; devuelve null si el horario esta libre.
    // idIgnorar se usa al modificar, para no compararla consigo misma.
    private String buscarChoque(String dni, String codigoMedico, String fecha, String hora, int idIgnorar) {
        for (Cita c : citas) {
            if (c.getId() == idIgnorar) {
                continue;
            }
            if (c.getFecha().equals(fecha) && c.getHora().equals(hora)) {
                if (c.getCodigoMedico().equalsIgnoreCase(codigoMedico)) {
                    return "El medico ya tiene una cita en esa fecha y hora.";
                }
                if (c.getDniPaciente().equals(dni)) {
                    return "El paciente ya tiene una cita en esa fecha y hora.";
                }
            }
        }
        return null;
    }

    private void mostrar(Cita c) {
        Paciente p = pacientes.buscarPaciente(c.getDniPaciente());
        Medico m = medicos.buscarPorCodigo(c.getCodigoMedico());

        String nombrePaciente = (p != null)
                ? p.getNombres() + " " + p.getApellidos()
                : "(paciente eliminado)";
        String nombreMedico = (m != null)
                ? m.getNombres() + " - " + m.getEspecialidad()
                : "(medico eliminado)";

        System.out.println("----------------------------");
        System.out.println("Cita Nro: " + c.getId());
        System.out.println("Paciente: " + c.getDniPaciente() + " - " + nombrePaciente);
        System.out.println("Medico: " + c.getCodigoMedico() + " - " + nombreMedico);
        System.out.println("Fecha: " + c.getFecha() + "  Hora: " + c.getHora());
        System.out.println("Motivo: " + c.getMotivo());
    }

    // ---------- LECTURA Y VALIDACION ----------

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    private String leerTextoObligatorio(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("Este campo es obligatorio.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    private String leerDni(String mensaje) {
        String dni;
        do {
            System.out.print(mensaje);
            dni = sc.nextLine().trim();

            if (!dni.matches("\\d{8}")) {
                System.out.println("DNI no valido. Debe contener exactamente 8 numeros.");
            }
        } while (!dni.matches("\\d{8}"));
        return dni;
    }

    // Fecha real (no 31/02) y que no sea una fecha pasada
    private String leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();

            try {
                LocalDate fecha = LocalDate.parse(texto, FORMATO_FECHA);

                if (fecha.isBefore(LocalDate.now())) {
                    System.out.println("La fecha de la cita no puede ser pasada.");
                } else {
                    return texto;
                }

            } catch (DateTimeParseException e) {
                System.out.println("Fecha no valida. Use el formato DD/MM/AAAA (ejemplo: 25/12/2026).");
            }
        }
    }

    // Hora en formato 24 horas con dos digitos (ejemplo: 09:30, 14:00)
    private String leerHora(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();

            try {
                LocalTime.parse(texto, FORMATO_HORA);
                return texto;
            } catch (DateTimeParseException e) {
                System.out.println("Hora no valida. Use el formato HH:MM de 24 horas (ejemplo: 09:30).");
            }
        }
    }
}
