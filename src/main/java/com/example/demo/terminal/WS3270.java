package com.example.demo.terminal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

@Component
public class WS3270 implements Terminal {
	
	private static final String EJECUTABLE = "C:/Program Files/wc3270/ws3270.exe";
	
	private Process proceso;
	
	private BufferedReader lectura;
	
	private BufferedWriter teclado;
	
	private String buffer = null;
	
	private final static boolean TERMINAL = false;
	
	private final static int RETARDO = 100;

	@Override
	public boolean iniciar(String direccion, int puerto) {
		try {
			ProcessBuilder builder = new ProcessBuilder(
					EJECUTABLE,
					direccion + ":" + puerto);
			proceso = builder.start();
			
	        OutputStream entrada = proceso.getOutputStream();
	        InputStream salida = proceso.getInputStream();
	        
			lectura = new BufferedReader(new InputStreamReader(salida));
			teclado = new BufferedWriter(new OutputStreamWriter(entrada));
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String enviar(String texto) throws TimeoutException {
		assert lectura != null;
		assert teclado != null;
        int intentos = 0;
        int maximo = 1000 * 20 / RETARDO;
        buffer = null;
        
        try {
            if (TERMINAL) { System.err.println(">" + texto); }
            teclado.write(texto + Comando.SALTO_LINEA.toString());
            teclado.flush();
            
            String cadena;
            String display = "";

            do {
				while (!lectura.ready()) {
				    if (intentos >= maximo) {
				        throw new TimeoutException("Fallo de espera.");
				    }
				    Thread.sleep(RETARDO);
				    intentos++;
				}
                cadena = lectura.readLine();
                display += cadena + Comando.SALTO_LINEA.toString();
                if (TERMINAL) { System.out.println(">:" + cadena); }
            } while (!cadena.equals(Comando.OK.toString()) && !cadena.equals(Comando.ERROR.toString()));
            return display;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	@Override
	public String enviarCadena(String texto) throws TimeoutException {
		assert texto != null;
		if (TERMINAL) { System.out.println("> " + texto); }
		return this.enviar("string " + texto);
	}

	@Override
	public void parar() throws IOException {
		lectura.close();
		teclado.close();
		proceso.destroy();
	}

	@Override
	public String devolverDisplay() throws TimeoutException {
		String cadena = this.enviar(Comando.ASCII.toString());
		if (cadena != null) {
			for (String fila : cadena.split(Comando.SALTO_LINEA.toString())) {
				if (fila.startsWith(Comando.PRE_LINEA.toString())) {
					buffer += fila.substring(Comando.PRE_LINEA.toString().length()) + Comando.SALTO_LINEA.toString();
				}
			}
			return buffer;
		}
		return (buffer != null)? buffer : null;
	}
	
	@Override
	public String[] devolverLineasDisplay() {
		try {
			return this.devolverDisplay().split(Comando.SALTO_LINEA.toString());
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String devolverLineaDisplay(int longitud, int fila, int columna) {
		return this.devolverLineasDisplay()[fila].substring(columna, columna + longitud);
	}
	
	@Override
	public boolean comprobarLineaDisplay(String texto, int fila, int columna) {
		return this.devolverLineaDisplay(texto.length(), fila, columna).equals(texto);
	}
	
}
