package edu.umg.programacion2.proyecto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.umg.programacion2.proyecto.modelo.Empleado;

public class EmpleadoDAO {

    public Empleado crear(Empleado empleado) throws SQLException {

        String sql = "INSERT INTO empleados "
                + "(nombre, departamento, salario, fecha_contratacion, activo) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getDepartamento());
            ps.setBigDecimal(3, empleado.getSalario());
            ps.setDate(4, java.sql.Date.valueOf(empleado.getFechaContratacion()));
            ps.setBoolean(5, empleado.isActivo());

            ps.executeUpdate();
        }

        return empleado;
    }
}
