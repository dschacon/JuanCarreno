package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;

import vos.*;

public class DAOConsultas {

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
	public DAOConsultas() {
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
	public ArrayList<Consulta9> darConsulta9(Consulta9 consulta) throws SQLException, Exception {
		ArrayList<Consulta9> consultas = new ArrayList<Consulta9>();

		String sql = "SELECT USUARIO.*,PEDIDOS2.FECHA, PEDIDO_PRODUCTO.NOMBRE_PRODUCTO , PEDIDO_PRODUCTO.NOMBRE_RESTAURANTE , CATEGORIA\r\n" + 
				"FROM((USUARIO JOIN PEDIDOS2 ON ID_USUARIO = USUARIO.NUMERO_ID) JOIN PEDIDO_PRODUCTO ON PEDIDOS2.PEDIDO_ID= PEDIDO_PRODUCTO.ID_PEDIDO)JOIN PRODUCTOS2 ON PEDIDO_PRODUCTO.NOMBRE_PRODUCTO=PRODUCTOS2.NOMBRE\r\n" + 
				"WHERE NOMBRE_RESTAURANTE='"+consulta.getNombreRestaurante()+"' AND FECHA BETWEEN '"+consulta.getFecha1()+"' AND '"+consulta.getFecha2()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Integer numero = rs.getInt("NUMERO_ID");
			String nombreUsuario = rs.getString("NOMBRE");
			String rol = rs.getString("ROL");
			String email = rs.getString("EMAIL");
			String producto = rs.getString("NOMBRE_PRODUCTO");
			String categoria = rs.getString("CATEGORIA");
			String fechaRespuesta = rs.getString("FECHA");
			Usuario usuario = new Usuario(numero, nombreUsuario , rol, email);
			Consulta9 cons = new Consulta9(consulta.getFecha1(), consulta.getFecha2(), consulta.getNombreRestaurante());
			cons.setCategoria(categoria);
			cons.setFechaRespuesta(fechaRespuesta);
			cons.setUsuario(usuario);
			cons.setNombreProducto(producto);
			consultas.add(cons);
		}
		return consultas;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Consulta10> darConsulta10(Consulta10 consulta) throws SQLException, Exception {
		ArrayList<Consulta10> consultas = new ArrayList<Consulta10>();
		int x = 0;

		String sql = "SELECT USUARIO.*,PEDIDOS2.FECHA, PEDIDO_PRODUCTO.NOMBRE_PRODUCTO , PEDIDO_PRODUCTO.NOMBRE_RESTAURANTE , CATEGORIA\r\n" + 
				"FROM((USUARIO JOIN PEDIDOS2 ON ID_USUARIO = USUARIO.NUMERO_ID) JOIN PEDIDO_PRODUCTO ON PEDIDOS2.PEDIDO_ID= PEDIDO_PRODUCTO.ID_PEDIDO)JOIN PRODUCTOS2 ON PEDIDO_PRODUCTO.NOMBRE_PRODUCTO=PRODUCTOS2.NOMBRE\r\n" + 
				"WHERE NOT (NOMBRE_RESTAURANTE='"+consulta.getNombreRestaurante()+"' OR (FECHA BETWEEN '"+consulta.getFecha1()+"' AND '"+consulta.getFecha2()+"'))";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next() && x < 5) {
			Integer numero = rs.getInt("NUMERO_ID");
			String nombreUsuario = rs.getString("NOMBRE");
			String rol = rs.getString("ROL");
			String email = rs.getString("EMAIL");
			String producto = rs.getString("NOMBRE_PRODUCTO");
			String categoria = rs.getString("CATEGORIA");
			String fechaRespuesta = rs.getString("FECHA");
			Usuario usuario = new Usuario(numero, nombreUsuario , rol, email);
			Consulta10 cons = new Consulta10(consulta.getFecha1(), consulta.getFecha2(), consulta.getNombreRestaurante());
			cons.setCategoria(categoria);
			cons.setFechaRespuesta(fechaRespuesta);
			cons.setUsuario(usuario);
			cons.setNombreProducto(producto);
			consultas.add(cons);
			x++;
		}
		return consultas;
	}

	public List<Consulta11> darConsulta11()throws SQLException, Exception {
		ArrayList<Consulta11> consultas = new ArrayList<Consulta11>();
		ArrayList<Consulta11> consultas2 = new ArrayList<Consulta11>();


		String sqlPMas = "SELECT DIA, MAX(CANTIDAD) AS PROD_MAX, MAX(NOMBREPA) KEEP (DENSE_RANK FIRST ORDER BY CANTIDAD DESC) AS PRODUCTO\r\n" + 
				"FROM(SELECT NOMBRE_PRODUCTO AS NOMBREPA, COUNT(NOMBRE_PRODUCTO) AS CANTIDAD, TO_CHAR(FECHA,'DAY') AS DIA \r\n" + 
				"FROM PEDIDOS2 JOIN PEDIDO_PRODUCTO ON PEDIDOS2.PEDIDO_ID = PEDIDO_PRODUCTO.ID_PEDIDO\r\n" + 
				"GROUP BY NOMBRE_PRODUCTO, TO_CHAR(FECHA,'DAY')) GROUP BY DIA";

		PreparedStatement prepStmt = conn.prepareStatement(sqlPMas);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String dia = rs.getString("DIA");
			String productoMas = rs.getString("PRODUCTO");
			Consulta11 cons = new Consulta11();
			cons.setDia(dia);
			cons.setProductoMas(productoMas);
			consultas2.add(cons);
		}	

		String sqlRMas = "SELECT DIA, MAX(CANTIDAD) AS REST_MAX, MAX(NOMBREPA) KEEP (DENSE_RANK FIRST ORDER BY CANTIDAD DESC) AS RESTAURANTE\r\n" + 
				"FROM(SELECT NOMBRE_RESTAURANTE AS NOMBREPA, COUNT(NOMBRE_RESTAURANTE) AS CANTIDAD, TO_CHAR(FECHA,'DAY') AS DIA \r\n" + 
				"FROM PEDIDOS2 JOIN PEDIDO_PRODUCTO ON PEDIDOS2.PEDIDO_ID = PEDIDO_PRODUCTO.ID_PEDIDO\r\n" + 
				"GROUP BY NOMBRE_RESTAURANTE, TO_CHAR(FECHA,'DAY')) GROUP BY DIA";

		PreparedStatement prepStmt2 = conn.prepareStatement(sqlRMas);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();
		int i = 0;

		while (rs2.next()) {
			String dia = rs2.getString("DIA");
			String restauranteMas = rs2.getString("RESTAURANTE");

			Consulta11 consL = consultas2.get(i);
			String diaL =consL.getDia();
			if(diaL.equals(dia)) {
				consL.setRestauranteMas(restauranteMas);
			}
			else if(diaL.equals(dia)) {
				consL.setRestauranteMas(restauranteMas);
			}
			else if(diaL.equals(dia)) {
				consL.setRestauranteMas(restauranteMas);
			}
			else if(diaL.equals(dia)) {
				consL.setRestauranteMas(restauranteMas);
			}
			else if(diaL.equals(dia)) {
				consL.setRestauranteMas(restauranteMas);
			}
			else if(diaL.equals(dia)) {
				consL.setRestauranteMas(restauranteMas);
			}
			else{
				consL.setRestauranteMas(restauranteMas);
			}
			i++;
		}

		String sqlPMenos = "SELECT DIA, MIN(CANTIDAD) AS PROD_MIN, MIN(NOMBREPA) KEEP (DENSE_RANK FIRST ORDER BY CANTIDAD DESC) AS PRODUCTO\r\n" + 
				"FROM(SELECT NOMBRE_PRODUCTO AS NOMBREPA, COUNT(NOMBRE_PRODUCTO) AS CANTIDAD, TO_CHAR(FECHA,'DAY') AS DIA \r\n" + 
				"FROM PEDIDOS2 JOIN PEDIDO_PRODUCTO ON PEDIDOS2.PEDIDO_ID = PEDIDO_PRODUCTO.ID_PEDIDO\r\n" + 
				"GROUP BY NOMBRE_PRODUCTO, TO_CHAR(FECHA,'DAY')) GROUP BY DIA";

		PreparedStatement prepStmt3 = conn.prepareStatement(sqlPMenos);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();
		i = 0;

		while (rs3.next()) {
			String dia = rs3.getString("DIA");
			String productoMenos = rs3.getString("PRODUCTO");

			Consulta11 consL = consultas2.get(i);
			String diaL =consL.getDia();
			if(diaL.equals(dia)) {
				consL.setProductoMenos(productoMenos);
			}
			else if(diaL.equals(dia)) {
				consL.setProductoMenos(productoMenos);
			}
			else if(diaL.equals(dia)) {
				consL.setProductoMenos(productoMenos);
			}
			else if(diaL.equals(dia)) {
				consL.setProductoMenos(productoMenos);
			}
			else if(diaL.equals(dia)) {
				consL.setProductoMenos(productoMenos);
			}
			else if(diaL.equals(dia)) {
				consL.setProductoMenos(productoMenos);
			}
			else{
				consL.setProductoMenos(productoMenos);
			}
			i++;
		}

		String sqlRMenos = "SELECT DIA, MIN(CANTIDAD) AS REST_MIN, MIN(NOMBREPA) KEEP (DENSE_RANK FIRST ORDER BY CANTIDAD DESC) AS RESTAURANTE\r\n" + 
				"FROM(SELECT NOMBRE_RESTAURANTE AS NOMBREPA, COUNT(NOMBRE_RESTAURANTE) AS CANTIDAD, TO_CHAR(FECHA,'DAY') AS DIA \r\n" + 
				"FROM PEDIDOS2 JOIN PEDIDO_PRODUCTO ON PEDIDOS2.PEDIDO_ID = PEDIDO_PRODUCTO.ID_PEDIDO\r\n" + 
				"GROUP BY NOMBRE_RESTAURANTE, TO_CHAR(FECHA,'DAY')) GROUP BY DIA";

		PreparedStatement prepStmt4 = conn.prepareStatement(sqlRMenos);
		recursos.add(prepStmt4);
		ResultSet rs4 = prepStmt4.executeQuery();
		i = 0;

		while (rs4.next()) {
			String dia = rs4.getString("DIA");
			String restauranteMenos = rs4.getString("RESTAURANTE");

			Consulta11 consL = consultas2.get(i);
			String diaL =consL.getDia();
			if(diaL.equals(dia)) {
				consL.setRestauranteMenos(restauranteMenos);
			}
			else if(diaL.equals(dia)) {
				consL.setRestauranteMenos(restauranteMenos);
			}
			else if(diaL.equals(dia)) {
				consL.setRestauranteMenos(restauranteMenos);
			}
			else if(diaL.equals(dia)) {
				consL.setRestauranteMenos(restauranteMenos);
			}
			else if(diaL.equals(dia)) {
				consL.setRestauranteMenos(restauranteMenos);
			}
			else if(diaL.equals(dia)) {
				consL.setRestauranteMenos(restauranteMenos);
			}
			else{
				consL.setRestauranteMenos(restauranteMenos);
			}
			consultas.add(consL);
			i++;
		}
		return consultas;
	}

	public List<Consulta12> darConsulta12() throws SQLException, Exception {
		ArrayList<Consulta12> consultas = new ArrayList<Consulta12>();
		ArrayList<Consulta12> consultas2 = new ArrayList<Consulta12>();

		String sqlTipo2 = "SELECT *  FROM USUARIO WHERE NUMERO_ID NOT IN \r\n" + 
				"(SELECT USUARIO.NUMERO_ID FROM USUARIO JOIN PEDIDOS2 ON ID_USUARIO=NUMERO_ID WHERE COSTO_TOTAL < 36750 ) AND ROL='Cliente'";

		PreparedStatement prepStmt = conn.prepareStatement(sqlTipo2);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		int x = 0;

		while (rs.next() && x < 5) {
			Integer numero = rs.getInt("NUMERO_ID");
			String nombre = rs.getString("NOMBRE");
			String rol = rs.getString("ROL");
			String email = rs.getString("EMAIL");
			Usuario us = new Usuario(numero, nombre , rol, email);
			Consulta12 cons = new Consulta12();
			ArrayList<Usuario> tipo2 = new ArrayList<Usuario>();
			tipo2.add(us);
			cons.setTipo2(tipo2);
			consultas2.add(cons);
			x++;
		}

		String sqlTipo3 = "SELECT * FROM USUARIO WHERE NUMERO_ID NOT IN \r\n" + 
				"(SELECT NUMERO_ID FROM (USUARIO JOIN PEDIDOS2 ON USUARIO.NUMERO_ID=PEDIDOS2.ID_USUARIO)JOIN  MENU_PEDIDO ON MENU_PEDIDO.ID_PEDIDO=PEDIDOS2.PEDIDO_ID) AND ROL='Cliente'";

		PreparedStatement prepStmt2 = conn.prepareStatement(sqlTipo3);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();
		x = 0;
		int i = 0;

		while (rs2.next() && x < 5) {
			Integer numero = rs2.getInt("NUMERO_ID");
			String nombre = rs2.getString("NOMBRE");
			String rol = rs2.getString("ROL");
			String email = rs2.getString("EMAIL");
			Usuario us = new Usuario(numero, nombre , rol, email);
			Consulta12 consL = consultas2.get(i);
			ArrayList<Usuario> tipo3 = new ArrayList<Usuario>();
			tipo3.add(us);
			consL.setTipo3(tipo3);
			consultas.add(consL);
			x++;
			i++;
		}
		return consultas;
	}

}
