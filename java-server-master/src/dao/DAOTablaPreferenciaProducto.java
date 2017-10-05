package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.PreferenciaProducto;
import vos.Producto;

public class DAOTablaPreferenciaProducto {
	
	
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOPreferenciaProducto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPreferenciaProducto() {
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
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	
	
	
	public void addPreferencia (PreferenciaProducto restaurante) throws SQLException, Exception {
		
	
		String sql = "INSERT INTO PREFERENCIA_RESTAURANTE VALUES (";
		sql += restaurante.getNumeroUsuario() + ",'";
		sql += restaurante.getNombreProducto()+"')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		
	}
	
	
	public ArrayList<Producto> buscarPreferenciaUsuario(Integer id) throws SQLException {
		
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT NOMBRE, DESCRIPCION , TRADUCCION , CATEGORIA "
				+ "FROM(SELECT * FROM PREFERENCIA_PRODUCTO WHERE NUMERO_USUARIO = "+ id +")JOIN"
				+ "PRODUCTO ON NOMBRE = NOMBRE_PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			String categoria = rs.getString("CATEGORIA");	
			productos.add(new Producto(name, null, descripcion, traduccion, null, null, null, categoria));
		}

		return productos;
	}

	
}
