package trabajo.poo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionUsuarios {

    private static final Path ARCHIVO = Paths.get("usuarios.txt");

    private ArrayList<Usuario> lista = new ArrayList<>();
    private Scanner sc;
    private int siguienteId = 1;

    public GestionUsuarios() {
        cargar();
    }

    public boolean hayUsuarios() {
        return !lista.isEmpty();
    }

    public void registrar(Scanner scanner) {
        sc = scanner;
        registrar();
    }

    public boolean validarCredenciales(String usuario, String contrasena) {
        Usuario u = buscarPorUsuario(usuario);
        return u != null && u.getContrasena().equals(contrasena);
    }

    public void menu(Scanner scanner) {
        sc = scanner;
        int opcion;
        do {
            System.out.println("\n--- USUARIOS ---");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar");
            System.out.println("4. Modificar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver al menu principal");
            System.out.print("Opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> modificar();
                case 5 -> eliminar();
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    public void registrar() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Usuario: ");
        String usuario = sc.nextLine().trim();
        System.out.print("Contrasena: ");
        String contrasena = sc.nextLine().trim();

        if (nombre.isBlank() || usuario.isBlank() || contrasena.isBlank()) {
            System.out.println("Todos los datos son obligatorios.");
            return;
        }
        if (nombre.contains(";") || usuario.contains(";") || contrasena.contains(";")) {
            System.out.println("No se permite el caracter ; en los datos.");
            return;
        }
        if (buscarPorUsuario(usuario) != null) {
            System.out.println("Ese usuario ya existe.");
            return;
        }

        lista.add(new Usuario(siguienteId++, nombre, usuario, contrasena));
        guardar();
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
        Usuario u = buscarPorUsuario(sc.nextLine().trim());
        if (u == null) {
            System.out.println("No encontrado.");
        } else {
            System.out.println(u);
        }
    }

    public void modificar() {
        System.out.print("Usuario a modificar: ");
        Usuario u = buscarPorUsuario(sc.nextLine().trim());
        if (u == null) {
            System.out.println("No encontrado.");
            return;
        }
        System.out.print("Nuevo nombre (Enter para no cambiar): ");
        String nombre = sc.nextLine().trim();
        System.out.print("Nueva contrasena (Enter para no cambiar): ");
        String contrasena = sc.nextLine().trim();

        if (nombre.contains(";") || contrasena.contains(";")) {
            System.out.println("No se permite el caracter ; en los datos.");
            return;
        }
        if (!nombre.isBlank()) {
            u.setNombre(nombre);
        }
        if (!contrasena.isBlank()) {
            u.setContrasena(contrasena);
        }
        guardar();
        System.out.println("Usuario modificado.");
    }

    public void eliminar() {
        System.out.print("Usuario a eliminar: ");
        Usuario u = buscarPorUsuario(sc.nextLine().trim());
        if (u == null) {
            System.out.println("No encontrado.");
            return;
        }
        lista.remove(u);
        guardar();
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

    private void guardar() {
        List<String> lineas = new ArrayList<>();
        for (Usuario u : lista) {
            lineas.add(u.getId() + ";" + u.getNombre() + ";"
                    + u.getUsuario() + ";" + u.getContrasena());
        }
        try {
            Files.write(ARCHIVO, lineas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo de usuarios.");
        }
    }

    private void cargar() {
        if (!Files.exists(ARCHIVO)) {
            return;
        }
        try {
            for (String linea : Files.readAllLines(ARCHIVO, StandardCharsets.UTF_8)) {
                String[] datos = linea.split(";", -1);
                if (datos.length != 4) {
                    continue;
                }
                try {
                    int id = Integer.parseInt(datos[0]);
                    lista.add(new Usuario(id, datos[1], datos[2], datos[3]));
                    if (id >= siguienteId) {
                        siguienteId = id + 1;
                    }
                } catch (NumberFormatException e) {
                    
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de usuarios.");
        }
    }
}