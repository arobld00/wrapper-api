package com.example.demo.terminal;

public enum Tecla {
	F3("PF(3)"),
	ENTER("ENTER"),
	TAB("TAB"),
	ESC("ESC");
	
	private String mensaje;
	
	/**
	 * Tecla
	 * @param mensaje
	 */
	private Tecla (String mensaje){
		this.mensaje = mensaje;
	}
	
	/**
	 * toString
	 */
	@Override
	public String toString(){
		return mensaje;
	}

}
