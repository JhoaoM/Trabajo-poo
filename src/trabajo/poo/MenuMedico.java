package trabajo.poo;

import java.util.Scanner;

public class MenuMedico {
    private GestionMedico gestion;
    private Scanner scanner;

    public MenuMedico() {
        gestion = new GestionMedico();
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n=================================");
            System.out.println("  GESTIÓN DE PERSONAL MÉDICO");
            System.out.println("=================================");
            System.out.println("1. Registrar Médico");
            System.out.println("2. Listar Médicos");
            System.out.println("3. Buscar Médico por Código");
            System.out.println("4. Modificar Médico");
            System.out.println("5. Eliminar Médico");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                opcion = 0;
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
                    eliminar();
                    break;
                case 6:
                    System.out.println("Regresando...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }

    private void registrar() {
        System.out.println("\n--- REGISTRAR MÉDICO ---");
        System.out.print("Ingrese Código: ");
        String cod = scanner.nextLine();
        System.out.print("Ingrese DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Ingrese Nombres completos: ");
        String nom = scanner.nextLine();
        System.out.print("Ingrese Especialidad: ");
        String esp = scanner.nextLine();
        System.out.print("Ingrese Teléfono: ");
        String tel = scanner.nextLine();

        Medico m = new Medico(cod, dni, nom, esp, tel);
        if (gestion.registrarMedico(m)) {
            System.out.println(">> ¡Médico registrado exitosamente!");
        } else {
            System.out.println(">> Error: El Código o el DNI ya existen.");
        }
    }

    private void listar() {
        System.out.println("\n--- LISTA DE MÉDICOS ---");
        if (gestion.obtenerMedicos().isEmpty()) {
            System.out.println("No hay médicos registrados.");
        } else {
            for (Medico m : gestion.obtenerMedicos()) {
                System.out.println(m);
            }
        }
    }

    private void buscar() {
        System.out.println("\n--- BUSCAR MÉDICO ---");
        System.out.print("Ingrese el código del médico a buscar: ");
        String cod = scanner.nextLine();
        Medico m = gestion.buscarPorCodigo(cod);
        if (m != null) {
            System.out.println("Resultado: " + m);
        } else {
            System.out.println(">> Médico no encontrado.");
        }
    }

    private void modificar() {
        System.out.println("\n--- MODIFICAR MÉDICO ---");
        System.out.print("Ingrese el código del médico a modificar: ");
        String cod = scanner.nextLine();
        Medico m = gestion.buscarPorCodigo(cod);
        if (m != null) {
            System.out.println("Médico seleccionado: " + m.getNombres());
            System.out.print("Nuevos Nombres: ");
            String nom = scanner.nextLine();
            System.out.print("Nueva Especialidad: ");
            String esp = scanner.nextLine();
            System.out.print("Nuevo Teléfono: ");
            String tel = scanner.nextLine();

            if (gestion.modificarMedico(cod, nom, esp, tel)) {
                System.out.println(">> ¡Médico modificado correctamente!");
            }
        } else {
            System.out.println(">> Médico no encontrado.");
        }
    }

    private void eliminar() {
        System.out.println("\n--- ELIMINAR MÉDICO ---");
        System.out.print("Ingrese el código del médico a eliminar: ");
        String cod = scanner.nextLine();
        if (gestion.eliminarMedico(cod)) {
            System.out.println(">> ¡Médico eliminado correctamente!");
        } else {
            System.out.println(">> Médico no encontrado.");
        }
    }
}