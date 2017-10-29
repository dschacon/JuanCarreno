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
	private String restaurante;
	@JsonProperty(value="disponible")
	private Integer disponibles;
	@JsonProperty(value="maximo")
	private Integer maximo ;
	@JsonProperty(value="tiempoPreparacion")
	private Integer tiempoPreparacion;


	public Menu(@JsonProperty(value="nombre")String nombre, @JsonProperty(value="restaurante")String restaurante,
			@JsonProperty(value="costoProduccion")int costoProduccion, @JsonProperty(value="precioVenta")int precioVenta
			,@JsonProperty(value="disponible")int disponible , @JsonProperty(value="maximo")int maximo,@JsonProperty(value="tiempoPreparacion") Integer tiempo)
	{
		this.nombre = nombre;
		this.restaurante = restaurante;
		this.costoProduccion = costoProduccion;
		this.precioVenta = precioVenta;
		this.disponibles=disponible;
		this.maximo=maximo;
		this.tiempoPreparacion=tiempo;
	}

	
	
	
	public Integer getDisponibles() {
		return disponibles;
	}




	public void setDisponibles(Integer disponibles) {
		this.disponibles = disponibles;
	}




	public Integer getMaximo() {
		return maximo;
	}




	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}




	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}




	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
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
