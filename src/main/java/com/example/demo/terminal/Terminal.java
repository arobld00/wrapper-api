package com.example.demo.terminal;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Terminal {
	
	boolean iniciar(String direccion, int puerto);
	
	String enviar(String texto) throws TimeoutException;
	
	String enviarCadena(String texto) throws TimeoutException;
	
	String devolverDisplay() throws TimeoutException;
	
	String[] devolverLineasDisplay() throws TimeoutException;
	
	String devolverLineaDisplay(int longitud, int fila, int columna);
	
	boolean comprobarLineaDisplay(String texto, int fila, int columna);
	
	void parar() throws IOException;
	
}
