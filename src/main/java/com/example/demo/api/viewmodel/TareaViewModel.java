package com.example.demo.api.viewmodel;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class TareaViewModel {
	
	private String numero;
	
	@NotNull
	@Min(3)
	@Max(16)
	private String nombre;
	
	@NotNull
    @Max(32)
	private String descripcion;
    
    @NotNull
    private Date fecha;
    
    public String getNumero() {
		return numero;
	}
    
	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
