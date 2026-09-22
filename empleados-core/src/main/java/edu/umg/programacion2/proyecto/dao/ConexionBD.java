package edu.umg.programacion2.proyecto.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    private static final Properties PROPIEDADES = cargarPropiedades();

    private static Properties cargarPropiedades() {

        Properties propiedades = new Properties();

        try (InputStream entrada =
                     ConexionBD.class.getResourceAsStream("/db.properties")) {

            if (entrada == null) {
                throw new RuntimeException(
                        "No se encontró el archivo db.properties."
                );
            }

            propiedades.load(entrada);
            return propiedades;

        } catch (IOException e) {
            throw new RuntimeException(
                    "No se pudo cargar la configuración de la base de datos.",
                    e
            );
        }
    }

    public static Connection conectar() throws SQLException {

        String url = PROPIEDADES.getProperty("db.url");
        String usuario = PROPIEDADES.getProperty("db.usuario");
        String password = PROPIEDADES.getProperty("db.password");

        return DriverManager.getConnection(url, usuario, password);
    }
}