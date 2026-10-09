package trabajo.poo;

import java.util.Scanner;

public class Menu {

    // Un solo Scanner para todo el programa
    private Scanner sc = new Scanner(System.in);

    // Se crean UNA sola vez, para que las listas no se borren al volver al menu
    // (gestionUsuarios va primero porque el Login la necesita)
    private GestionUsuarios gestionUsuarios = new GestionUsuarios();
    private Login login = new Login(gestionUsuarios);
    private MenuPaciente menuPaciente = new MenuPaciente();
    private MenuMedico menuMedico = new MenuMedico();

    // Pantalla de inicio: iniciar sesion, registrarse o salir
    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== BIENVENIDO ===");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    if (iniciarSesion()) {
                        mostrarMenu();
                        salir = true; // al salir del menu principal se cierra el programa
                    }
                    break;
                case 2:
                    gestionUsuarios.registrar(sc);
                    break;
                case 0:
                    System.out.println("Hasta luego!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private boolean iniciarSesion() {
        if (!gestionUsuarios.hayUsuarios()) {
            System.out.println("No hay usuarios registrados. Registrese primero.");
            return false;
        }

        int intentos = 0;
        while (intentos < 3) {
            System.out.print("Usuario: ");
            String usuario = sc.nextLine().trim();
            System.out.print("Contrasena: ");
            String contrasena = sc.nextLine().trim();

            if (login.validar(usuario, contrasena)) {
                System.out.println("Bienvenido!");
                return true;
            }
            intentos++;
            System.out.println("Datos incorrectos. Intentos: " + intentos + "/3");
        }
        System.out.println("Demasiados intentos fallidos.");
        return false;
    }

    private void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Pacientes");
            System.out.println("2. Usuarios");
            System.out.println("3. Medicamentos");
            System.out.println("4. Personal medico");
            System.out.println("5. Citas");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    menuPaciente.mostrarMenu(sc);
                    break;
                case 2:
                    gestionUsuarios.menu(sc);
                    break;
                case 3:
                    GestionMedicamentos.iniciar(sc);
                    break;
                case 4:
                    menuMedico.mostrarMenu(sc);
                    break;
                case 5:
                    System.out.println("Citas (pendiente: todos)");
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    // Lee una opcion numerica sin que el programa se caiga si escriben letras
    private int leerOpcion() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}