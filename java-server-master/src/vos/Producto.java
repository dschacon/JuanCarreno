package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Producto 
{
	@JsonProperty(value="nombre")
	private String nombre;
	@JsonProperty(value="disponible")
	private Integer disponible;
	@JsonProperty(value="descripcion")
	private String descripcion;
	@JsonProperty(value="traduccion")
	private String traduccion;
	@JsonProperty(value="tiempoPreparacion")
	private Float tiempoPreparacion;
	@JsonProperty(value="costoProduccion")
	private Float costoProduccion;
	@JsonProperty(value="precioVenta")
	private Float precioVenta;
	@JsonProperty(value="categoria")
	private String categoria;
	@JsonProperty(value="tipo")
	private List<String> tipo;

	public Producto(@JsonProperty(value="NOMBRE")String nombre, 
					@JsonProperty(value="DESCRIPCION")String descripcion,
					@JsonProperty(value="TRADUCCION")String traduccion, 
					@JsonProperty(value="CATEGORIA")String categoria)
	{
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.traduccion = traduccion;
		this.categoria = categoria;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTraduccion() {
		return traduccion;
	}

	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
