package trabajo.poo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class MenuPaciente {

    private GestorPacientes gestor;
    private Scanner teclado;

    public MenuPaciente() {
        gestor = new GestorPacientes();
    }

    // Recibe el Scanner del Menu principal (asi no hay dos Scanner)
    public void mostrarMenu(Scanner scanner) {

        teclado = scanner;
        int opcion;

        do {
            System.out.println("\n===== GESTION DE PACIENTES =====");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Listar pacientes");
            System.out.println("3. Buscar paciente");
            System.out.println("4. Modificar paciente");
            System.out.println("5. Eliminar paciente");
            System.out.println("6. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            // Si escriben letras, no se cae el programa
            try {
                opcion = Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {

                case 1:
                    registrar();
                    break;

                case 2:
                    gestor.listarPacientes();
                    break;

                case 3:
                    buscar();
                    break;

                case 4:
                    modificar();
                    break;

                case 5:
                    eliminar();
                    break;

                case 6:
                    System.out.println("Volviendo al menu principal...");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 6);
    }

    // ---------- METODOS AUXILIARES DE LECTURA Y VALIDACION ----------

    // DNI: exactamente 8 numeros
    private String leerDni(String mensaje) {
        String dni;
        do {
            System.out.print(mensaje);
            dni = teclado.nextLine().trim();

            if (!dni.matches("\\d{8}")) {
                System.out.println("DNI no valido. Debe contener exactamente 8 numeros.");
            }
        } while (!dni.matches("\\d{8}"));
        return dni;
    }

    // Texto con solo letras y espacios (nombres y apellidos)
    private String leerSoloLetras(String mensaje, String campo) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = teclado.nextLine().trim();

            if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println(campo + " solo deben contener letras y espacios.");
            }
        } while (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));
        return texto;
    }

    // Fecha dd/mm/aaaa: debe existir de verdad (no 31/02) y no ser futura
    private String leerFechaNacimiento(String mensaje) {
        DateTimeFormatter formato = DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        while (true) {
            System.out.print(mensaje);
            String texto = teclado.nextLine().trim();

            try {
                LocalDate fecha = LocalDate.parse(texto, formato);

                if (fecha.isAfter(LocalDate.now())) {
                    System.out.println("La fecha de nacimiento no puede ser futura.");
                } else {
                    return texto;
                }

            } catch (DateTimeParseException e) {
                System.out.println("Fecha no valida. Use el formato DD/MM/AAAA y una fecha real (ejemplo: 15/03/1990).");
            }
        }
    }

    // Telefono: exactamente 9 numeros
    private String leerTelefono(String mensaje) {
        String telefono;
        do {
            System.out.print(mensaje);
            telefono = teclado.nextLine().trim();

            if (!telefono.matches("\\d{9}")) {
                System.out.println("Telefono no valido. Debe contener exactamente 9 numeros.");
            }
        } while (!telefono.matches("\\d{9}"));
        return telefono;
    }

    // ---------- OPERACIONES ----------

    private void registrar() {

        String dni = leerDni("Ingrese DNI (8 digitos): ");
        String nombres = leerSoloLetras("Ingrese nombres: ", "Los nombres");
        String apellidos = leerSoloLetras("Ingrese apellidos: ", "Los apellidos");
        String fechaNacimiento = leerFechaNacimiento("Ingrese fecha de nacimiento (DD/MM/AAAA): ");
        String telefono = leerTelefono("Ingrese telefono (9 digitos): ");

        Paciente paciente = new Paciente(
                dni,
                nombres,
                apellidos,
                fechaNacimiento,
                telefono
        );

        boolean registrado = gestor.registrarPaciente(paciente);

        if (registrado) {
            System.out.println("Paciente registrado correctamente.");
        } else {
            System.out.println("No se puede registrar. El DNI ya se encuentra registrado.");
        }
    }

    private void buscar() {

        String dni = leerDni("Ingrese DNI del paciente (8 digitos): ");

        Paciente paciente = gestor.buscarPaciente(dni);

        if (paciente != null) {
            System.out.println("Paciente encontrado:");
            System.out.println("DNI: " + paciente.getDni());
            System.out.println("Nombres: " + paciente.getNombres());
            System.out.println("Apellidos: " + paciente.getApellidos());
            System.out.println("Fecha de nacimiento: " + paciente.getFechaNacimiento());
            System.out.println("Telefono: " + paciente.getTelefono());
        } else {
            System.out.println("No se encontro el paciente.");
        }
    }

    private void modificar() {

        String dni = leerDni("Ingrese DNI del paciente a modificar (8 digitos): ");

        Paciente paciente = gestor.buscarPaciente(dni);

        if (paciente == null) {
            System.out.println("No se encontro el paciente.");
            return;
        }

        String nombres = leerSoloLetras("Ingrese nuevos nombres: ", "Los nombres");
        String apellidos = leerSoloLetras("Ingrese nuevos apellidos: ", "Los apellidos");
        String fechaNacimiento = leerFechaNacimiento("Ingrese nueva fecha de nacimiento (DD/MM/AAAA): ");
        String telefono = leerTelefono("Ingrese nuevo telefono (9 digitos): ");

        gestor.modificarPaciente(
                dni,
                nombres,
                apellidos,
                fechaNacimiento,
                telefono
        );

        System.out.println("Paciente modificado correctamente.");
    }

    private void eliminar() {

        String dni = leerDni("Ingrese DNI del paciente a eliminar (8 digitos): ");

        boolean eliminado = gestor.eliminarPaciente(dni);

        if (eliminado) {
            System.out.println("Paciente eliminado correctamente.");
        } else {
            System.out.println("No se encontro el paciente.");
        }
    }
}