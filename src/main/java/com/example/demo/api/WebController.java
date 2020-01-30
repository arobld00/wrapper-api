package com.example.demo.api;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.terminal.Comando;
import com.example.demo.terminal.Tecla;
import com.example.demo.terminal.Terminal;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class WebController {
	
	@Autowired
	private Terminal terminal;	

	private final static boolean TERMINAL = true;
	
	private final static String SERVIDOR = "155.210.68.153";
	private final static Integer PUERTO = 423;
	
	@PostConstruct
	public void init() throws ConnectException, TimeoutException  {
		if (!terminal.iniciar(SERVIDOR, PUERTO)) {
    		throw new ConnectException("No se puede conectar a " + SERVIDOR + ":" + PUERTO);
    	}
		terminal.enviar(Tecla.ENTER.toString());
		if (!terminal.comprobarLineaDisplay("Press the ENTER key", 23, 0)) {
			terminal.enviar(Tecla.ENTER.toString());
		}
		if (!terminal.comprobarLineaDisplay("Press the ENTER key", 23, 0)
				&& !terminal.comprobarLineaDisplay("MUSIC Userid:", 2, 3)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No aparece la pantalla de inicio.");
		}
	}
	
	@GetMapping
    public boolean conectado() {
    	return (terminal.comprobarLineaDisplay("**MENU**", 4, 2) 
    			&& terminal != null)? true : false;
    }
	
    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
	public void acceder(@RequestBody ObjectNode json) throws ConnectException, TimeoutException {
    	if (!terminal.comprobarLineaDisplay("MUSIC Userid:", 2, 3)) {
    		terminal.enviar(Tecla.ENTER.toString());
    	}
    	
    	String usuario = json.get("user").asText();
    	usuario = usuario.replaceAll("usuario", "");
    	String password = json.get("password").asText();
    	password = password.replaceAll("password", "");
    	
    	if (TERMINAL) { System.out.println("> " + usuario + " " + password); }
    	
    	terminal.enviarCadena(usuario);
    	terminal.enviar(Tecla.TAB.toString());
    	
    	terminal.enviarCadena(password);
    	terminal.enviar(Tecla.ENTER.toString());
    	
    	if (terminal.comprobarLineaDisplay("Userid is in use", 1, 0)) {
    		terminal.enviarCadena(Comando.OK.toString());
    		terminal.enviar(Tecla.ENTER.toString());
    	}
    	
    	if (terminal.comprobarLineaDisplay("Userid is not authorized", 6, 1)
    			|| terminal.comprobarLineaDisplay("Password incorrect!", 6, 1)) {
    		terminal.enviar(Tecla.F3.toString());
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No aparece la pantalla de inicio.");
    	}
    	terminal.enviar(Tecla.ENTER.toString());
    	terminal.enviarCadena(Comando.JOB.toString());
    	terminal.enviar(Tecla.ENTER.toString());  
	}
	
	@PostMapping(path = "/logout")
	@ResponseStatus(HttpStatus.OK)  
	public void salir() {
    	try {
			this.desconexion();
			terminal.parar();
		} catch (TimeoutException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	private void desconexion() throws TimeoutException {
		terminal.enviarCadena("E");
		terminal.enviar(Tecla.ENTER.toString());
		terminal.enviarCadena(Comando.FSI.toString());
		terminal.enviar(Tecla.ENTER.toString());
		terminal.enviarCadena(Comando.OFF.toString());
		terminal.enviar(Tecla.ENTER.toString());
	}

}
