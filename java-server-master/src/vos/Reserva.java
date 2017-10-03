package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Reserva 
{
	@JsonProperty(value="fecha")
	private Date fecha;
	@JsonProperty(value="idEspacio")
	private int idEspacio;
	@JsonProperty(value="nombreZona")
	private String nombreZona;
	@JsonProperty(value="menu")
	private String menu;
	@JsonProperty(value="personas")
	private int personas;
	@JsonProperty(value="horas")
	private int horas;
	@JsonProperty(value="idUsuario")
	private int idUsuario;
	@JsonProperty(value="nombreRestaurante")
	private String nombreRestaurante;
	
	public Reserva(@JsonProperty(value="FECHA")Date fecha, @JsonProperty(value="ID_ESPACIO")int idEspacio, 
				   @JsonProperty(value="NOMBRE_ZONA")String nombreZona, @JsonProperty(value="MENU")String menu,
				   @JsonProperty(value="PERSONAS")int personas, @JsonProperty(value="HORAS")int horas,
				   @JsonProperty(value="ID_USUARIO")int idUsuario, @JsonProperty(value="NOMBRE_RESTAURANTE")String nombreRestaurante)
	{
		this.fecha = fecha;
		this.idEspacio = idEspacio;
		this.nombreZona = nombreZona;
		this.menu = menu;
		this.personas = personas;
		this.horas = horas;
		this.idUsuario = idUsuario;
		this.nombreRestaurante = nombreRestaurante;
	}

	public int getIdEspacio() {
		return idEspacio;
	}

	public void setIdEspacio(int idEspacio) {
		this.idEspacio = idEspacio;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	public int getPersonas() {
		return personas;
	}

	public void setPersonas(int personas) {
		this.personas = personas;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}
}
