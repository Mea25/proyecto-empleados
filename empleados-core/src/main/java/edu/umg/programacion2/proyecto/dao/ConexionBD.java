package edu.umg.programacion2.proyecto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/proyecto_empleados?useSSL=false&serverTimezone=UTC";

    private static final String USUARIO = "root";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}