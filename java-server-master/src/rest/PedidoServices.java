
package rest;


import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import tm.RotondAndesTm;
import vos.Pedido;
import vos.Video;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/RotondAndes/rest/pedidos/...
 * @author Juan Carreño
 */
@Path("pedidos")
public class PedidoServices {

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	

	/**
     * Metodo que expone servicio REST usando GET que busca el pedido con el id que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/pedidos/<<id>>" para la busqueda"
     * @param name - Nombre del pedido a buscar que entra en la URL como parametro 
     * @return Json con el/los pedidos encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getPedido( @PathParam( "id" ) int id )
	{
		RotondAndesTm tm = new RotondAndesTm( getPath( ) );
		try
		{
			Pedido p = tm.buscarPedidoPorId( id );
			return Response.status( 200 ).entity( p ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

    /**
     * Metodo que expone servicio REST usando POST que agrega el pedido que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/pedidos/pedido
     * @param pedido - pedido a agregar
     * @return Json con el pedido que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPedido(Pedido pedido) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			if(tm.buscarUsuarioPorId(pedido.getIdUsuario())==null){
				String error = "No existe un usuario con el id: "+pedido.getIdUsuario() ;
				return Response.status(500).entity("{ \"ERROR\": \""+ error + "\"}").build();
			}else if(tm.buscarProductoPorName(pedido.getNombreProducto())==null){
				String error = "No existe un prosucto con el nombre de : "+pedido.getNombreProducto() ;
				return Response.status(500).entity("{ \"ERROR\": \""+ error + "\"}").build();
			}else{
				tm.addPedido(pedido);
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}

    /**
     * Metodo que expone servicio REST usando PUT que actualiza el pedido que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/pedidos
     * @param pedido - pedido a actualizar. 
     * @return Json con el pedido que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePedido(Pedido pedido) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.updatePedido(pedido);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el pedido que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/pedidos
     * @param pedido - pedido a aliminar. 
     * @return Json con el pedido que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePedido(Pedido pedido) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.deletePedido(pedido);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}


}
