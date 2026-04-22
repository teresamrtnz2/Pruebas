package test;

import login.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

public class UsuarioTest {

    // 🔧 Helper para setear atributos privados
    private void setField(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Usuario crearUsuario(String usuario, String contrasena, String correo, int edad) {
        Usuario u = new Usuario();
        setField(u, "usuario", usuario);
        setField(u, "contrasena", contrasena);
        setField(u, "correo", correo);
        setField(u, "edad", edad);
        return u;
    }

    // =========================
    // ✅ CLASES DE EQUIVALENCIA
    // =========================

    @Test
    void CE_usuario_valido() {
        Usuario u = crearUsuario("usuario1", "Abcdef12", "test@mail.com", 18);
        assertTrue(u.validarRegistro());
    }

    @Test
    void CE_usuario_corto() {
        Usuario u = crearUsuario("abc", "Abcdef12", "test@mail.com", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void CE_usuario_null() {
        Usuario u = crearUsuario(null, "Abcdef12", "test@mail.com", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void CE_usuario_largo() {
        Usuario u = crearUsuario("usuario_muy_largo_123", "Abcdef12", "test@mail.com", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void CE_password_sin_mayuscula() {
        Usuario u = crearUsuario("usuario1", "abcdef12", "test@mail.com", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void CE_password_sin_minuscula() {
        Usuario u = crearUsuario("usuario1", "ABCDEF12", "test@mail.com", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void CE_password_sin_digito() {
        Usuario u = crearUsuario("usuario1", "Abcdefgh", "test@mail.com", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void CE_password_con_simbolo() {
        Usuario u = crearUsuario("usuario1", "Abcdef12!", "test@mail.com", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void CE_correo_invalido() {
        Usuario u = crearUsuario("usuario1", "Abcdef12", "correo.com", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void CE_edad_invalida() {
        Usuario u = crearUsuario("usuario1", "Abcdef12", "test@mail.com", 17);
        assertFalse(u.validarRegistro());
    }

    // =========================
    // 📏 VALORES LÍMITE
    // =========================

    @Test
    void VL_usuario_4_caracteres() {
        Usuario u = crearUsuario("abcd", "Abcdef12", "a@b.c", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void VL_usuario_5_caracteres() {
        Usuario u = crearUsuario("abcde", "Abcdef12", "a@b.c", 18);
        assertTrue(u.validarRegistro());
    }

    @Test
    void VL_usuario_15_caracteres() {
        Usuario u = crearUsuario("abcdefghijklmno", "Abcdef12", "a@b.c", 18);
        assertTrue(u.validarRegistro());
    }

    @Test
    void VL_usuario_16_caracteres() {
        Usuario u = crearUsuario("abcdefghijklmnop", "Abcdef12", "a@b.c", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void VL_password_7_caracteres() {
        Usuario u = crearUsuario("usuario1", "Abcdef1", "a@b.c", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void VL_password_8_caracteres() {
        Usuario u = crearUsuario("usuario1", "Abcdef12", "a@b.c", 18);
        assertTrue(u.validarRegistro());
    }

    @Test
    void VL_password_20_caracteres() {
        Usuario u = crearUsuario("usuario1", "Abcdefghijklmnopqr12", "a@b.c", 18);
        assertTrue(u.validarRegistro());
    }

    @Test
    void VL_password_21_caracteres() {
        Usuario u = crearUsuario("usuario1", "Abcdefghijklmnopqrstu12", "a@b.c", 18);
        assertFalse(u.validarRegistro());
    }

    @Test
    void VL_edad_17() {
        Usuario u = crearUsuario("usuario1", "Abcdef12", "a@b.c", 17);
        assertFalse(u.validarRegistro());
    }

    @Test
    void VL_edad_18() {
        Usuario u = crearUsuario("usuario1", "Abcdef12", "a@b.c", 18);
        assertTrue(u.validarRegistro());
    }

    @Test
    void VL_correo_invalido() {
        Usuario u = crearUsuario("usuario1", "Abcdef12", "a@b", 18);
        assertFalse(u.validarRegistro());
    }
}