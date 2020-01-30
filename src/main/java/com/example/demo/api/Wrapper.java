package com.example.demo.api;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.validation.BindingResult;

import com.example.demo.api.viewmodel.TareaViewModel;
import com.example.demo.modelo.Tarea;

public interface Wrapper {
	
	Tarea nuevaTarea(TareaViewModel tareaViewModel, BindingResult bindingResult)  throws TimeoutException;
	
	void nuevoArchivo() throws TimeoutException;
	
	void eliminarTarea(String numero) throws TimeoutException;
	
	List<Tarea> listarTareas();
	
	List<Tarea> buscarTarea(Date fecha);
	
	void guardarTareas();
	
}
