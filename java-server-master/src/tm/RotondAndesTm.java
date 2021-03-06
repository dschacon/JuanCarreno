package tm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import dao.*;
import dtm.RotondAndesDistributed;
import jms.NonReplyException;
import vos.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RotondAndesTm {

	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;

	private RotondAndesDistributed dtm;
	
	/**
	 * Metodo constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTm( String pathp) {
		connectionDataPath = pathp + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
		dtm=RotondAndesDistributed.getInstance(this);
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////



	/**
	 * Metodo que modela la transaccion que busca el/los usuarios en la base de datos con el nombre entra como parametro.
	 * @param name - Nombre del usuario a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Usuario> buscarUsuariosporName( String name ) throws Exception {
		List<Usuario> usuarios;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuarios = daoUsuarios.buscarUsuarioPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}


	/**
	 * Metodo que modela la transaccion que busca el video en la base de datos con el id que entra como parametro.
	 * @param name - Id del video a buscar. name != null
	 * @return Video - Resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Usuario buscarUsuarioPorId(Integer id) throws Exception {
		Usuario video;
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			video = daoUsuario.buscarUsuarioPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return video;
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parametro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genere agregando el video
	 */
	public void addUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoUsuario.addUsuario(usuario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<Restaurante> darRestaurantes() throws Exception {
		List<Restaurante> restaurantes;
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try 
		{
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurantes = daoRestaurantes.darRestaurantes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurantes;
	}

	public Restaurante buscarRestaurantesPorName(String name) throws Exception{
		Restaurante restaurante;
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try 
		{
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			restaurante = daoRestaurantes.buscarRestaurantesPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurante;
	}

	public void addRestaurante(Restaurante restaurante) throws Exception {
		DAOTablaRestaurantes daoRestaurante = new DAOTablaRestaurantes();
		try 
		{
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			daoRestaurante.addRestaurante(restaurante);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateRestaurante(String producto) throws Exception {
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try 
		{
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.updateRestaurante(producto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteRestaurante(String restaurante) throws Exception {
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try 
		{
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.deleteRestaurante(restaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<Menu> darMenus() throws Exception {
		List<Menu> menus;
		DAOTablaMenus daoMenus = new DAOTablaMenus();
		try 
		{
			this.conn = darConexion();
			daoMenus.setConn(conn);
			menus = daoMenus.darMenus();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menus;
	}

	public Menu buscarMenusPorName(String name) throws Exception{
		Menu menus;
		DAOTablaMenus daoMenus = new DAOTablaMenus();
		try 
		{
			this.conn = darConexion();
			daoMenus.setConn(conn);
			menus = daoMenus.buscarMenusPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenus.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menus;
	}

	public void addMenu(Menu menu) throws Exception{
		DAOTablaMenus daoMenu = new DAOTablaMenus();
		try 
		{
			this.conn = darConexion();
			daoMenu.setConn(conn);
			daoMenu.addMenu(menu);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	public void updateMenu(Menu menu) throws Exception{
		DAOTablaMenus daoMenu = new DAOTablaMenus();
		try 
		{
			this.conn = darConexion();
			daoMenu.setConn(conn);
			daoMenu.updateMenu(menu);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}
	
	public String buscarRestauranteProducto(String procucto) throws Exception{
		DAOTablaProductos daoprodu = new DAOTablaProductos();
		try 
		{
			this.conn = darConexion();
			daoprodu.setConn(conn);
			String a = daoprodu.darNombreRestaurante(procucto);
			conn.commit();
			return a;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoprodu.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	public void deleteMenu(Menu menu) throws Exception {
		DAOTablaMenus daoMenu = new DAOTablaMenus();
		try 
		{
			this.conn = darConexion();
			daoMenu.setConn(conn);
			daoMenu.deleteMenu(menu);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	public List<Pedido> darPedidos() throws Exception{
		List<Pedido> pedidos;
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		try 
		{
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			pedidos = daoPedidos.darPedidos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedidos;
	}

	public Pedido buscarPedidoPorId(int id) throws Exception{
		Pedido pedido;
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		try 
		{
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			pedido = daoPedidos.buscarPedidoPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedido;
	}

	public void addPedido(Pedido pedido) throws Exception{
		DAOTablaPedidos daoPedido = new DAOTablaPedidos();
		try 
		{
			this.conn = darConexion();
			daoPedido.setConn(conn);
			daoPedido.addPedido(pedido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	public Pedido updatePedido(int id) throws Exception {
		DAOTablaPedidos daoPedido = new DAOTablaPedidos();
		try 
		{
			this.conn = darConexion();
			daoPedido.setConn(conn);
			 Pedido pedido = daoPedido.updatePedido(id);
			conn.commit();
			return pedido;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	public void deletePedido(Pedido pedido) throws Exception {
		DAOTablaPedidos daoPedido = new DAOTablaPedidos();
		try 
		{
			this.conn = darConexion();
			daoPedido.setConn(conn);
			daoPedido.deletePedido(pedido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	public List<Zona> darZonas() throws Exception{
		List<Zona> zonas;
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		try 
		{
			this.conn = darConexion();
			daoZonas.setConn(conn);
			zonas = daoZonas.darZonas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonas;
	}

	public List<Zona> buscarZonasPorName(String name) throws Exception{
		List<Zona> zonas;
		DAOTablaZonas daoZonas = new DAOTablaZonas();
		try 
		{
			this.conn = darConexion();
			daoZonas.setConn(conn);
			zonas = daoZonas.buscarZonasPorName(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZonas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonas;
	}

	public void addZona(Zona zona) throws Exception {
		DAOTablaZonas daoZona = new DAOTablaZonas();
		try 
		{
			this.conn = darConexion();
			daoZona.setConn(conn);
			daoZona.addZona(zona);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteZona(Zona zona) throws Exception{
		DAOTablaZonas daoZona = new DAOTablaZonas();
		try 
		{
			this.conn = darConexion();
			daoZona.setConn(conn);
			daoZona.deleteZona(zona);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public Producto buscarProductoPorName(String nombre) throws Exception {
		Producto producto;
		DAOTablaProductos daoProducto = new DAOTablaProductos();
		try 
		{
			this.conn = darConexion();
			daoProducto.setConn(conn);
			producto = daoProducto.buscarProductosPorNombre(nombre);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return producto;
	}

	public void addProducto(Producto producto, Restaurante restaurante) throws Exception {
		DAOTablaProductos daoProducto = new DAOTablaProductos();
		try 
		{
			this.conn = darConexion();
			daoProducto.setConn(conn);
			daoProducto.addProducto(producto, restaurante);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public List<Ingrediente> darIngredientes() throws Exception {
		List<Ingrediente> ingredientes;
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try 
		{
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingredientes = daoIngredientes.darIngredientes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredientes;
	}

	public List<Ingrediente> buscarIngredientesPorName(String name) throws Exception{
		List<Ingrediente> ingredientes;
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try 
		{
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			ingredientes = daoIngredientes.buscarIngredientesPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredientes;
	}

	public void addIngrediente(Ingrediente ingrediente) throws Exception {
		DAOTablaIngredientes daoIngrediente = new DAOTablaIngredientes();
		try 
		{
			this.conn = darConexion();
			daoIngrediente.setConn(conn);
			daoIngrediente.addIngrediente(ingrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngrediente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateIngrediente(Ingrediente ingrediente) throws Exception {
		DAOTablaIngredientes daoIngrediente = new DAOTablaIngredientes();
		try 
		{
			this.conn = darConexion();
			daoIngrediente.setConn(conn);
			daoIngrediente.updateIngrediente(ingrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngrediente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteIngrediente(Ingrediente ingrediente) throws Exception{
		DAOTablaIngredientes daoIngrediente = new DAOTablaIngredientes();
		try 
		{
			this.conn = darConexion();
			daoIngrediente.setConn(conn);
			daoIngrediente.deleteIngrediente(ingrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngrediente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<Restaurante> darPreferenciasRestaurante(Integer id) throws Exception{
		List<Restaurante> preferencias;
		DAOTablaPreferenciaRestaurante daoPreferencias = new DAOTablaPreferenciaRestaurante();
		try 
		{
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			preferencias = daoPreferencias.buscarPreferenciaUsuario(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencias;
	}

	public void addPreferenciaRestaurante(PreferenciaRestaurante preferencia) throws Exception {
		DAOTablaPreferenciaRestaurante daoPreferencia = new DAOTablaPreferenciaRestaurante();
		try 
		{
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			daoPreferencia.addPreferencia(preferencia);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<Producto> darPreferenciasProducto(Integer id) throws Exception {
		List<Producto> preferencias;
		DAOTablaPreferenciaProducto daoPreferencias = new DAOTablaPreferenciaProducto();
		try 
		{
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			preferencias = daoPreferencias.buscarPreferenciaUsuario(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencias;
	}

	public void addPreferenciaProducto(PreferenciaProducto preferencia) throws Exception {
		DAOTablaPreferenciaProducto daoPreferencia = new DAOTablaPreferenciaProducto();
		try 
		{
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			daoPreferencia.addPreferencia(preferencia);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<Zona> darPreferenciasZona(Integer id) throws Exception {
		List<Zona> preferencias;
		DAOTablaPreferenciaZona daoPreferencias = new DAOTablaPreferenciaZona();
		try 
		{
			this.conn = darConexion();
			daoPreferencias.setConn(conn);
			preferencias = daoPreferencias.buscarPreferenciaUsuario(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return preferencias;
	}

	public void addPreferenciaZona(PreferenciaZona preferencia) throws Exception{
		DAOTablaPreferenciaZona daoPreferencia = new DAOTablaPreferenciaZona();
		try 
		{
			this.conn = darConexion();
			daoPreferencia.setConn(conn);
			daoPreferencia.addPreferencia(preferencia);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateProducto(Producto producto ) throws Exception {
		DAOTablaProductos daoProducto = new DAOTablaProductos();
		try 
		{
			this.conn = darConexion();
			daoProducto.setConn(conn);
			daoProducto.updateProducto(producto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addPedidoMesa(PedidoMesa pedido) throws SQLException {
		DAOTablaPedidos daoPedido = new DAOTablaPedidos();
		try 
		{
			this.conn = darConexion();
			daoPedido.setConn(conn);
			daoPedido.addPedidoMesa(pedido);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	


	public List<Usuario> darUsuarios() throws Exception{
		List<Usuario> usuarios;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuarios = daoUsuarios.darUsuarios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

	public List<EquivalenciaIngrediente> darEquivalenciasIngrediente() throws Exception{
		List<EquivalenciaIngrediente> equivalencias;
		DAOTablaEquivalenciaIngrediente daoEquivalencia = new DAOTablaEquivalenciaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoEquivalencia.setConn(conn);
			equivalencias = daoEquivalencia.darEquivalencias();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return equivalencias;
	}

	public List<EquivalenciaIngrediente> buscarEquivalenciasIngredientePorName(String name) throws Exception{
		List<EquivalenciaIngrediente> equivalencias;
		DAOTablaEquivalenciaIngrediente daoEquivalencia = new DAOTablaEquivalenciaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoEquivalencia.setConn(conn);
			equivalencias = daoEquivalencia.buscarEquivalenciasPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return equivalencias;
	}

	public void addEquivalenciaIngrediente(EquivalenciaIngrediente equivalencia) throws Exception{
		DAOTablaEquivalenciaIngrediente daoEquivalencia = new DAOTablaEquivalenciaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoEquivalencia.setConn(conn);
			daoEquivalencia.addEquivalencia(equivalencia);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateEquivalenciaIngrediente(EquivalenciaIngrediente equivalencia) throws Exception {
		DAOTablaEquivalenciaIngrediente daoEquivalencia = new DAOTablaEquivalenciaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoEquivalencia.setConn(conn);
			daoEquivalencia.updateEquivalencia(equivalencia);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteEquivalenciaIngrediente(EquivalenciaIngrediente equivalencia) throws Exception {
		DAOTablaEquivalenciaIngrediente daoEquivalencia = new DAOTablaEquivalenciaIngrediente();
		try 
		{
			this.conn = darConexion();
			daoEquivalencia.setConn(conn);
			daoEquivalencia.deleteEquivalencia(equivalencia);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<EquivalenciaProducto> darEquivalenciasProducto() throws Exception {
		List<EquivalenciaProducto> equivalencias;
		DAOTablaEquivalenciaProducto daoEquivalencia = new DAOTablaEquivalenciaProducto();
		try 
		{
			this.conn = darConexion();
			daoEquivalencia.setConn(conn);
			equivalencias = daoEquivalencia.darEquivalencias();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return equivalencias;
	}

	public PedidoMesa buscarPedidoMesaId(int id) throws SQLException {
		PedidoMesa pedido;
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		try 
		{
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			pedido = daoPedidos.buscarPedidoMesaPorId(id);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedido;
	}

	public List<EquivalenciaProducto> buscarEquivalenciasProductoPorName(String name)throws Exception{
		List<EquivalenciaProducto> equivalencia;
		DAOTablaEquivalenciaProducto daoEquivalencia = new DAOTablaEquivalenciaProducto();
		try 
		{
			this.conn = darConexion();
			daoEquivalencia.setConn(conn);
			equivalencia = daoEquivalencia.buscarEquivalenciasPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return equivalencia;
	}

	public void addEquivalenciaProducto(EquivalenciaProducto equivalencia) throws Exception {
		DAOTablaEquivalenciaProducto daoEquivalencia = new DAOTablaEquivalenciaProducto();
		try 
		{
			this.conn = darConexion();
			daoEquivalencia.setConn(conn);
			daoEquivalencia.addEquivalencia(equivalencia);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void updateEquivalenciaProducto(EquivalenciaProducto equivalencia) throws Exception{
		DAOTablaEquivalenciaProducto daoEquivalencia = new DAOTablaEquivalenciaProducto();
		try 
		{
			this.conn = darConexion();
			daoEquivalencia.setConn(conn);
			daoEquivalencia.updateEquivalencia(equivalencia);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteEquivalenciaProducto(EquivalenciaProducto equivalencia) throws Exception{
		DAOTablaEquivalenciaProducto daoEquivalencia = new DAOTablaEquivalenciaProducto();
		try 
		{
			this.conn = darConexion();
			daoEquivalencia.setConn(conn);
			daoEquivalencia.deleteEquivalencia(equivalencia);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquivalencia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	public List<Consulta9> darConsulta9(Consulta9 consulta) throws Exception {
		List<Consulta9> consultas;
		DAOConsultas daoConsultas = new DAOConsultas();
		try 
		{
			this.conn = darConexion();
			daoConsultas.setConn(conn);
			consultas = daoConsultas.darConsulta9(consulta);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoConsultas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return consultas;
	}

	public List<Consulta10> darConsulta10(Consulta10 consulta) throws Exception {
		List<Consulta10> consultas;
		DAOConsultas daoConsultas = new DAOConsultas();
		try 
		{
			this.conn = darConexion();
			daoConsultas.setConn(conn);
			consultas = daoConsultas.darConsulta10(consulta);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoConsultas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return consultas;
	}

	public List<Consulta11> darConsulta11() throws Exception{
		List<Consulta11> consultas;
		DAOConsultas daoConsultas = new DAOConsultas();
		try 
		{
			this.conn = darConexion();
			daoConsultas.setConn(conn);
			consultas = daoConsultas.darConsulta11();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoConsultas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return consultas;
	}

	public List<Consulta12> darConsulta12() throws Exception {
		List<Consulta12> consultas;
		DAOConsultas daoConsultas = new DAOConsultas();
		try 
		{
			this.conn = darConexion();
			daoConsultas.setConn(conn);
			consultas = daoConsultas.darConsulta12();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoConsultas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return consultas;
	}

	public ListaProductos darProductosLocales() throws Exception {
		List<ProductoIter5> productos= new ArrayList<>();
		ArrayList<Producto> productosLocales;
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try 
		{
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productosLocales = daoProductos.darProductos();
			Iterator<Producto> iter = productosLocales.iterator();
			while(iter.hasNext()){
				Producto l = iter.next();
				productos.add(new ProductoIter5("Rotonda 1",l.getNombre(),0, l.getPrecioVenta(), l.getCostoProduccion(), null ,l.getTiempoPreparacion(),null, l.getDescripcion(), l.getDisponible().intValue(),null, l.getMaximo().intValue()));
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaProductos(productos);
	}
	
	public ListaProductos darProductos() throws Exception {
		ListaProductos remL = darProductosLocales();
		try
		{
			ListaProductos resp = dtm.getRemoteVideos();
			System.out.println(resp.getProductos().size());
			remL.getProductos().addAll(resp.getProductos());
		}
		catch(NonReplyException e)
		{
			System.out.println("ALGO PASO ");
			
		}
		return remL;
	}
	
	public ListaUtilidad utilidad(String nombre , String fecha,String fecha2) throws Exception {
		ListaUtilidad consultas;
		DAOConsultas daoConsultas = new DAOConsultas();
		try 
		{
			this.conn = darConexion();
			daoConsultas.setConn(conn);
			consultas = new ListaUtilidad(daoConsultas.darUtilidad(nombre , fecha, fecha2));

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoConsultas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return consultas;
	}
	
	public ListaUtilidad darUtilidadRemote(String nombre, String fechaI, String fechaF) throws Exception {
		
			ListaUtilidad remL = utilidad(nombre, fechaI, fechaF);

			try
			{
				ListaUtilidad resp = dtm.getRemoteUtilidad(nombre, fechaI, fechaF);
				remL.getUtilidades().addAll(resp.getUtilidades());
			}
			catch(NonReplyException e)
			{
				e.printStackTrace();
			}
			return remL;
		}
	
	public void deleteRestauranteRemote(String nombre) throws Exception {
		
		deleteRestauranteRemote(nombre);

		try
		{
			dtm.getRemoteDelete(nombre);
		}
		catch(NonReplyException e)
		{
			e.printStackTrace();
		}
	}
}


