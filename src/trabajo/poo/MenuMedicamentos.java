package trabajo.poo;

import java.util.Scanner;

public class MenuMedicamentos {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Medicamentos");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    GestionMedicamentos.iniciar(sc);
                    break;
                case 2:
                    System.out.println("Adios.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 2);

        sc.close();
    }
}