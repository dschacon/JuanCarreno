package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.PreferenciaZona;
import vos.Zona;

public class DAOTablaPreferenciaZona {
	
	
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOPreferenciaZona
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPreferenciaZona() {
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
	
	
	
	public void addPreferencia (PreferenciaZona zona) throws SQLException, Exception {
		
	
		String sql = "INSERT INTO PREFERENCIA_RESTAURANTE VALUES (";
		sql += zona.getNumeroUsuario() + ",'";
		sql += zona.getNombreZona()+"')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		
	}
	
	
	public ArrayList<Zona> buscarPreferenciaUsuario(Integer id) throws SQLException {
		
		ArrayList<Zona> zonas = new ArrayList<Zona>();

		String sql = "SELECT NOMBRE "
				+ "FROM(SELECT * FROM PREFERENCIA_ZONA WHERE NUMERO_USUARIO = "+ id +")JOIN"
				+ "ZONA ON NOMBRE = NOMBRE_ZONA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
		
			zonas.add(new Zona(name));
		}

		return zonas;
	}

	
}
