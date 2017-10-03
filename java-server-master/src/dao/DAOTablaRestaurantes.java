package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaci贸n
 * @author Juan Carre駉
 */
public class DAOTablaRestaurantes {


	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORestaurante
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaRestaurantes() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexi贸n que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los restaurantes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM RESTAURANTE;
	 * @return Arraylist con los restaurantes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Restaurante> darRestaurantes() throws SQLException, Exception {
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String tipoComida = rs.getString("TIPO_COMIDA");
			String paginaWeb = rs.getString("PAGINAWEB");
			String representante = rs.getString("REPRESENTANTE");
			String nombreZona = rs.getString("NOMBREZONA");		
			restaurantes.add(new Restaurante(name, tipoComida, paginaWeb, representante, nombreZona));
		}
		return restaurantes;
	}


	/**
	 * Metodo que busca el/los restaurantes con el nombre que entra como parametro.
	 * @param name - Nombre de el/los restaurantes a buscar
	 * @return ArrayList con los restaurantes encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Restaurante> buscarRestaurantesPorNombre(String name) throws SQLException, Exception {
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTE WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String tipoComida = rs.getString("TIPO_COMIDA");
			String paginaWeb = rs.getString("PAGINAWEB");
			String representante = rs.getString("REPRESENTANTE");
			String nombreZona = rs.getString("NOMBREZONA");		
			restaurantes.add(new Restaurante(nombre, tipoComida, paginaWeb, representante, nombreZona));
		}

		return restaurantes;
	}

	/**
	 * Metodo que agrega el restaurante que entra como parametro a la base de datos.
	 * @param restaurante - el restaurante a agregar. restaurante !=  null
	 * <b> post: </b> se ha agregado el restaurante a la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que el restaurante baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el restaurante a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "INSERT INTO RESTAURANTE VALUES ('";
		sql += restaurante.getNombre() + "','";
		sql += restaurante.getTipoComida() + "','";
		sql += restaurante.getPaginaWeb() + "','";
		sql += restaurante.getRepresentante() + "','";
		sql += restaurante.getNombreZona() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el restaurante que entra como parametro en la base de datos.
	 * @param restaurante - el restaurante a actualizar. restaurante !=  null
	 * <b> post: </b> se ha actualizado el restaurante en la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "UPDATE RESTAURANTE SET ";
		sql += "TIPO_COMIDA='" + restaurante.getTipoComida() + "',";
		sql += "PAGINAWEB='" + restaurante.getPaginaWeb() + "',";
		sql += "REPRESENTANTE='" + restaurante.getRepresentante() + "',";
		sql += "NOMBREZONA='" + restaurante.getNombreZona() + "',";
		sql += " WHERE NOMBRE = " + restaurante.getNombre();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el restaurante que entra como parametro en la base de datos.
	 * @param restaurante - el restaurante a borrar. restaurante !=  null
	 * <b> post: </b> se ha borrado el restaurante en la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "DELETE FROM RESTAURANTE";
		sql += " WHERE NOMBRE = '" + restaurante.getNombre() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
