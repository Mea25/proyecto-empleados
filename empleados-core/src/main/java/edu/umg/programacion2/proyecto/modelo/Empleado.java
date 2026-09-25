package edu.umg.programacion2.proyecto.modelo;
import java.math.BigDecimal;
import java.time.LocalDate;


public class Empleado {
    private int id;
    private String nombre;
    private String departamento;
    private String correo;
    private BigDecimal salario;
    private LocalDate fechaContratacion;
    private boolean activo;

    public Empleado(String nombre, String departamento, String correo,
            BigDecimal salario, LocalDate fechaContratacion, boolean activo) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.correo = correo;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCorreo() {
	    return correo;
	}

	public void setCorreo(String correo) {
	    this.correo = correo;
	}
	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public LocalDate getFechaContratacion() {
		return fechaContratacion;
	}

	public void setFechaContratacion(LocalDate fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Empleado(int id, String nombre, String departamento, String correo,
	        BigDecimal salario, LocalDate fechaContratacion, boolean activo) {
	    this.id = id;
	    this.nombre = nombre;
	    this.departamento = departamento;
	    this.correo = correo;
	    this.salario = salario;
	    this.fechaContratacion = fechaContratacion;
	    this.activo = activo;
	}
}
