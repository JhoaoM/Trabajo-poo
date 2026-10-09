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
            System.out.println("  GESTION DE PERSONAL MEDICO");
            System.out.println("=================================");
            System.out.println("1. Registrar Medico");
            System.out.println("2. Listar Medicos");
            System.out.println("3. Buscar Medico por Codigo");
            System.out.println("4. Modificar Medico");
            System.out.println("5. Eliminar Medico");
            System.out.println("6. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");
            
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
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }

    private String leerTextoObligatorio(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println(">> Error: Este campo no puede quedar vacio. Intente de nuevo.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    private String leerDniValido() {
        String dni;
        do {
            System.out.print("Ingrese DNI (8 digitos): ");
            dni = scanner.nextLine().trim();
            if (!dni.matches("\\d{8}")) {
                System.out.println(">> Error: El DNI debe contener exactamente 8 digitos numericos.");
            }
        } while (!dni.matches("\\d{8}"));
        return dni;
    }

    private String leerTelefonoValido() {
        String tel;
        do {
            System.out.print("Ingrese Telefono (9 digitos): ");
            tel = scanner.nextLine().trim();
            if (!tel.matches("\\d{9}")) {
                System.out.println(">> Error: El telefono debe contener exactamente 9 digitos numericos.");
            }
        } while (!tel.matches("\\d{9}"));
        return tel;
    }

    private void registrar() {
        System.out.println("\n--- REGISTRAR MEDICO ---");
        String cod = leerTextoObligatorio("Ingrese Codigo: ");
        String dni = leerDniValido();
        String nom = leerTextoObligatorio("Ingrese Nombres completos: ");
        String esp = leerTextoObligatorio("Ingrese Especialidad: ");
        String tel = leerTelefonoValido();

        Medico m = new Medico(cod, dni, nom, esp, tel);
        if (gestion.registrarMedico(m)) {
            System.out.println(">> ¡Medico registrado exitosamente!");
        }
    }

    private void listar() {
        System.out.println("\n--- LISTA DE MEDICOS ---");
        if (gestion.obtenerMedicos().isEmpty()) {
            System.out.println("No hay medicos registrados.");
        } else {
            for (Medico m : gestion.obtenerMedicos()) {
                System.out.println(m);
            }
        }
    }

    private void buscar() {
        System.out.println("\n--- BUSCAR MEDICO ---");
        String cod = leerTextoObligatorio("Ingrese el codigo del medico a buscar: ");
        Medico m = gestion.buscarPorCodigo(cod);
        if (m != null) {
            System.out.println("Resultado: " + m);
        } else {
            System.out.println(">> Medico no encontrado.");
        }
    }

    private void modificar() {
        System.out.println("\n--- MODIFICAR MEDICO ---");
        String cod = leerTextoObligatorio("Ingrese el codigo del medico a modificar: ");
        Medico m = gestion.buscarPorCodigo(cod);
        if (m != null) {
            System.out.println("Medico seleccionado: " + m.getNombres());
            String nom = leerTextoObligatorio("Nuevos Nombres: ");
            String esp = leerTextoObligatorio("Nueva Especialidad: ");
            String tel = leerTelefonoValido();

            if (gestion.modificarMedico(cod, nom, esp, tel)) {
                System.out.println(">> ¡Medico modificado correctamente!");
            }
        } else {
            System.out.println(">> Medico no encontrado.");
        }
    }

    private void eliminar() {
        System.out.println("\n--- ELIMINAR MEDICO ---");
        String cod = leerTextoObligatorio("Ingrese el codigo del medico a eliminar: ");
        if (gestion.eliminarMedico(cod)) {
            System.out.println(">> ¡Medico eliminado correctamente!");
        } else {
            System.out.println(">> Medico no encontrado.");
        }
    }

    public static void main(String[] args) {
        MenuMedico menu = new MenuMedico();
        menu.mostrarMenu();
    }
}