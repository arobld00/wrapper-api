package com.example.demo.terminal;

public enum Comando {
	JOB("tasks2.job"),
	ASCII("ascii"),
	USUARIO("PROG"),
	CONTRASEÑA("PROG123"),
	SALTO_LINEA("\n"),
	PRE_LINEA("data: "),
	OK("ok"),
	ERROR("error"),
	YES("Y"),
	NO("N"),
	OFF("Off"),
	FSI("FSI");
	
	private String mensaje;
	
	/**
	 * Comando
	 * @param mensaje
	 */
	private Comando (String mensaje){
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
