	
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
import vos.Menu;
import vos.Restaurante;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/RotondAndes/rest/menus/...
 * @author Juan Carreño
 */
@Path("menus")
public class MenuServices {

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
	 * Metodo que expone servicio REST usando GET que da todos los menus de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus
	 * @return Json con todos los menus de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMenus() {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<Menu> menus;
		try {
			menus = tm.darMenus();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menus).build();
	}

    /**
     * Metodo que expone servicio REST usando GET que busca el menu con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus/nombre/nombre?nombre=<<nombre>>" para la busqueda"
     * @param name - Nombre del menu a buscar que entra en la URL como parametro 
     * @return Json con el/los menus encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getMenuName( @PathParam("nombre") String name) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		Menu menus;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre del menu no valido");
			menus = tm.buscarMenusPorName(name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menus).build();
	}


    /**
     * Metodo que expone servicio REST usando POST que agrega el menu que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus/menu
     * @param menu - menu a agregar
     * @return Json con el menu que agrego o Json con el error que se produjo
     */
	@POST
	@Path( "{nombre}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenu(@PathParam( "nombre" ) String nombreRestaurante, Menu menu) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			Restaurante restaurante = tm.buscarRestaurantesPorName(nombreRestaurante);
			if(restaurante==null){
				String error = "No existe un restaurante con el nombre: "+nombreRestaurante;
				return Response.status(500).entity("{ \"ERROR\": \""+ error + "\"}").build();
			}else{
			menu.setRestaurante(restaurante.getNombre());	
			tm.addMenu(menu);
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}

    /**
     * Metodo que expone servicio REST usando PUT que actualiza el menu que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus
     * @param menu - menu a actualizar. 
     * @return Json con el menu que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMenu(Menu menu) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.updateMenu(menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el restaurante que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes
     * @param menu - restaurante a aliminar. 
     * @return Json con el restaurante que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMenu(Menu menu) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.deleteMenu(menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}


}
