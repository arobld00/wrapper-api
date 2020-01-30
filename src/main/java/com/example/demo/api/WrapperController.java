package com.example.demo.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Mapper;
import com.example.demo.api.viewmodel.TareaViewModel;
import com.example.demo.modelo.Tarea;
import com.example.demo.terminal.Comando;
import com.example.demo.terminal.Tecla;
import com.example.demo.terminal.Terminal;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin
public class WrapperController implements Wrapper {
	
	@Autowired
	private Terminal terminal;

	@Autowired
	private Mapper mapper;

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Tarea nuevaTarea(@RequestBody TareaViewModel tareaViewModel,
	        BindingResult bindingResult) throws TimeoutException {
	    if (bindingResult.hasErrors()) {
	        throw new ValidationException();
	    }
	    Tarea tarea = this.mapper.convertirATarea(tareaViewModel);
	    terminal.enviarCadena("A");
	    terminal.enviar(Tecla.ENTER.toString());
	    terminal.enviarCadena(tarea.getNumero());
	    terminal.enviar(Tecla.ENTER.toString());
	    terminal.enviarCadena(tarea.getNombre());
	    terminal.enviar(Tecla.ENTER.toString());
	    terminal.enviarCadena(tarea.getDescripcion());
	    terminal.enviar(Tecla.ENTER.toString());
	    
	    SimpleDateFormat f = new SimpleDateFormat("dd MM yy");
	    terminal.enviarCadena(f.format(tarea.getFecha()));
	    terminal.enviar(Tecla.ENTER.toString());
	    
	    terminal.enviar(Tecla.ENTER.toString());
	    
		return tarea;
	}

	@Override
	@DeleteMapping("/{numero}")
	public void eliminarTarea(@PathVariable String numero) throws TimeoutException {
		terminal.enviarCadena("R");
		terminal.enviar(Tecla.ENTER.toString());
		
		terminal.enviarCadena(numero);
		terminal.enviar(Tecla.ENTER.toString());
		
		terminal.enviarCadena(Comando.YES.toString());
		terminal.enviar(Tecla.ENTER.toString());
		
		terminal.enviar(Tecla.ENTER.toString());
	}
	
	@Override
	@PostMapping(value = "/archivo")
	public void nuevoArchivo() throws TimeoutException {
		terminal.enviarCadena("N");
		terminal.enviar(Tecla.ENTER.toString());
		
		terminal.enviarCadena("Y");
		terminal.enviar(Tecla.ENTER.toString());
		
		terminal.enviar(Tecla.ENTER.toString());
	}

	@Override
	public void guardarTareas() {
	}
	
	@Override
	@GetMapping("/all")
	public List<Tarea> listarTareas() {
		return null;
	}
	
	@Override
	@GetMapping("/byDate/{fecha}")
	public List<Tarea> buscarTarea(@PathVariable Date fecha) {
		return null;
	}

}
