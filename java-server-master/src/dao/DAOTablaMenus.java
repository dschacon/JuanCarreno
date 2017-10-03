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
public class DAOTablaMenus {


	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOMenu
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaMenus() {
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
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los menus de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM MENU;
	 * @return Arraylist con los menus de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Menu> darMenus() throws SQLException, Exception {
		ArrayList<Menu> menus = new ArrayList<Menu>();

		String sql = "SELECT * FROM MENU";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String restaurante = rs.getString("RESTAURANTE");
			int costoProduccion = rs.getInt("COSTO_PRODUCCION");
			int precioVenta = rs.getInt("PRECIO_VENTA");
			menus.add(new Menu(name, restaurante, costoProduccion, precioVenta));
		}
		return menus;
	}


	/**
	 * Metodo que busca el/los menus con el nombre que entra como parametro.
	 * @param name - Nombre de el/los menus a buscar
	 * @return ArrayList con los menus encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Menu> buscarMenusPorName(String name) throws SQLException, Exception {
		ArrayList<Menu> menus = new ArrayList<Menu>();

		String sql = "SELECT * FROM MENU WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String restaurante = rs.getString("RESTAURANTE");
			int costoProduccion = rs.getInt("COSTO_PRODUCCION");
			int precioVenta = rs.getInt("PRECIO_VENTA");
			menus.add(new Menu(nombre, restaurante, costoProduccion, precioVenta));
		}

		return menus;
	}

	/**
	 * Metodo que agrega el menu que entra como parametro a la base de datos.
	 * @param menu - el menu a agregar. menu !=  null
	 * <b> post: </b> se ha agregado el menu a la base de datos en la transaction actual. pendiente que el menu master
	 * haga commit para que el menu baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el menu a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addMenu(Menu menu) throws SQLException, Exception {

		String sql = "INSERT INTO MENU VALUES ('";
		sql += menu.getRestaurante() + "','";
		sql += menu.getNombre() + "',";
		sql += menu.getCostoProduccion() + ",";
		sql += menu.getPrecioVenta() +")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el menu que entra como parametro en la base de datos.
	 * @param menu - el menu a actualizar. menu !=  null
	 * <b> post: </b> se ha actualizado el menu en la base de datos en la transaction actual. pendiente que el menu master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el menu.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateMenu(Menu menu) throws SQLException, Exception {

		String sql = "UPDATE MENU SET ";
		sql += "RESTAURANTE='" + menu.getRestaurante() + "',";
		sql += "COSTO_PRODUCCION=" + menu.getCostoProduccion() + "',";
		sql += "PRECIO_VENTA=" + menu.getPrecioVenta() + ",";
		sql += " WHERE NOMBRE = " + menu.getNombre();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el menu que entra como parametro en la base de datos.
	 * @param menu - el menu a borrar. menu !=  null
	 * <b> post: </b> se ha borrado el menu en la base de datos en la transaction actual. pendiente que el menu master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el menu.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteMenu(Menu menu) throws SQLException, Exception {

		String sql = "DELETE FROM MENU";
		sql += " WHERE NOMBRE = '" + menu.getNombre() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
