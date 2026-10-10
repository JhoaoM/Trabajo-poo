package trabajo.poo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class GestionMedicamentos {

    private static final ArrayList<Medicamentos> medicamentos = new ArrayList<>();
    private static Scanner sc;

    // Lo llama el Menu principal
    public static void iniciar(Scanner scanner) {
        sc = scanner;
        int opcion;

        do {
            System.out.println("\n===== MEDICAMENTOS =====");
            System.out.println("1. Registrar medicamento");
            System.out.println("2. Listar medicamentos");
            System.out.println("3. Buscar medicamento");
            System.out.println("4. Modificar medicamento");
            System.out.println("5. Eliminar medicamento");
            System.out.println("6. Volver al menu principal");

            opcion = leerEntero("Seleccione una opcion: ");

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
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 6);
    }

    // ---------- METODOS DE LECTURA CON VALIDACION ----------

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Ingrese un numero valido.");
            }
        }
    }

    // Texto que no puede quedar vacio
    private static String leerTextoObligatorio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("ERROR: Este campo es obligatorio.");
        }
    }

    // Cantidad mayor que 0. Si opcional es true y presionan Enter, devuelve null
    private static Integer leerCantidad(String mensaje, boolean opcional) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();

            if (texto.isEmpty() && opcional) {
                return null;
            }
            try {
                int cantidad = Integer.parseInt(texto);
                if (cantidad > 0) {
                    return cantidad;
                }
                System.out.println("ERROR: La cantidad debe ser mayor que 0.");
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Ingrese un numero valido.");
            }
        }
    }

    // Fecha real dd/mm/aaaa. Si opcional es true y presionan Enter, devuelve null
    private static LocalDate leerFecha(String mensaje, boolean opcional) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();

            if (texto.isEmpty() && opcional) {
                return null;
            }
            try {
                return LocalDate.parse(texto, Medicamentos.FORMATO);
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: Fecha invalida. Use dd/mm/aaaa (ejemplo: 25/12/2027).");
            }
        }
    }

    // ---------- METODOS PARA LA INTERFAZ GRAFICA ----------
    // Devuelven un mensaje de error, o null si todo salio bien

    public static ArrayList<Medicamentos> getMedicamentos() {
        return medicamentos;
    }

    public static String registrarDatos(String codigo, String nombre, String descripcion,
                                        String cantidad, String fecha) {
        if (codigo.isBlank()) {
            return "El codigo es obligatorio.";
        }
        if (buscarPorCodigo(codigo.trim()) != null) {
            return "El codigo ya existe.";
        }
        String error = validarDatos(nombre, cantidad, fecha);
        if (error != null) {
            return error;
        }
        medicamentos.add(new Medicamentos(codigo.trim(), nombre.trim(), descripcion.trim(),
                Integer.parseInt(cantidad.trim()),
                LocalDate.parse(fecha.trim(), Medicamentos.FORMATO)));
        return null;
    }

    public static String modificarDatos(String codigo, String nombre, String descripcion,
                                        String cantidad, String fecha) {
        Medicamentos m = buscarPorCodigo(codigo);
        if (m == null) {
            return "Medicamento no encontrado.";
        }
        String error = validarDatos(nombre, cantidad, fecha);
        if (error != null) {
            return error;
        }
        m.setNombre(nombre.trim());
        m.setDescripcion(descripcion.trim());
        m.setCantidad(Integer.parseInt(cantidad.trim()));
        m.setFechaVencimiento(LocalDate.parse(fecha.trim(), Medicamentos.FORMATO));
        return null;
    }

    public static String eliminarMedicamento(String codigo) {
        Medicamentos m = buscarPorCodigo(codigo);
        if (m == null) {
            return "Medicamento no encontrado.";
        }
        medicamentos.remove(m);
        return null;
    }

    private static String validarDatos(String nombre, String cantidad, String fecha) {
        if (nombre.isBlank()) {
            return "El nombre es obligatorio.";
        }
        try {
            if (Integer.parseInt(cantidad.trim()) <= 0) {
                return "La cantidad debe ser mayor que 0.";
            }
        } catch (NumberFormatException e) {
            return "La cantidad debe ser un numero entero.";
        }
        try {
            LocalDate.parse(fecha.trim(), Medicamentos.FORMATO);
        } catch (DateTimeParseException e) {
            return "Fecha invalida. Use DD/MM/AAAA (ejemplo: 25/12/2027).";
        }
        return null;
    }

    // ---------- METODOS AUXILIARES ----------

    // Busca por codigo exacto (sin importar mayusculas); devuelve null si no existe
    private static Medicamentos buscarPorCodigo(String codigo) {
        for (Medicamentos m : medicamentos) {
            if (m.getCodigo().equalsIgnoreCase(codigo)) {
                return m;
            }
        }
        return null;
    }

    // Pide un codigo y devuelve el medicamento, o null si no existe
    private static Medicamentos pedirMedicamento() {
        String codigo = leerTextoObligatorio("Ingrese el codigo: ");
        Medicamentos m = buscarPorCodigo(codigo);
        if (m == null) {
            System.out.println("Medicamento no encontrado.");
        }
        return m;
    }

    // ---------- REGISTRAR ----------
    private static void registrar() {
        System.out.println("\n--- REGISTRAR MEDICAMENTO ---");

        String codigo = leerTextoObligatorio("Codigo: ");

        if (buscarPorCodigo(codigo) != null) {
            System.out.println("ERROR: El codigo ya existe.");
            return;
        }

        String nombre = leerTextoObligatorio("Nombre: ");

        System.out.print("Descripcion (opcional): ");
        String descripcion = sc.nextLine().trim();

        int cantidad = leerCantidad("Cantidad: ", false);
        LocalDate fecha = leerFecha("Fecha de vencimiento (dd/mm/aaaa): ", false);

        medicamentos.add(new Medicamentos(codigo, nombre, descripcion, cantidad, fecha));

        System.out.println("Medicamento registrado correctamente.");
        if (fecha.isBefore(LocalDate.now())) {
            System.out.println("AVISO: Este medicamento ya esta vencido.");
        }
    }

    // ---------- LISTAR ----------
    private static void listar() {
        System.out.println("\n--- LISTA DE MEDICAMENTOS ---");

        if (medicamentos.isEmpty()) {
            System.out.println("No hay medicamentos registrados.");
            return;
        }

        for (Medicamentos m : medicamentos) {
            System.out.println(m);
        }
        System.out.println("----------------------------");
        System.out.println("Total: " + medicamentos.size() + " medicamento(s).");
    }

    // ---------- BUSCAR ----------
    private static void buscar() {
        System.out.println("\n--- BUSCAR MEDICAMENTO ---");

        String texto = leerTextoObligatorio("Ingrese el codigo o parte del nombre: ").toLowerCase();

        int encontrados = 0;
        for (Medicamentos m : medicamentos) {
            boolean coincide = m.getCodigo().equalsIgnoreCase(texto)
                    || m.getNombre().toLowerCase().contains(texto);
            if (coincide) {
                System.out.println(m);
                encontrados++;
            }
        }

        if (encontrados == 0) {
            System.out.println("Medicamento no encontrado.");
        } else {
            System.out.println("----------------------------");
            System.out.println("Resultados: " + encontrados);
        }
    }

    // ---------- MODIFICAR ----------
    private static void modificar() {
        System.out.println("\n--- MODIFICAR MEDICAMENTO ---");

        Medicamentos m = pedirMedicamento();
        if (m == null) {
            return;
        }

        System.out.println(m);
        System.out.println("(Presione Enter para dejar el valor actual)");

        System.out.print("Nuevo nombre [" + m.getNombre() + "]: ");
        String nombre = sc.nextLine().trim();
        if (!nombre.isEmpty()) {
            m.setNombre(nombre);
        }

        System.out.print("Nueva descripcion [" + m.getDescripcion() + "]: ");
        String descripcion = sc.nextLine().trim();
        if (!descripcion.isEmpty()) {
            m.setDescripcion(descripcion);
        }

        Integer cantidad = leerCantidad("Nueva cantidad [" + m.getCantidad() + "]: ", true);
        if (cantidad != null) {
            m.setCantidad(cantidad);
        }

        LocalDate fecha = leerFecha("Nueva fecha de vencimiento ["
                + m.getFechaVencimiento().format(Medicamentos.FORMATO) + "]: ", true);
        if (fecha != null) {
            m.setFechaVencimiento(fecha);
        }

        System.out.println("Medicamento modificado correctamente.");
    }

    // ---------- ELIMINAR ----------
    private static void eliminar() {
        System.out.println("\n--- ELIMINAR MEDICAMENTO ---");

        Medicamentos m = pedirMedicamento();
        if (m == null) {
            return;
        }

        System.out.println(m);
        System.out.print("Seguro que desea eliminarlo? (S/N): ");
        String respuesta = sc.nextLine().trim();

        if (respuesta.equalsIgnoreCase("S")) {
            medicamentos.remove(m);
            System.out.println("Medicamento eliminado correctamente.");
        } else {
            System.out.println("Eliminacion cancelada.");
        }
    }
}
