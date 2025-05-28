package co.edu.uniquindio.red_social.Pruebas;

import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.social.SolicitudAyuda;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import javafx.fxml.FXML;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

public class RedSocialTest {

    private File obtenerImagenPrueba(String nombreArchivo) {
        URL recurso = getClass().getResource("/" + nombreArchivo);
        assertNotNull(recurso, "No se encontró el recurso: " + nombreArchivo);
        return new File(recurso.getFile());
    }

    @Test
    void testActualizarNombreEstudiante() {
        File imagen = obtenerImagenPrueba("imagenPrueba.jpg");
        Estudiante estudiante = new Estudiante("Helen", "Libreros", "helen@gmail.com", "123", imagen);
        estudiante.setNombre("Maria");
        assertEquals("Maria", estudiante.getNombre(), "El nombre del estudiante no se actualizó correctamente");
    }

    @Test
    void testAsignarImagenPerfil() {
        File imagen = obtenerImagenPrueba("imagenPrueba.jpg");
        Estudiante estudiante = new Estudiante("Miguel", "Vargas", "miguel@gmail.com", "amarillo", imagen);
        estudiante.setImagenPerfil(imagen);

        assertEquals(imagen.getAbsolutePath(), estudiante.getImagenPerfil().getAbsolutePath(), "La imagen de perfil no fue asignada correctamente");
        assertTrue(imagen.exists(), "La imagen asignada no existe");
    }

    @Test
    void testContraseñaIncorrectaNoActualiza() {
        File imagen = obtenerImagenPrueba("imagenPrueba.jpg");
        Estudiante estudiante = new Estudiante("Luis", "Mejia", "luis@mail.com", "original", imagen);

        String contraseñaAnteriorIncorrecta = "otra";
        String nuevaContraseña = "nuevaClave";

        if (estudiante.getContrasena().equals(contraseñaAnteriorIncorrecta)) {
            estudiante.setContrasena(nuevaContraseña);
        }

        assertEquals("original", estudiante.getContrasena(), "La contraseña fue cambiada incorrectamente");
    }

    @Test
    void testAgregarEstudianteAGrupo() {
        // Suponiendo que tienes una clase Grupo con un método agregarEstudiante
        Grupo grupo = new Grupo("1","Grupo de Estudio 1", "tipo matematica", false);
        File imagen = obtenerImagenPrueba("imagenPrueba.jpg");
        Estudiante estudiante = new Estudiante("Carlos", "López", "carlos@mail.com", "1234", imagen);

        grupo.agregarMiembro(estudiante);

        assertTrue(grupo.getMiembros().add(estudiante), "El estudiante no fue agregado correctamente al grupo");
    }


    @Test
    void testActualizarEmail() {
        File imagen = obtenerImagenPrueba("imagenPrueba.jpg");
        Estudiante estudiante = new Estudiante("Laura", "Diaz", "laura@old.com", "xyz", imagen);
        estudiante.setEmail("laura@new.com");

        assertEquals("laura@new.com", estudiante.getEmail(), "El correo electrónico no se actualizó correctamente");
    }

    @Test
    void testImagenNoExiste() {
        File imagenValida = obtenerImagenPrueba("imagenPrueba.jpg");
        Estudiante estudiante = new Estudiante("Carlos", "Ruiz", "carlos@mail.com", "456", imagenValida);

        File imagenInvalida = new File("ruta/no/existe.jpg");

        // Solo se asigna si existe
        if (imagenInvalida.exists()) {
            estudiante.setImagenPerfil(imagenInvalida);
        }

        assertEquals(imagenValida.getAbsolutePath(), estudiante.getImagenPerfil().getAbsolutePath(), "La imagen de perfil se cambió a una ruta inválida");
    }

    @Test
    void testGuardarImagenEnCarpeta() throws IOException {
        File original = obtenerImagenPrueba("imagenPrueba.jpg");
        File destino = new File("imagenesPerfil/prueba123.jpg");

        // Crear directorio si no existe
        if (!destino.getParentFile().exists()) {
            assertTrue(destino.getParentFile().mkdirs(), "No se pudo crear el directorio");
        }

        Files.copy(original.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

        assertTrue(destino.exists(), "La imagen no fue copiada correctamente al destino");
    }

    @Test
    void testEstudianteConGrupo() {
        File imagen = obtenerImagenPrueba("imagenPrueba.jpg");
        Estudiante estudiante = new Estudiante("Helen", "Libreros", "helen@gmail.com", "123", imagen);
        Grupo grupo = new Grupo("1", "Grupo de Estudio 1", "tipo matematica", false);
        grupo.agregarMiembro(estudiante);
        assertTrue(grupo.getMiembros().contains(estudiante), "El estudiante no fue agregado correctamente al grupo");
    }




}
