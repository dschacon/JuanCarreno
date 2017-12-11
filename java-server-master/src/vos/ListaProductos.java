package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaProductos {

	@JsonProperty(value="productos")
	private List<ProductoIter5> productos;
	

	public ListaProductos( @JsonProperty(value="productos")List<ProductoIter5> videos){
		this.productos = videos;
	}


	public List<ProductoIter5> getProductos() {
		return productos;
	}


	public void setProductos(List<ProductoIter5> productos) {
		this.productos = productos;
	}
}