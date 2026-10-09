package trabajo.poo;

public class Usuario {
    private int id;
    private String nombre;
    private String usuario;
    private String contrasena;

    public Usuario(int id, String nombre, String usuario, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Usuario: " + usuario;
    }
}