package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Producto 
{
	@JsonProperty(value="id")
	private int id;
	@JsonProperty(value="nombre")
	private String nombre;
	@JsonProperty(value="disponible")
	private Integer disponible;
	@JsonProperty(value="descripcion")
	private String descripcion;
	@JsonProperty(value="traduccion")
	private String traduccion;
	@JsonProperty(value="tiempoPreparacion")
	private int tiempoPreparacion;
	@JsonProperty(value="costo")
	private int costoProduccion;
	@JsonProperty(value="precio")
	private int precioVenta;
	@JsonProperty(value="categoria")
	private String categoria;
	@JsonProperty(value="tipo")
	private List<String> tipo;
	@JsonProperty(value="maximo")
	private Integer maximo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getMaximo() {
		return maximo;
	}

	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}

	public Integer getDisponible() {
		return disponible;
	}

	public void setDisponible(Integer disponible) {
		this.disponible = disponible;
	}

	public int getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoPreparacion(int tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public int getCostoProduccion() {
		return costoProduccion;
	}

	public void setCostoProduccion(int costoProduccion) {
		this.costoProduccion = costoProduccion;
	}

	public int getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(int precioVenta) {
		this.precioVenta = precioVenta;
	}

	public List<String> getTipo() {
		return tipo;
	}

	public void setTipo(List<String> tipo) {
		this.tipo = tipo;
	}

	public Producto(@JsonProperty(value="nombre")String nombre,
					@JsonProperty(value="disponible")Integer disponible,
					@JsonProperty(value="descripcion")String descripcion,
					@JsonProperty(value="traduccion")String traduccion,
					@JsonProperty(value="tiempoPreparacion")int tiempoPreparacion,
					@JsonProperty(value="costo")int costoProduccion,
					@JsonProperty(value="precio")int precioVenta,
					@JsonProperty(value="categoria")String categoria,
					@JsonProperty(value="maximo")Integer maximo)
	{
		this.nombre = nombre;
		this.disponible = disponible;
		this.descripcion = descripcion;
		this.traduccion = traduccion;
		this.tiempoPreparacion= tiempoPreparacion;
		this.costoProduccion= costoProduccion;
		this.precioVenta = precioVenta;
		this.categoria = categoria;
		this.maximo = maximo; 
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
