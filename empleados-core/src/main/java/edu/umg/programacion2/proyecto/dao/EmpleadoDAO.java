package edu.umg.programacion2.proyecto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Empleado> listarTodos() throws SQLException {

        String sql = "SELECT * FROM empleados";

        List<Empleado> empleados = new ArrayList<>();

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Empleado empleado = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("departamento"),
                        rs.getBigDecimal("salario"),
                        rs.getDate("fecha_contratacion").toLocalDate(),
                        rs.getBoolean("activo")
                );

                empleados.add(empleado);
            }
        }

        return empleados;
    }
    
    public Optional<Empleado> buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM empleados WHERE id = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Empleado empleado = new Empleado(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("departamento"),
                            rs.getBigDecimal("salario"),
                            rs.getDate("fecha_contratacion").toLocalDate(),
                            rs.getBoolean("activo")
                    );

                    return Optional.of(empleado);
                }
            }
        }

        return Optional.empty();
    }
}