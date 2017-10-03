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
public class DAOTablaZonas {


	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOZona
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaZonas() {
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
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos las zonas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ZONA;
	 * @return Arraylist con las zonas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Zona> darZonas() throws SQLException, Exception {
		ArrayList<Zona> zonas = new ArrayList<Zona>();

		String sql = "SELECT * FROM ZONA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");	
			zonas.add(new Zona(name));
		}
		return zonas;
	}


	/**
	 * Metodo que busca la/las zonas con el nombre que entra como parametro.
	 * @param name - Nombre de la/las zonas a buscar
	 * @return ArrayList con las zonas encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Zona> buscarZonasPorName(String name) throws SQLException, Exception {
		ArrayList<Zona> zonas = new ArrayList<Zona>();

		String sql = "SELECT * FROM ZONA WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");		
			zonas.add(new Zona(nombre));
		}

		return zonas;
	}

	/**
	 * Metodo que agrega la zona que entra como parametro a la base de datos.
	 * @param zona - la zona a agregar. zona !=  null
	 * <b> post: </b> se ha agregado la zona a la base de datos en la transaction actual. pendiente que la zona master
	 * haga commit para que la zona baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la zona a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addZona(Zona zona) throws SQLException, Exception {

		String sql = "INSERT INTO ZONA VALUES ('";
		sql += zona.getNombre() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que elimina la zona que entra como parametro en la base de datos.
	 * @param zona - la zona a borrar. zona !=  null
	 * <b> post: </b> se ha borrado la zona en la base de datos en la transaction actual. pendiente que la zona master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la zona.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteZona(Zona zona) throws SQLException, Exception {

		String sql = "DELETE FROM ZONA";
		sql += " WHERE NOMBRE = '" + zona.getNombre() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
