package trabajo.poo;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MenuPaciente {

    private GestorPacientes gestor;
    private Scanner teclado;

    public MenuPaciente() {
        gestor = new GestorPacientes();
        teclado = new Scanner(System.in);
    }

    public void mostrarMenu() {

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

            opcion = teclado.nextInt();
            teclado.nextLine();

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

    private void registrar() {

    String dni;

    do {
        System.out.print("Ingrese DNI (8 digitos): ");
        dni = teclado.nextLine();

        if (!dni.matches("\\d{8}")) {
            System.out.println("DNI no valido. Debe contener exactamente 8 numeros.");
        }

    } while (!dni.matches("\\d{8}"));

    String nombres;

    do {
        System.out.print("Ingrese nombres: ");
        nombres = teclado.nextLine().trim();

        if (!nombres.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("Los nombres solo deben contener letras y espacios.");
        }

    } while (!nombres.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));

    String apellidos;

    do {
        System.out.print("Ingrese apellidos: ");
        apellidos = teclado.nextLine().trim();

        if (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("Los apellidos solo deben contener letras y espacios.");
        }

    } while (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));

    String fechaNacimiento;
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy");

    do {
        System.out.print("Ingrese fecha de nacimiento (DD/MM/AA): ");
        fechaNacimiento = teclado.nextLine();

        try {

            LocalDate.parse(fechaNacimiento, formato);

        } catch (DateTimeParseException e) {

            System.out.println("Fecha no valida. Use el formato DD/MM/AA y una fecha real.");
            fechaNacimiento = "";

        }

    } while (fechaNacimiento.isEmpty());

    String telefono;

    do {
        System.out.print("Ingrese telefono (9 digitos): ");
        telefono = teclado.nextLine();

        if (!telefono.matches("\\d{9}")) {
            System.out.println("Telefono no valido. Debe contener exactamente 9 numeros.");
        }

    } while (!telefono.matches("\\d{9}"));

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
        String dni;
        do {
            System.out.print("Ingrese DNI del paciente (8 digitos): ");
            dni = teclado.nextLine();
            if (!dni.matches("\\d{8}")) {
                System.out.println("DNI no valido. Debe contener exactamente 8 numeros.");
            }
        } while (!dni.matches("\\d{8}"));

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

    String dni;

    do {
        System.out.print("Ingrese DNI del paciente a modificar (8 digitos): ");
        dni = teclado.nextLine();

        if (!dni.matches("\\d{8}")) {
            System.out.println("DNI no valido. Debe contener exactamente 8 numeros.");
        }

    } while (!dni.matches("\\d{8}"));

    Paciente paciente = gestor.buscarPaciente(dni);

    if (paciente != null) {

        String nombres;

        do {
            System.out.print("Ingrese nuevos nombres: ");
            nombres = teclado.nextLine().trim();

            if (!nombres.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Los nombres solo deben contener letras y espacios.");
            }

        } while (!nombres.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));

        String apellidos;

        do {
            System.out.print("Ingrese nuevos apellidos: ");
            apellidos = teclado.nextLine().trim();

            if (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Los apellidos solo deben contener letras y espacios.");
            }

        } while (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));

        String fechaNacimiento;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy");

        do {
            System.out.print("Ingrese nueva fecha de nacimiento (DD/MM/AA): ");
            fechaNacimiento = teclado.nextLine();

            try {

                LocalDate.parse(fechaNacimiento, formato);

            } catch (DateTimeParseException e) {

                System.out.println("Fecha no valida. Use el formato DD/MM/AA y una fecha real.");
                fechaNacimiento = "";

            }

        } while (fechaNacimiento.isEmpty());

        String telefono;

        do {
            System.out.print("Ingrese nuevo telefono (9 digitos): ");
            telefono = teclado.nextLine();

            if (!telefono.matches("\\d{9}")) {
                System.out.println("Telefono no valido. Debe contener exactamente 9 numeros.");
            }

        } while (!telefono.matches("\\d{9}"));

        gestor.modificarPaciente(
                dni,
                nombres,
                apellidos,
                fechaNacimiento,
                telefono
        );

        System.out.println("Paciente modificado correctamente.");

    } else {
        System.out.println("No se encontro el paciente.");
    }
}

    private void eliminar() {

        String dni;
        do {
            System.out.print("Ingrese DNI del paciente a eliminar (8 digitos): ");
            dni = teclado.nextLine();
            if (!dni.matches("\\d{8}")) {
                System.out.println("DNI no valido. Debe contener exactamente 8 numeros.");
            }
        } while (!dni.matches("\\d{8}"));

        boolean eliminado = gestor.eliminarPaciente(dni);

        if (eliminado) {
            System.out.println("Paciente eliminado correctamente.");
        } else {
            System.out.println("No se encontro el paciente.");
        }
    }
}