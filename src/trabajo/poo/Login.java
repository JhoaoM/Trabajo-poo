package trabajo.poo;

public class Login {

    private GestionUsuarios gestionUsuarios;

    public Login(GestionUsuarios gestionUsuarios) {
        this.gestionUsuarios = gestionUsuarios;
    }

    public boolean validar(String usuario, String contrasena) {
        return gestionUsuarios.validarCredenciales(usuario, contrasena);
    }
}