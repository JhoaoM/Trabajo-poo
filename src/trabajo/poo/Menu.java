package trabajo.poo;

import java.util.Scanner;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private Login login = new Login();

    public void iniciar() {
        if (iniciarSesion()) {
            mostrarMenu();
        } else {
            System.out.println("Demasiados intentos fallidos. Saliendo...");
        }
    }

    private boolean iniciarSesion() {
        int intentos = 0;
        while (intentos < 3) {
            System.out.print("Usuario: ");
            String usuario = sc.nextLine();
            System.out.print("Contrasena: ");
            String contrasena = sc.nextLine();

            if (login.validar(usuario, contrasena)) {
                System.out.println("Bienvenido!");
                return true;
            }
            intentos++;
            System.out.println("Datos incorrectos. Intentos: " + intentos + "/3");
        }
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

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    System.out.println("Pacientes (pendiente: Alison)");
                    break;
                case 2:
                    System.out.println("Usuarios (pendiente: Benjamin)");
                    break;
                case 3:
                    System.out.println("Medicamentos (pendiente: Leonel)");
                    break;
                case 4:
                    System.out.println("Personal medico (pendiente: Matthias)");
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
}