package vos;

import java.sql.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido 
{
	@JsonProperty(value="id")
	private int id;
	@JsonProperty(value="costo")
	private float costoTotal;
	@JsonProperty(value="fecha")
	private Date fecha;
	@JsonProperty(value="idUsuario")
	private int idUsuario;
	@JsonProperty(value="producto")
	private String nombreProducto ;

	private Producto producto;
	
	private boolean entregado;

	
	
	public Pedido(@JsonProperty(value="PEDIDO_ID")int id, @JsonProperty(value="COSTO_TOTAL")float costoTotal,
				  @JsonProperty(value="FECHA")Date fecha, @JsonProperty(value="ID_USUARIO")int idUsuario,
				  @JsonProperty(value="producto") String producto)
	{
		this.id = id;
		this.costoTotal = costoTotal;
		this.fecha = fecha;
		this.idUsuario = idUsuario;
		this.nombreProducto=producto;
	}
	
	
	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}


	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(float costoTotal) {
		this.costoTotal = costoTotal;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
}
