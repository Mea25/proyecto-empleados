package edu.umg.programacion2.proyecto.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import edu.umg.programacion2.proyecto.dao.EmpleadoDAO;
import edu.umg.programacion2.proyecto.modelo.Empleado;

public class VentanaPrincipal {

    private JFrame ventana;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private EmpleadoDAO dao;

    public VentanaPrincipal() {

        ventana = new JFrame("Gestión de Empleados");

        ventana.setSize(900, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        dao = new EmpleadoDAO();

        // Título
        JLabel titulo = new JLabel(
                "GESTIÓN DE EMPLEADOS",
                SwingConstants.CENTER
        );

        ventana.add(titulo, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {
        	    "ID",
        	    "Nombre",
        	    "Departamento",
        	    "Correo",
        	    "Salario",
        	    "Fecha contratación",
        	    "Activo"
        	};

        modeloTabla = new DefaultTableModel(columnas, 0);

        tabla = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tabla);

        ventana.add(scroll, BorderLayout.CENTER);

        // Botones
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        javax.swing.JPanel panelBotones = new javax.swing.JPanel(
                new FlowLayout()
        );

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        ventana.add(panelBotones, BorderLayout.SOUTH);

        // Registrar
        btnRegistrar.addActionListener(e -> {

            FormularioEmpleado formulario =
                    new FormularioEmpleado(
                            null,
                            this::cargarEmpleados
                    );

            formulario.mostrar();
        });

        // Editar
        btnEditar.addActionListener(e -> editarEmpleado());

        // Eliminar
        btnEliminar.addActionListener(e -> eliminarEmpleado());

        cargarEmpleados();
    }

    private void cargarEmpleados() {

        modeloTabla.setRowCount(0);

        try {

            List<Empleado> empleados = dao.listarTodos();

            for (Empleado empleado : empleados) {

            	modeloTabla.addRow(new Object[] {
            		    empleado.getId(),
            		    empleado.getNombre(),
            		    empleado.getDepartamento(),
            		    empleado.getCorreo(),
            		    empleado.getSalario(),
            		    empleado.getFechaContratacion(),
            		    empleado.isActivo() ? "Sí" : "No"
            		});
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Error al cargar los empleados:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void editarEmpleado() {

        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Seleccione un empleado para editar."
            );

            return;
        }

        int id = (int) modeloTabla.getValueAt(
                filaSeleccionada,
                0
        );

        try {

            java.util.Optional<Empleado> resultado =
                    dao.buscarPorId(id);

            if (resultado.isPresent()) {

                Empleado empleado = resultado.get();

                FormularioEmpleado formulario =
                        new FormularioEmpleado(
                                empleado,
                                this::cargarEmpleados
                        );

                formulario.mostrar();

            } else {

                JOptionPane.showMessageDialog(
                        ventana,
                        "No se encontró el empleado."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Error al buscar empleado:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarEmpleado() {

        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Seleccione un empleado para eliminar."
            );

            return;
        }

        int id = (int) modeloTabla.getValueAt(
                filaSeleccionada,
                0
        );

        String[] opciones = {"Sí", "No"};

        int respuesta = JOptionPane.showOptionDialog(
            ventana,
            "¿Está seguro de eliminar este empleado?",
            "Confirmar eliminación",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            opciones,
            opciones[1]
        );

        if (respuesta != 0) {
            return;
        }

        try {

            boolean eliminado = dao.eliminar(id);

            if (eliminado) {

                JOptionPane.showMessageDialog(
                        ventana,
                        "Empleado eliminado correctamente."
                );

                cargarEmpleados();

            } else {

                JOptionPane.showMessageDialog(
                        ventana,
                        "No se encontró el empleado."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Error al eliminar empleado:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void mostrar() {
        ventana.setVisible(true);
    }

    public static void main(String[] args) {

        VentanaPrincipal ventana =
                new VentanaPrincipal();

        ventana.mostrar();
    }
}