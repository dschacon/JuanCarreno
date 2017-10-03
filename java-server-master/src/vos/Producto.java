package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Producto 
{
	@JsonProperty(value="nombre")
	private String nombre;
//	@JsonProperty(value="disponible")
//	private boolean disponible;
	@JsonProperty(value="descripcion")
	private String descripcion;
	@JsonProperty(value="traduccion")
	private String traduccion;
//	@JsonProperty(value="tiempoPreparacion")
//	private int tiempoPreparacion;
//	@JsonProperty(value="costoProduccion")
//	private int costoProduccion;
//	@JsonProperty(value="precioVenta")
//	private int precioVenta;
	@JsonProperty(value="categoria")
	private int categoria;
//	@JsonProperty(value="tipo")
//	private List<String> tipo;

	public Producto(@JsonProperty(value="NOMBRE")String nombre, @JsonProperty(value="DESCRIPCION")String descripcion,
					@JsonProperty(value="TRADUCCION")String traduccion, @JsonProperty(value="CATEGORIA")int categoria)
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

//	public boolean isDisponible() {
//		return disponible;
//	}
//
//	public void setDisponible(boolean disponible) {
//		this.disponible = disponible;
//	}

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

//	public int getTiempoPreparacion() {
//		return tiempoPreparacion;
//	}
//
//	public void setTiempoPreparacion(int tiempoPreparacion) {
//		this.tiempoPreparacion = tiempoPreparacion;
//	}
//
//	public int getCostoProduccion() {
//		return costoProduccion;
//	}
//
//	public void setCostoProduccion(int costoProduccion) {
//		this.costoProduccion = costoProduccion;
//	}
//
//	public int getPrecioVenta() {
//		return precioVenta;
//	}
//
//	public void setPrecioVenta(int precioVenta) {
//		this.precioVenta = precioVenta;
//	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

//	public List<String> getTipo() {
//		return tipo;
//	}
//
//	public void setTipo(List<String> tipo) {
//		this.tipo = tipo;
//	}


}
