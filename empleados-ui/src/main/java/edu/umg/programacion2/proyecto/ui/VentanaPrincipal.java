package edu.umg.programacion2.proyecto.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import edu.umg.programacion2.proyecto.dao.EmpleadoDAO;
import edu.umg.programacion2.proyecto.modelo.Empleado;

public class VentanaPrincipal {

    public static void main(String[] args) {

        JFrame ventana = new JFrame("Gestión de Empleados");

        ventana.setSize(900, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        // Título
        JLabel titulo = new JLabel(
                "GESTIÓN DE EMPLEADOS",
                SwingConstants.CENTER
        );

        ventana.add(titulo, BorderLayout.NORTH);

        // Columnas de la tabla
        String[] columnas = {
            "ID",
            "Nombre",
            "Departamento",
            "Salario",
            "Fecha contratación",
            "Activo"
        };

        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

        JTable tabla = new JTable(modeloTabla);

        // Obtener empleados desde la base de datos
        EmpleadoDAO dao = new EmpleadoDAO();

        try {

            List<Empleado> empleados = dao.listarTodos();

            for (Empleado empleado : empleados) {

                modeloTabla.addRow(new Object[] {
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getDepartamento(),
                    empleado.getSalario(),
                    empleado.getFechaContratacion(),
                    empleado.isActivo() ? "Sí" : "No"
                });
            }

        } catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                    ventana,
                    "Error al cargar los empleados:\n" + e.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }

        JScrollPane scroll = new JScrollPane(tabla);

        ventana.add(scroll, BorderLayout.CENTER);

        ventana.setVisible(true);
    }
}