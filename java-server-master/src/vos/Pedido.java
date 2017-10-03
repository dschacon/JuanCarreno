package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido 
{
	@JsonProperty(value="id")
	private int id;
	@JsonProperty(value="costoTotal")
	private float costoTotal;
	@JsonProperty(value="fecha")
	private Date fecha;
	@JsonProperty(value="idUsuario")
	private int idUsuario;
	
	public Pedido(@JsonProperty(value="PEDIDO_ID")int id, @JsonProperty(value="COSTO_TOTAL")float costoTotal,
				  @JsonProperty(value="FECHA")Date fecha, @JsonProperty(value="ID_USUARIO")int idUsuario)
	{
		this.id = id;
		this.costoTotal = costoTotal;
		this.fecha = fecha;
		this.idUsuario = idUsuario;
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
