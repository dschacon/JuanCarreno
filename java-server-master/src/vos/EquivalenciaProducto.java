package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class EquivalenciaProducto 
{
	@JsonProperty(value="nombreProducto")
	private String nombreProducto;
	
	@JsonProperty(value="productoEquiv")
	private String productoEquiv;

	public EquivalenciaProducto(@JsonProperty(value="nombreProducto")String nombreI, @JsonProperty(value="productoEquiv")String ingredienteE)
	{
		this.nombreProducto = nombreI;
		this.productoEquiv = ingredienteE;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getProductoEquiv() {
		return productoEquiv;
	}

	public void setProductoEquiv(String productoEquiv) {
		this.productoEquiv = productoEquiv;
	}

}
