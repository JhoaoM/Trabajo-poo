package trabajo.poo;

import java.util.Scanner;

public class MenuMedico {

    private GestionMedico gestion;
    private Scanner scanner;

    public MenuMedico() {
        gestion = new GestionMedico();
    }

    // Recibe el Scanner del Menu principal (asi no hay dos Scanner)
    public void mostrarMenu(Scanner sc) {

        scanner = sc;
        int opcion;

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

            // Si escriben letras, no se cae el programa
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
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
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }

    // ---------- METODOS AUXILIARES DE LECTURA Y VALIDACION ----------

    // Texto que no puede quedar vacio
    private String leerTextoObligatorio(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("Este campo es obligatorio.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    // Texto con solo letras y espacios (nombres y especialidad)
    private String leerSoloLetras(String mensaje, String campo) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();

            if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println(campo + " solo debe contener letras y espacios.");
            }
        } while (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));
        return texto;
    }

    // DNI: exactamente 8 numeros
    private String leerDni(String mensaje) {
        String dni;
        do {
            System.out.print(mensaje);
            dni = scanner.nextLine().trim();

            if (!dni.matches("\\d{8}")) {
                System.out.println("DNI no valido. Debe contener exactamente 8 numeros.");
            }
        } while (!dni.matches("\\d{8}"));
        return dni;
    }

    // Telefono: exactamente 9 numeros
    private String leerTelefono(String mensaje) {
        String telefono;
        do {
            System.out.print(mensaje);
            telefono = scanner.nextLine().trim();

            if (!telefono.matches("\\d{9}")) {
                System.out.println("Telefono no valido. Debe contener exactamente 9 numeros.");
            }
        } while (!telefono.matches("\\d{9}"));
        return telefono;
    }

    // ---------- OPERACIONES ----------

    private void registrar() {
        System.out.println("\n--- REGISTRAR MEDICO ---");

        String cod = leerTextoObligatorio("Ingrese Codigo: ");
        String dni = leerDni("Ingrese DNI (8 digitos): ");
        String nom = leerSoloLetras("Ingrese Nombres completos: ", "Los nombres");
        String esp = leerSoloLetras("Ingrese Especialidad: ", "La especialidad");
        String tel = leerTelefono("Ingrese Telefono (9 digitos): ");

        Medico m = new Medico(cod, dni, nom, esp, tel);

        // Si falla, GestionMedico ya muestra el motivo (codigo o DNI repetido)
        if (gestion.registrarMedico(m)) {
            System.out.println(">> Medico registrado exitosamente!");
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
        System.out.print("Ingrese el codigo del medico a buscar: ");
        String cod = scanner.nextLine().trim();

        Medico m = gestion.buscarPorCodigo(cod);

        if (m != null) {
            System.out.println("Resultado: " + m);
        } else {
            System.out.println(">> Medico no encontrado.");
        }
    }

    private void modificar() {
        System.out.println("\n--- MODIFICAR MEDICO ---");
        System.out.print("Ingrese el codigo del medico a modificar: ");
        String cod = scanner.nextLine().trim();

        Medico m = gestion.buscarPorCodigo(cod);

        if (m == null) {
            System.out.println(">> Medico no encontrado.");
            return;
        }

        System.out.println("Medico seleccionado: " + m.getNombres());

        String nom = leerSoloLetras("Nuevos Nombres: ", "Los nombres");
        String esp = leerSoloLetras("Nueva Especialidad: ", "La especialidad");
        String tel = leerTelefono("Nuevo Telefono (9 digitos): ");

        if (gestion.modificarMedico(cod, nom, esp, tel)) {
            System.out.println(">> Medico modificado correctamente!");
        }
    }

    private void eliminar() {
        System.out.println("\n--- ELIMINAR MEDICO ---");
        System.out.print("Ingrese el codigo del medico a eliminar: ");
        String cod = scanner.nextLine().trim();

        if (gestion.eliminarMedico(cod)) {
            System.out.println(">> Medico eliminado correctamente!");
        } else {
            System.out.println(">> Medico no encontrado.");
        }
    }

    // Lo usa el modulo de citas para consultar los medicos registrados
    public GestionMedico getGestion() {
        return gestion;
    }
}