package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario 
{
	@JsonProperty(value="nombre")
	private String nombre;
	@JsonProperty(value="id")
	private Integer id;
	@JsonProperty(value="rol")
	private String rol;
	@JsonProperty(value="email")
	private String email;
	@JsonProperty(value="restaurante")
	private List<Restaurante> preferenciaRestaurante ;
	
	public Usuario(@JsonProperty(value="id")Integer numero, 
					@JsonProperty(value="nombre")String nombre,
					@JsonProperty(value="rol") String rol, 
					@JsonProperty(value="email")String email ) 
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
