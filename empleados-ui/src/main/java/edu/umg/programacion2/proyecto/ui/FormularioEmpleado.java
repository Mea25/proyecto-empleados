package edu.umg.programacion2.proyecto.ui;

import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import edu.umg.programacion2.proyecto.dao.EmpleadoDAO;
import edu.umg.programacion2.proyecto.modelo.Empleado;

public class FormularioEmpleado {

    private JFrame ventana;

    private JTextField txtNombre;
    private JTextField txtDepartamento;
    private JTextField txtSalario;
    private JTextField txtFechaContratacion;

    private JCheckBox chkActivo;

    private JButton btnGuardar;
    private JButton btnCancelar;

    private Empleado empleadoEditar;

    private Runnable alGuardar;

    // Constructor para REGISTRAR
    public FormularioEmpleado() {
        this(null, null);
    }

    // Constructor para EDITAR
    public FormularioEmpleado(Empleado empleado, Runnable alGuardar) {

        this.empleadoEditar = empleado;
        this.alGuardar = alGuardar;

        ventana = new JFrame(
                empleado == null ? "Registrar Empleado" : "Editar Empleado"
        );

        ventana.setSize(450, 350);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ventana.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblDepartamento = new JLabel("Departamento:");
        txtDepartamento = new JTextField();

        JLabel lblSalario = new JLabel("Salario:");
        txtSalario = new JTextField();

        JLabel lblFecha = new JLabel("Fecha contratación:");
        txtFechaContratacion = new JTextField();

        JLabel lblActivo = new JLabel("Activo:");
        chkActivo = new JCheckBox();

        chkActivo.setSelected(true);

        btnGuardar = new JButton(
                empleado == null ? "Guardar" : "Actualizar"
        );

        btnGuardar.addActionListener(e -> guardarEmpleado());

        btnCancelar = new JButton("Cancelar");

        btnCancelar.addActionListener(e -> ventana.dispose());

        ventana.add(lblNombre);
        ventana.add(txtNombre);

        ventana.add(lblDepartamento);
        ventana.add(txtDepartamento);

        ventana.add(lblSalario);
        ventana.add(txtSalario);

        ventana.add(lblFecha);
        ventana.add(txtFechaContratacion);

        ventana.add(lblActivo);
        ventana.add(chkActivo);

        ventana.add(btnGuardar);
        ventana.add(btnCancelar);

        // Si estamos editando, cargar los datos existentes
        if (empleadoEditar != null) {

            txtNombre.setText(empleadoEditar.getNombre());
            txtDepartamento.setText(empleadoEditar.getDepartamento());
            txtSalario.setText(empleadoEditar.getSalario().toString());
            txtFechaContratacion.setText(
                    empleadoEditar.getFechaContratacion().toString()
            );
            chkActivo.setSelected(empleadoEditar.isActivo());
        }
    }

    public void mostrar() {
        ventana.setVisible(true);
    }

    public static void main(String[] args) {

        FormularioEmpleado formulario = new FormularioEmpleado();

        formulario.mostrar();
    }

    private void guardarEmpleado() {

        try {

            String nombre = txtNombre.getText().trim();
            String departamento = txtDepartamento.getText().trim();
            String salarioTexto = txtSalario.getText().trim();
            String fechaTexto = txtFechaContratacion.getText().trim();

            BigDecimal salario = new BigDecimal(salarioTexto);

            LocalDate fecha = LocalDate.parse(fechaTexto);

            if (empleadoEditar == null) {

                // REGISTRAR
                Empleado empleado = new Empleado(
                        nombre,
                        departamento,
                        salario,
                        fecha,
                        chkActivo.isSelected()
                );

                EmpleadoDAO dao = new EmpleadoDAO();

                dao.crear(empleado);

                JOptionPane.showMessageDialog(
                        ventana,
                        "Empleado registrado correctamente."
                );

            } else {

                // EDITAR
                empleadoEditar.setNombre(nombre);
                empleadoEditar.setDepartamento(departamento);
                empleadoEditar.setSalario(salario);
                empleadoEditar.setFechaContratacion(fecha);
                empleadoEditar.setActivo(chkActivo.isSelected());

                EmpleadoDAO dao = new EmpleadoDAO();

                boolean actualizado = dao.actualizar(empleadoEditar);

                if (actualizado) {

                    JOptionPane.showMessageDialog(
                            ventana,
                            "Empleado actualizado correctamente."
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            ventana,
                            "No se encontró el empleado."
                    );

                    return;
                }
            }

            // Avisar a VentanaPrincipal para recargar la tabla
            if (alGuardar != null) {
                alGuardar.run();
            }

            ventana.dispose();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    ventana,
                    "Error al guardar empleado:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}