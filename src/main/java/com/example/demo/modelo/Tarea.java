package com.example.demo.modelo;

import java.util.Date;

public class Tarea {
	
	private String numero;

	private String nombre;

	private String descripcion;

	private Date fecha;
	
	public Tarea(String numero, String nombre, String descripcion, Date fecha) {
		this.numero = numero;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}
	
	public Tarea() {
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
    @Override
    public String toString() {
        return "Tarea{" + "numbero=" + numero + ", nombre=" + nombre + 
                ", descripcion=" + descripcion + ", fecha=" + fecha.toString() + '}';
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarea other = (Tarea) obj;
		
	    return this.numero == other.numero;
	}

}
