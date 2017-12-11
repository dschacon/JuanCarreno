package rest;



import java.util.ArrayList;
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
import tm.RotondAndesTm;
import vos.*;

@Path("consultas")
public class Consultas {

	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}


	@POST
	@Path( "consulta1/{id: \\d+}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsulta9( @PathParam( "id" ) Integer id, Consulta9 consulta ) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<Consulta9> consultas;
		try {
			consultas = tm.darConsulta9(consulta);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(consultas).build();
	}

	@POST
	@Path( "consulta2/{id: \\d+}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsulta10( @PathParam( "id" ) Integer id, Consulta10 consulta ) {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		List<Consulta10> consultas;
		try {
			consultas = tm.darConsulta10(consulta);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(consultas).build();
	}


	@GET
	@Path( "consulta3/{id: \\d+}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsulta11( @PathParam( "id" ) Integer id ) throws Exception {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		ArrayList<Consulta11> clientes = new ArrayList<>();
		Usuario u = tm.buscarUsuarioPorId(id);
		if(u.getRol().equals("Gerente")) {
			try {
				clientes = (ArrayList<Consulta11>) tm.darConsulta11();
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		else {
			String error = "El usuario con el id: "+id+" no es un gerente";
			return Response.status(500).entity("{ \"ERROR\": \""+ error + "\"}").build();
		}
		return Response.status(200).entity(clientes).build();
	}

	@GET
	@Path( "consulta4/{id: \\d+}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsulta12( @PathParam( "id" ) Integer id ) throws Exception {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		ArrayList<Consulta12> clientes = new ArrayList<>();
		Usuario u = tm.buscarUsuarioPorId(id);
		if(u.getRol().equals("Gerente")) {
			try {
				clientes = (ArrayList<Consulta12>) tm.darConsulta12();
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
		}
		else {
			String error = "El usuario con el id: "+id+" no es un gerente";
			return Response.status(500).entity("{ \"ERROR\": \""+ error + "\"}").build();
		}
		return Response.status(200).entity(clientes).build();
	}
	
	@GET
	@Path( "utilidad/query" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUtilidad( @QueryParam( "nombre" ) String nombre , @QueryParam( "f1" ) String fecha,
			@QueryParam( "f2" ) String fecha2 ) throws Exception {
		RotondAndesTm tm = new RotondAndesTm(getPath());
		ListaUtilidad rta;
			try {
				rta = tm.darUtilidadRemote(nombre, fecha, fecha2);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}

		return Response.status(200).entity(rta).build();
	}

}