
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
import vos.EquivalenciaIngrediente;
import vos.Restaurante;
import vos.Usuario;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/RotondAndes/rest/equivalenciasIngrediente/...
 * @author Juan Carreño
 */
@Path("equivalenciasIngrediente")
public class EquivalenciaIngredienteServices {

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
	 * Metodo que expone servicio REST usando GET que da todos las equivalencias de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/equivalenciasIngrediente
	 * @return Json con todos las equivalencias de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getEquivalencias() {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<EquivalenciaIngrediente> equivalencias;
		try {
			equivalencias = tm.darEquivalenciasIngrediente();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(equivalencias).build();
	}

    /**
     * Metodo que expone servicio REST usando GET que busca la equivalencia con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/equivalenciasIngrediente/nombre/nombre?nombre=<<nombre>>" para la busqueda"
     * @param name - Nombre de la equivalencia a buscar que entra en la URL como parametro 
     * @return Json con la/las equivalencias encontradas con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getEquivalenciaName( @PathParam("nombre") String name) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<EquivalenciaIngrediente> equivalencias;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre de la equivalencia no valido");
			equivalencias = tm.buscarEquivalenciasIngredientePorName(name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(equivalencias).build();
	}


    /**
     * Metodo que expone servicio REST usando POST que agrega la equivalencia que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/equivalenciasIngrediente/equivalencia
     * @param equivalencia - equivalencia a agregar
     * @return Json con la equivalencia que agrego o Json con el error que se produjo
     */
	@POST
	@Path( "{nombre}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEquivalencia(@PathParam( "nombre" ) String nombreRestaurante,EquivalenciaIngrediente equivalencia) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			Restaurante rest = tm.buscarRestaurantesPorName(nombreRestaurante);
			if(rest==null){
				String error = "No existe un restaurante con el nombre: "+nombreRestaurante;
				return Response.status(500).entity("{ \"ERROR\": \""+ error + "\"}").build();
			}
			else{
			tm.addEquivalenciaIngrediente(equivalencia);
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(equivalencia).build();
	}

    /**
     * Metodo que expone servicio REST usando PUT que actualiza la equivalencia que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/equivalenciasIngrediente
     * @param equivalencia - equivalencia a actualizar. 
     * @return Json con la equivalencia que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEquivalencia(EquivalenciaIngrediente equivalencia) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.updateEquivalenciaIngrediente(equivalencia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(equivalencia).build();
	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina la equivalencia que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/equivalenciasIngrediente
     * @param equivalencia - equivalencia a aliminar. 
     * @return Json con la equivalencia que elimino o Json con el error que se produjo
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEquivalencia(EquivalenciaIngrediente equivalencia) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		try {
			tm.deleteEquivalenciaIngrediente(equivalencia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(equivalencia).build();
	}


}
