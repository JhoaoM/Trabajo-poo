package trabajo.poo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Medicamentos {

    // Formato estricto dd/mm/aaaa: rechaza fechas que no existen
    public static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private final String codigo;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private LocalDate fechaVencimiento;

    public Medicamentos(String codigo, String nombre, String descripcion,
                       int cantidad, LocalDate fechaVencimiento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean estaVencido() {
        return fechaVencimiento.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        String estado = estaVencido() ? "  ** VENCIDO **" : "";
        return "----------------------------\n"
                + "Codigo: " + codigo + "\n"
                + "Nombre: " + nombre + "\n"
                + "Descripcion: " + (descripcion.isEmpty() ? "(sin descripcion)" : descripcion) + "\n"
                + "Cantidad: " + cantidad + "\n"
                + "Vencimiento: " + fechaVencimiento.format(FORMATO) + estado;
    }
}