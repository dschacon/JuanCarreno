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
public class DAOTablaProductos {


	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOProducto
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaProductos() {
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
	 * Metodo que, usando la conexi贸n a la base de datos, saca todos los productos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PRODUCTO;
	 * @return Arraylist con los productos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> darProductos() throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTOS2";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		boolean algo = true;
		while (rs.next() && algo) {
			String name = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			String categoria = rs.getString("CATEGORIA");		
			productos.add(new Producto(name, null, descripcion, traduccion, null, null, null, categoria,null));
			algo = false;
		}
		return productos;
	}


	public String darNombreRestaurante( String nombre) throws SQLException, Exception {

		String sql = "SELECT RESTAURANTE_PRODUCTO.NOMBRE_RESTAURANTE AS NOMBRE FROM PRODUCTO JOIN RESTAURANTE_PRODUCTO ON PRODUCTO.NOMBRE= RESTAURANTE_PRODUCTO.NOMBRE_PRODUCTO WHERE NOMBRE='"+nombre+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		String restaurante= null; 
		
		while (rs.next()) {
			restaurante = rs.getString("NOMBRE");
		}
		return restaurante ;
	}
	
	
	
	
	/**
	 * Metodo que busca el/los productos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los productos a buscar
	 * @return ArrayList con los productos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Producto buscarProductosPorNombre(String name) throws SQLException, Exception {
		Producto productos=null ;

		String sql = "SELECT * FROM PRODUCTO JOIN RESTAURANTE_PRODUCTO ON PRODUCTO.NOMBRE= RESTAURANTE_PRODUCTO.NOMBRE_PRODUCTO WHERE NOMBRE='"+ name +"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			String categoria = rs.getString("CATEGORIA");
			Integer disponible = rs.getInt("DISPONIBLES");
			Integer maximo = rs.getInt("MAXIMO");
			Float costoProduccion=rs.getFloat("COSTO_PRODUCCION");
			Float precioVenta=rs.getFloat("PRECIO_VENTA");
			Float tiempo=rs.getFloat("TIEMPO_PREPARACION");
			productos = new Producto(nombre, disponible , descripcion, traduccion, tiempo , costoProduccion, precioVenta, categoria,maximo);
		}

		return productos;
	}

	/**
	 * Metodo que agrega el producto que entra como parametro a la base de datos.
	 * @param producto - el producto a agregar. producto !=  null
	 * <b> post: </b> se ha agregado el producto a la base de datos en la transaction actual. pendiente que el producto master
	 * haga commit para que el producto baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el producto a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProducto(Producto producto , Restaurante restaurante) throws SQLException, Exception {

		String sql = "SELECT * FROM CATEGORIA WHERE NOMBRE ='" + producto.getCategoria() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!(rs.next())){
			sql = "INSERT INTO CATEGORIA VALUES ('"+ producto.getCategoria() +"')";

			prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
		
		
		sql = "INSERT INTO PRODUCTO VALUES ('";
		sql += producto.getNombre() + "','";
		sql += producto.getDescripcion() + "','";
		sql += producto.getTraduccion() + "','";
		sql += producto.getCategoria() + "')";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		//INSERT INTO RESTAURANTE_PRODUCTO VALUES('Corral','diana',20,10,22,16,16); 
		sql="INSERT INTO RESTAURANTE_PRODUCTO VALUES('";
		
		sql+= restaurante.getNombre()+"',"+"'"+producto.getNombre()+"',"+producto.getCostoProduccion()+","+ producto.getTiempoPreparacion()+"," +producto.getPrecioVenta()+","+producto.getDisponible()+","+producto.getMaximo()+")";
		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Metodo que actualiza el producto que entra como parametro en la base de datos.
	 * @param producto - el producto a actualizar. producto !=  null
	 * <b> post: </b> se ha actualizado el producto en la base de datos en la transaction actual. pendiente que el producto master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el prodcuto.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateProducto(Producto producto) throws SQLException, Exception {

		//UPDATE RESTAURANTE_PRODUCTO SET DISPONIBLES=MAXIMO WHERE NOMBRE_PRODUCTO='alitas3';
		String sql = "UPDATE RESTAURANTE_PRODUCTO SET DISPONIBLES=DISPONIBLES-1 WHERE NOMBRE_PRODUCTO='"+producto.getNombre()+"'"; 
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el producto que entra como parametro en la base de datos.
	 * @param producto - el producto a borrar. producto !=  null
	 * <b> post: </b> se ha borrado el producto en la base de datos en la transaction actual. pendiente que el proucto master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el producto.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteProducto(Producto producto) throws SQLException, Exception {

		String sql = "DELETE FROM PRODUCTO";
		sql += " WHERE NOMBRE = '" + producto.getNombre() + "'";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
