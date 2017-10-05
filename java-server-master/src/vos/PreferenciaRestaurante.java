package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PreferenciaRestaurante {
	
	@JsonProperty(value="numeroUsuario")
	private Integer numeroUsuario;
	@JsonProperty(value="nombreRestaurante")
	private String nombreRestaurante;

	public Integer getNumeroUsuario() {
		return numeroUsuario;
	}

	public void setNumeroUsuario(Integer numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	public PreferenciaRestaurante() {
		// TODO Auto-generated constructor stub
	}

}
