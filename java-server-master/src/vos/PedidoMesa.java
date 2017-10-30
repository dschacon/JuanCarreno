package vos;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoMesa {
	
	@JsonProperty(value="idPedido")
	private int id;
	
	@JsonProperty(value="idMesa")
	private int idMesa;
	
	@JsonProperty(value="idUsuario")
	private int idUsuario;
	
	@JsonProperty(value="productos")
	private ArrayList<String> Productos ;

	private Integer costoTotal;
	
	private Integer numProductos;

	private Date fecha;

	private ArrayList<Producto>voProductos=new ArrayList<>();
	
	private ArrayList<Menu>voMenus=new ArrayList<>();
	
	
	public PedidoMesa(
			@JsonProperty(value="idPedido")int idPedido,
			@JsonProperty(value="idUsuario")int id, 
			@JsonProperty(value="productos")ArrayList<String>productos,
			@JsonProperty(value="idMesa")int mesa)
	{
		this.id=idPedido;
		this.idMesa =mesa;
		this.idUsuario = id;
		this.Productos=productos;

	}

	
		



	public Integer getNumProductos() {
		return numProductos;
	}






	public void setNumProductos() {
		this.numProductos = voProductos.size()+voMenus.size();
	}






	private int calcularCosto(){
		 Iterator<Producto> iter = voProductos.iterator();
		 int costo=0;
		 while(iter.hasNext()){
			 costo+=iter.next().getPrecioVenta();
		 }
		 
		 Iterator<Menu> iter2 = voMenus.iterator();
		 while(iter2.hasNext()){
			 costo+=iter2.next().getPrecioVenta();
		 }
		  return costo;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIdMesa() {
		return idMesa;
	}


	public void setIdMesa(int idMesa) {
		this.idMesa = idMesa;
	}


	public int getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}


	public ArrayList<String> getProductos() {
		return Productos;
	}


	public void setProductos(ArrayList<String> productos) {
		Productos = productos;
	}


	public Integer getCostoTotal() {
		return costoTotal;
	}


	public void setCostoTotal() {
		this.costoTotal = calcularCosto();
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public ArrayList<Producto> getVoProductos() {
		return voProductos;
	}


	public void setVoProductos(ArrayList<Producto> voProductos) {
		this.voProductos = voProductos;
	}


	public ArrayList<Menu> getVoMenus() {
		return voMenus;
	}


	public void setVoMenus(ArrayList<Menu> voMenus) {
		this.voMenus = voMenus;
	}

	
}
