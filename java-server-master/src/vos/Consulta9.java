package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.omg.CORBA.portable.ValueOutputStream;

public class Consulta9 {
		
	@JsonProperty(value="fecha1")
	private String fecha1;
	
	@JsonProperty(value="fecha2")
	private String fecha2;
	@JsonProperty(value="nombreRestaurante")
	private String nombreRestaurante;
	@JsonProperty(value="usuario")
	private Usuario usuario;
	@JsonProperty(value="nombreProducto")
	private String nombreProducto;
	@JsonProperty(value="categoria")
	private String categoria;
	@JsonProperty(value="fechaRespuesta")
	private String fechaRespuesta;

	public Consulta9(@JsonProperty(value="fecha1")String fecha1, @JsonProperty(value="fecha2")String fecha2,
					 @JsonProperty(value="nombreRestaurante")String nombreR)
	{
		this.fecha1 = fecha1;
		this.fecha2 = fecha2;
		this.nombreRestaurante = nombreR;
		usuario = null;
		nombreProducto = null;
		categoria = null;
		fechaRespuesta = null;
	}

	public String getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(String fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public String getFecha1() {
		return fecha1;
	}

	public void setFecha1(String fecha1) {
		this.fecha1 = fecha1;
	}

	public String getFecha2() {
		return fecha2;
	}

	public void setFecha2(String fecha2) {
		this.fecha2 = fecha2;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
