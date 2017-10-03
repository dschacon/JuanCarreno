package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

import vos.Restaurante;
import vos.Usuario;

public class DAOTablaUsuarios {
	
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
		public DAOTablaUsuarios() {
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


		/**
		 * Metodo que, usando la conexión a la base de datos, saca todos los videos de la base de datos
		 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
		 * @return Arraylist con los videos de la base de datos.
		 * @throws SQLException - Cualquier error que la base de datos arroje.
		 * @throws Exception - Cualquier error que no corresponda a la base de datos
		 */
		public ArrayList<Usuario> darUsuarios() throws SQLException, Exception {
			ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

			String sql = "SELECT * FROM USUARIO";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				Integer numero = rs.getInt("NUMERO_ID");
				String nombre = rs.getString("NOMBRE");
				String rol = rs.getString("ROL");
				String email = rs.getString("EMAIL");
				usuarios.add(new Usuario(numero, nombre , rol, email));
			}
			return usuarios;
		}


		/**
		 * Metodo que busca el/los videos con el nombre que entra como parametro.
		 * @param name - Nombre de el/los videos a buscar
		 * @return ArrayList con los videos encontrados
		 * @throws SQLException - Cualquier error que la base de datos arroje.
		 * @throws Exception - Cualquier error que no corresponda a la base de datos
		 */
		public ArrayList<Usuario> buscarUsuarioPorName(String name) throws SQLException, Exception {
			ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

			String sql = "SELECT * FROM USUARIO WHERE NOMBRE ='" + name + "'";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String name2 = rs.getString("NOMBRE");
				Integer id = rs.getInt("NUMERO_ID");
				String rol = rs.getString("ROL");
				String email = rs.getString("EMAIL");
				usuarios.add(new Usuario(id, name2, rol, email));
			}

			return usuarios;
		}
		
		/**
		 * Metodo que busca el video con el id que entra como parametro.
		 * @param name - Id de el video a buscar
		 * @return Video encontrado
		 * @throws SQLException - Cualquier error que la base de datos arroje.
		 * @throws Exception - Cualquier error que no corresponda a la base de datos
		 */
		public Usuario buscarUsuarioPorId(Integer id) throws SQLException, Exception 
		{
			Usuario usuario = null;

			String sql = "SELECT * FROM USUARIO WHERE NUMERO_ID  =" + id;

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();

			if(rs.next()) {
				Integer id2 = rs.getInt("NUMERO_ID");
				String name2 = rs.getString("NOMBRE");
				String rol = rs.getString("ROL");
				String email = rs.getString("EMAIL");
				usuario = new Usuario(id2, name2, rol, email);
			}

			return usuario;
		}

		/**
		 * Metodo que agrega el video que entra como parametro a la base de datos.
		 * @param usuario - el video a agregar. video !=  null
		 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
		 * haga commit para que el video baje  a la base de datos.
		 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
		 * @throws Exception - Cualquier error que no corresponda a la base de datos
		 */
		public void addUsuario(Usuario usuario) throws SQLException, Exception {

			String sql = "INSERT INTO USUARIO VALUES (";
			sql += usuario.getId() + ",'";
			sql += usuario.getNombre() + "','";
			sql += usuario.getRol() + "','";
			sql += usuario.getEmail() + "')";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();

		}
		
		

}
