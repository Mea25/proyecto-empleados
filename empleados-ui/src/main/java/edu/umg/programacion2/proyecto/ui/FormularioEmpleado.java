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
    private JTextField txtCorreo;
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
        
        JLabel lblCorreo = new JLabel("Correo:");
        txtCorreo = new JTextField();

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
        
        ventana.add(lblCorreo);
        ventana.add(txtCorreo);

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
            txtCorreo.setText(empleadoEditar.getCorreo());
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
            String correo = txtCorreo.getText().trim();
            String salarioTexto = txtSalario.getText().trim();
            String fechaTexto = txtFechaContratacion.getText().trim();

            // Validar nombre
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(
                        ventana,
                        "El nombre es obligatorio.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                txtNombre.requestFocus();
                return;
            }

            // Validar departamento
            if (departamento.isEmpty()) {
                JOptionPane.showMessageDialog(
                        ventana,
                        "El departamento es obligatorio.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                txtDepartamento.requestFocus();
                return;
            }

            // Validar salario vacío
            if (salarioTexto.isEmpty()) {
                JOptionPane.showMessageDialog(
                        ventana,
                        "El salario es obligatorio.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                txtSalario.requestFocus();
                return;
            }

            // Convertir y validar salario
            BigDecimal salario;

            try {
                salario = new BigDecimal(salarioTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        ventana,
                        "El salario debe ser un número válido.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                txtSalario.requestFocus();
                return;
            }

            if (salario.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(
                        ventana,
                        "El salario debe ser mayor que 0.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                txtSalario.requestFocus();
                return;
            }

            // Validar fecha vacía
            if (fechaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(
                        ventana,
                        "La fecha de contratación es obligatoria.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                txtFechaContratacion.requestFocus();
                return;
            }

            // Convertir fecha
            LocalDate fecha;

            try {
                fecha = LocalDate.parse(fechaTexto);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        ventana,
                        "La fecha debe tener el formato AAAA-MM-DD.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                txtFechaContratacion.requestFocus();
                return;
            }

            // La fecha no puede ser futura
            if (fecha.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(
                        ventana,
                        "La fecha de contratación no puede ser futura.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE
                );
                txtFechaContratacion.requestFocus();
                return;
            }

            // =========================
            // REGISTRAR
            // =========================

            if (empleadoEditar == null) {

                Empleado empleado = new Empleado(
                        nombre,
                        departamento,
                        correo,
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

                // =========================
                // EDITAR
                // =========================

                empleadoEditar.setNombre(nombre);
                empleadoEditar.setDepartamento(departamento);
                empleadoEditar.setCorreo(correo);
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

            // Recargar la tabla
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