package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurante 
{
	@JsonProperty(value="nombre")
	private String nombre;
	@JsonProperty(value="tipoComida")
	private String tipoComida;
	@JsonProperty(value="paginaWeb")
	private String paginaWeb;
	@JsonProperty(value="representante")
	private String representante;
	@JsonProperty(value="nombreZona")
	private String nombreZona;
	
	public Restaurante(@JsonProperty(value="NOMBRE")String nombre, @JsonProperty(value="TIPO_COMIDA")String tipoComida,
					   @JsonProperty(value="PAGINAWEB")String paginaWeb, @JsonProperty(value="REPRESENTANTE")String representante, @JsonProperty(value="NOMBREZONA")String nombreZona)
	{
		this.nombre = nombre;
		this.tipoComida = tipoComida;
		this.paginaWeb = paginaWeb;
		this.representante = representante;
		this.nombreZona = nombreZona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoComida() {
		return tipoComida;
	}

	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

}
