package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.PreferenciaRestaurante;
import vos.Restaurante;

public class DAOTablaPreferenciaRestaurante {
	
	
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOTablaUsuario
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPreferenciaRestaurante() {
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
	
	
	
	public void addPreferencia (PreferenciaRestaurante restaurante) throws SQLException, Exception {
		
	
		String sql = "INSERT INTO PREFERENCIA_RESTAURANTE VALUES (";
		sql += restaurante.getNumeroUsuario() + ",'";
		sql += restaurante.getNombreRestaurante()+"')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		
	}
	
	
	public ArrayList<Restaurante> buscarPreferenciaUsuario(Integer id) throws SQLException {
		
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT NOMBRE, PAGINAWEB , REPRESENTANTE , TIPO_COMIDA, NOMBREZONA "
				+ "FROM(SELECT * FROM PREFERENCIA_RESTAURANTE WHERE NUMERO_USUARIO = "+ id +")JOIN"
				+ "RESTAURANTE ON NOMBRE = NOMBRE_RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			String pagina = rs.getString("PAGINAWEB");
			String representante = rs.getString("REPRESENTANTE");
			String tipoComida = rs.getString("TIPO_COMIDA");
			String nombreZona = rs.getString("NOMBREZONA");
		
			restaurantes.add(new Restaurante(name, tipoComida, pagina, representante, nombreZona));
		}

		return restaurantes;
	}

	
}
