CREATE DATABASE proyecto_empleados;
USE proyecto_empleados;
CREATE TABLE empleados (
	    id INT AUTO_INCREMENT PRIMARY KEY,
	    nombre VARCHAR(100) NOT NULL,
	    departamento VARCHAR(100) NOT NULL,
	    salario DECIMAL(10,2) NOT NULL,
	    fecha_contratacion DATE NOT NULL,
	    activo BOOLEAN NOT NULL
	    );
	    
	        
	    