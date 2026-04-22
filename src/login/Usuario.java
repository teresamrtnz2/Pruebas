package login;

/**
 * Ejercicio de pruebas unitarias sobre la clase Usuario.
 * Se requiere implementar la clase UsuarioTest con dos conjuntos de pruebas
 * para el método validarRegistro():
 * <ul>
 *   <li>Pruebas de Clases de Equivalencia (CE): casos válidos e inválidos para cada campo.</li>
 *   <li>Pruebas de Valores Límite (VL): valores en los extremos de los rangos permitidos.</li>
 * </ul>
 * Se deberá crear un excel con las clases de equivalencia y los test cases aplicando la estrategia de pruebas pairwise en ambos casos.
 * Se debe entregar la url del proyecto en Github.
 */
public class Usuario {

    private String usuario;
    private String contrasena;
    private int edad;
    private String correo;

    public boolean validarUsuario() {
        if (usuario == null) return false;
        return usuario.matches(".{5,15}");
    }

    public boolean validarContrasena() {
        if (contrasena == null) return false;
        if (contrasena.length() < 8 || contrasena.length() > 20) return false;
        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneDigito    = false;

        for (int i = 0; i < contrasena.length(); i++) {
            char c = contrasena.charAt(i);
            if (!Character.isLetterOrDigit(c)) return false;
            if (Character.isUpperCase(c)) tieneMayuscula = true;
            if (Character.isLowerCase(c)) tieneMinuscula = true;
            if (Character.isDigit(c))     tieneDigito    = true;
        }
        return tieneMayuscula && tieneMinuscula && tieneDigito;
    }

    public boolean validarCorreo() {
        if (correo == null) return false;
        return correo.matches(".+@.+\\..+");
    }

    public boolean validarEdad() {
        return edad >= 18;
    }

    public boolean validarRegistro() {
        return validarUsuario()
                && validarContrasena()
                && validarCorreo()
                && validarEdad();
    }
}