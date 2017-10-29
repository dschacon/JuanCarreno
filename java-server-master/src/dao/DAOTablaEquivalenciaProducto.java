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
public class DAOTablaEquivalenciaProducto{

	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOEquivalenciaProducto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaEquivalenciaProducto() {
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
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos las equivalencias de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM EQUIVALENCIA_PRODUCTO;
	 * @return Arraylist con las equivalencias de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<EquivalenciaProducto> darEquivalencias() throws SQLException, Exception {
		ArrayList<EquivalenciaProducto> equivalencias = new ArrayList<EquivalenciaProducto>();

		String sql = "SELECT * FROM EQUIVALENCIA_PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE_PRODUCTO");
			String equivalente = rs.getString("EQUIVALENTE");	
			equivalencias.add(new EquivalenciaProducto(name, equivalente));
		}
		return equivalencias;
	}


	/**
	 * Metodo que busca la/las equivalencias con el nombre que entra como parametro.
	 * @param name - Nombre de la/los equivalencias a buscar
	 * @return ArrayList con las equivalencias encontradas
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<EquivalenciaProducto> buscarEquivalenciasPorNombre(String name) throws SQLException, Exception {
		ArrayList<EquivalenciaProducto> equivalencias = new ArrayList<EquivalenciaProducto>() ;

		String sql = "SELECT * FROM EQUIVALENCIA_PRODUCTO WHERE NOMBRE_PRODUCTO ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE_PRODUCTO");
			String equivalente = rs.getString("EQUIVALENTE");
			equivalencias.add(new EquivalenciaProducto(nombre, equivalente));
		}

		return equivalencias;
	}

	/**
	 * Metodo que agrega la equivalencia que entra como parametro a la base de datos.
	 * @param equivalencia - la equivalencia a agregar. equivalencia !=  null
	 * <b> post: </b> se ha agregado la equivalencia a la base de datos en la transaction actual. pendiente que la equivalencia master
	 * haga commit para que la equivalencia baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el restaurante a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addEquivalencia(EquivalenciaProducto equivalencia) throws SQLException, Exception {

		String sql = "INSERT INTO EQUIVALENCIA_PRODUCTO VALUES ('";
		sql += equivalencia.getNombreProducto() + "','";
		sql += equivalencia.getProductoEquiv() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza la equivalencia que entra como parametro en la base de datos.
	 * @param equivalencia - la equivalencia a actualizar. equivalencia !=  null
	 * <b> post: </b> se ha actualizado la equivalencia en la base de datos en la transaction actual. pendiente que la equivalencia master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la equivalencia.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateEquivalencia(EquivalenciaProducto equivalencia) throws SQLException, Exception {

		String sql = "UPDATE EQUIVALENCIA_PRODUCTO SET ";
		sql += "NOMBRE_PRODUCTO='" + equivalencia.getNombreProducto() + "',";
		sql += "EQUIVALENTE='" + equivalencia.getProductoEquiv() + "')";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina la equivalencia que entra como parametro en la base de datos.
	 * @param equivalencia - la equivalencia a borrar. equivalencia !=  null
	 * <b> post: </b> se ha borrado la equivalencia en la base de datos en la transaction actual. pendiente que la equivalencia master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar la equivalencia.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteEquivalencia(EquivalenciaProducto equivalencia) throws SQLException, Exception {

		String sql = "DELETE FROM EQUIVALENCIA_PRODUCTO";
		sql += " WHERE NOMBRE_PRODUCTO = '" + equivalencia.getNombreProducto() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
