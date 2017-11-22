package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Consulta11{
	
	@JsonProperty(value="restauranteMas")
	private String restauranteMas;
	@JsonProperty(value="restauranteMenos")
	private String restauranteMenos;
	@JsonProperty(value="productoMas")
	private String productoMas;
	@JsonProperty(value="productoMenos")
	private String productoMenos;
    @JsonProperty(value="dia")
	private String dia;
	
	public Consulta11()
	{
		this.restauranteMas = null;
		this.restauranteMenos = null;
		this.productoMas = null;
		this.productoMenos = null;
		this.dia = null;
	}

	public String getRestauranteMas() {
		return restauranteMas;
	}

	public void setRestauranteMas(String restauranteMas) {
		this.restauranteMas = restauranteMas;
	}

	public String getRestauranteMenos() {
		return restauranteMenos;
	}

	public void setRestauranteMenos(String restauranteMenos) {
		this.restauranteMenos = restauranteMenos;
	}

	public String getProductoMas() {
		return productoMas;
	}

	public void setProductoMas(String productoMas) {
		this.productoMas = productoMas;
	}

	public String getProductoMenos() {
		return productoMenos;
	}

	public void setProductoMenos(String productoMenos) {
		this.productoMenos = productoMenos;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
	
}
