package trabajo.poo;

public class Medico {
    private String codigo;
    private String dni;
    private String nombres;
    private String especialidad;
    private String telefono;

    // Constructor vacío
    public Medico() {
    }

    // Constructor con atributos
    public Medico(String codigo, String dni, String nombres, String especialidad, String telefono) {
        this.codigo = codigo;
        this.dni = dni;
        this.nombres = nombres;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Código: " + codigo + " | DNI: " + dni + " | Nombres: " + nombres + 
               " | Especialidad: " + especialidad + " | Teléfono: " + telefono;
    }
}
