package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTm;
import vos.Pedido;
import vos.Producto;
import vos.Restaurante;
import vos.Usuario;

@Path("productos")
public class ProductoServices {

	@Context
	private ServletContext context;
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	


    /**
     * Metodo que expone servicio REST usando GET que busca el video con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/nombre/nombre?nombre=<<nombre>>" para la busqueda"
     * @param name - Nombre del video a buscar que entra en la URL como parametro 
     * @return Json con el/los videos encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getProductoname( @PathParam("nombre") String name) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		Producto producto;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre del video no valido");
			 producto = tm.buscarProductoPorName(name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}


    /**
     * Metodo que expone servicio REST usando POST que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Path( "{nombre}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProducto(@PathParam( "nombre" ) String nombreRestaurante , Producto producto) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			Restaurante restaurante = tm.buscarRestaurantesPorName(nombreRestaurante);
			if(restaurante==null){
				String error = "No existe un restaurante con el nombre: "+nombreRestaurante;
				return Response.status(500).entity("{ \"ERROR\": \""+ error + "\"}").build();
			}else{
			tm.addProducto(producto,restaurante);
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}
	
	 /**
     * Metodo que expone servicio REST usando PUT que actualiza el pedido que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/pedidos
     * @param pedido - pedido a actualizar. 
     * @return Json con el pedido que actualizo o Json con el error que se produjo
     */
	@PUT
	@Path( "{nombre}" )
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProducto(@PathParam( "nombre" ) String nombre ) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		Producto producto;
		try {
			producto = tm.buscarProductoPorName(nombre);
			if(producto==null){
				String error = "No existe un producto con el : "+nombre ;
				return Response.status(500).entity("{ \"ERROR\": \""+ error + "\"}").build();
			}
		tm.updateProducto(producto);
			
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}
}
