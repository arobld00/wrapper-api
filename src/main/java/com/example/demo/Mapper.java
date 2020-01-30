package com.example.demo;

import org.springframework.stereotype.Component;

import com.example.demo.api.viewmodel.TareaViewModel;
import com.example.demo.modelo.Tarea;

@Component
public class Mapper {
	
    public TareaViewModel convertirATareaViewModel(Tarea tarea) {
    	TareaViewModel viewModel = new TareaViewModel();
    	viewModel.setNumero(tarea.getNumero());
    	viewModel.setNombre(tarea.getNombre());
    	viewModel.setNombre(tarea.getNombre());
    	viewModel.setFecha(tarea.getFecha());
        return viewModel;
    }

    public Tarea convertirATarea(TareaViewModel viewModel) {
    	Tarea tarea = new Tarea(
    			viewModel.getNumero(), 
    			viewModel.getNombre(),
    			viewModel.getDescripcion(), 
    			viewModel.getFecha());
        return tarea;
    }

}
