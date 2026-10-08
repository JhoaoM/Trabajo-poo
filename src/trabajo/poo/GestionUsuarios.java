package trabajo.poo;

import java.util.ArrayList;
import java.util.Scanner;

public class GestionUsuarios {
    private ArrayList<Usuario> lista = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private int siguienteId = 1;

    public void menu() {
        int opcion;
        do {
            System.out.println("\n--- USUARIOS ---");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar");
            System.out.println("4. Modificar");
            System.out.println("5. Eliminar");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> modificar();
                case 5 -> eliminar();
                case 0 -> System.out.println("Saliendo de usuarios.");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    public void registrar() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contrasena: ");
        String contrasena = sc.nextLine();

        if (nombre.isBlank() || usuario.isBlank() || contrasena.isBlank()) {
            System.out.println("Todos los datos son obligatorios.");
            return;
        }
        if (buscarPorUsuario(usuario) != null) {
            System.out.println("Ese usuario ya existe.");
            return;
        }

        lista.add(new Usuario(siguienteId++, nombre, usuario, contrasena));
        System.out.println("Usuario registrado.");
    }

    public void listar() {
        if (lista.isEmpty()) {
            System.out.println("No hay usuarios.");
            return;
        }
        for (Usuario u : lista) {
            System.out.println(u);
        }
    }

    public void buscar() {
        System.out.print("Usuario a buscar: ");
        Usuario u = buscarPorUsuario(sc.nextLine());
        if (u == null) {
            System.out.println("No encontrado.");
        } else {
            System.out.println(u);
        }
    }

    public void modificar() {
        System.out.print("Usuario a modificar: ");
        Usuario u = buscarPorUsuario(sc.nextLine());
        if (u == null) {
            System.out.println("No encontrado.");
            return;
        }
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nueva contrasena: ");
        String contrasena = sc.nextLine();
        if (!nombre.isBlank()) {
            u.setNombre(nombre);
        }
        if (!contrasena.isBlank()) {
            u.setContrasena(contrasena);
        }
        System.out.println("Usuario modificado.");
    }

    public void eliminar() {
        System.out.print("Usuario a eliminar: ");
        Usuario u = buscarPorUsuario(sc.nextLine());
        if (u == null) {
            System.out.println("No encontrado.");
            return;
        }
        lista.remove(u);
        System.out.println("Usuario eliminado.");
    }

    private Usuario buscarPorUsuario(String usuario) {
        for (Usuario u : lista) {
            if (u.getUsuario().equalsIgnoreCase(usuario)) {
                return u;
            }
        }
        return null;
    }
}