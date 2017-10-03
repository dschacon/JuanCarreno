package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Menu 
{
	@JsonProperty(value="nombre")
	private String nombre;
	@JsonProperty(value="costoProduccion")
	private int costoProduccion;
	@JsonProperty(value="precioVenta")
	private int precioVenta;
	@JsonProperty(value="restaurante")
	private String restaurante;

	public Menu(@JsonProperty(value="NOMBRE")String nombre, @JsonProperty(value="RESTAURANTE")String restaurante,
			@JsonProperty(value="COSTO_PRODUCCION")int costoProduccion, @JsonProperty(value="PRECIO_VENTA")int precioVenta)
	{
		this.nombre = nombre;
		this.restaurante = restaurante;
		this.costoProduccion = costoProduccion;
		this.precioVenta = precioVenta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}
}
