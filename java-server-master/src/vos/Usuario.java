package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario 
{
	@JsonProperty(value="NOMBRE")
	private String nombre;
	@JsonProperty(value="NUMERO_ID")
	private String id;
	@JsonProperty(value="ROL")
	private String rol;
	@JsonProperty(value="EMAIL")
	private String email;
	@JsonProperty(value="RESTAURANTES")
	private List<Restaurante> preferenciaRestaurante ;
	
	public Usuario(@JsonProperty(value="NUMERO_ID")String numero, 
					@JsonProperty(value="NOMBRE")String nombre,
					@JsonProperty(value="ROL") String rol, 
					@JsonProperty(value="EMAIL")String email ) 
	{
		this.id = numero;
		this.nombre = nombre;
		this.rol = rol ;
		this.email = email;
	}
	
	
//	public Usuario(@JsonProperty(value="NUMERO_ID")Integer numero, 
//			@JsonProperty(value="NOMBRE")String nombre,
//			@JsonProperty(value="ROL") String rol, 
//			@JsonProperty(value="EMAIL")String email,
//			@JsonProperty(value="RESTAURANTES")List<Restaurante> preferenciaRestaurante ) 
//		{
//		this.id = numero;
//		this.nombre = nombre;
//		this.rol = rol ;
//		this.preferenciaRestaurante=preferenciaRestaurante;
//		}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
