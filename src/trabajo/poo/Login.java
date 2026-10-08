package trabajo.poo;

public class Login {

    private Usuario usuarioValido = new Usuario("admin", "1234");

    public boolean validar(String usuario, String contrasena) {
        return usuarioValido.getUsuario().equals(usuario)
            && usuarioValido.getContrasena().equals(contrasena);
    }
}