package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaci贸n
 * @author Juan Carre駉
 */
public class DAOTablaPedidos {


	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOPedido
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPedidos() {
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
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los pedidos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PEDIDO;
	 * @return Arraylist con los pedidos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Pedido> darPedidos() throws SQLException, Exception {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("PEDIDO_ID");
			float costoTotal = rs.getFloat("COSTO_TOTAL");
			Date fecha = rs.getDate("FECHA");
			int idUsuario = rs.getInt("ID_USUARIO");
			String entrega = rs.getString("ENTREGADO");
			Pedido ppedido=new Pedido(id, costoTotal, fecha, idUsuario,null,null);
			ppedido.setEntregado(desicion(entrega));;
			pedidos.add(ppedido);
		}
		return pedidos;
	}
	
	/**
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los pedidos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PEDIDO;
	 * @return Arraylist con los pedidos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Integer darPedidoIdUsuario(int id) throws SQLException, Exception {
		
		Integer idUsuario=null;
		String sql = "SELECT * FROM PEDIDO WHERE ID_USUARIO="+id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
		 idUsuario = rs.getInt("PEDIDO_ID");
		}
		return idUsuario;
	}

	/**
	 * Metodo que busca el pedido con el id que entra como parametro.
	 * @param name - Id de el pedido a buscar
	 * @return Pedido encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Pedido buscarPedidoPorId(int id) throws SQLException, Exception 
	{
		Pedido pedido = null;

		String sql = "SELECT * FROM PEDIDO WHERE PEDIDO_ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			int idP = rs.getInt("PEDIDO_ID");
			float costoTotal = rs.getFloat("COSTO_TOTAL");
			Date fecha = rs.getDate("FECHA");
			int idUsuario = rs.getInt("ID_USUARIO");
			pedido = new Pedido(idP, costoTotal, fecha, idUsuario, null,null);
			String entrega = rs.getString("ENTREGADO");
			pedido.setEntregado(desicion(entrega));
		}

		return pedido;
	}
	
	
	private boolean desicion(String ab){
		if(ab.equals("T")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Metodo que agrega el pedido que entra como parametro a la base de datos.
	 * @param pedido - el pedido a agregar. pedido !=  null
	 * <b> post: </b> se ha agregado el pedido a la base de datos en la transaction actual. pendiente que el pedido master
	 * haga commit para que el pedido baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el pedido a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPedido(Pedido pedido) throws SQLException, Exception {

		String sql = "INSERT INTO PEDIDO VALUES (";
		sql += pedido.getId() + ",";
		sql += pedido.getCostoTotal() + ",";
		sql += pedido.getIdUsuario() + ",SYSDATE,'F')";
		 
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		Integer pedidoID = darPedidoIdUsuario(pedido.getIdUsuario()); 
		
		if(pedido.getMenu()!=null){
			sql = "INSERT INTO MENU_PEDIDO VALUES ('"+pedido.getMenu().getNombre()+"','"+pedido.getMenu().getRestaurante()+"',"+pedidoID+")";
		}else{
			sql ="INSERT INTO PEDIDO_PRODUCTO VALUES ("+pedidoID+",'"+pedido.getProducto().getNombre()+"','"+pedido.getRestaurante()+"')";
		}
		
		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Metodo que actualiza el pedido que entra como parametro en la base de datos.
	 * @param pedido - el pedido a actualizar. pedido !=  null
	 * <b> post: </b> se ha actualizado el pedido en la base de datos en la transaction actual. pendiente que el pedido master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el pedido.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Pedido updatePedido( int id ) throws SQLException, Exception {

		Pedido pedido = buscarPedidoPorId(id);
		pedido.setEntregado(true);
		 ; 
		String sql = "UPDATE PEDIDO SET ENTREGADO='T'WHERE PEDIDO_ID="+id;
	
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		return pedido;
	}

	/**
	 * Metodo que elimina el pedido que entra como parametro en la base de datos.
	 * @param pedido - el pedido a borrar. pedido !=  null
	 * <b> post: </b> se ha borrado el pedido en la base de datos en la transaction actual. pendiente que el pedido master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el pedido.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deletePedido(Pedido pedido) throws SQLException, Exception {

		String sql = "DELETE FROM PEDIDO";
		sql += " WHERE PEDIDO_ID = '" + pedido.getId() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
