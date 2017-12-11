package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import jms.AllProductosMDB;
import jms.NonReplyException;
import jms.RestauranteMDB;
import jms.UtilidadMDB;
import tm.RotondAndesTm;
import vos.ListaProductos;
import vos.ListaUtilidad;
import vos.Producto;
import vos.Utilidad;


public class RotondAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static RotondAndesDistributed instance;
	
	private RotondAndesTm tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	private AllProductosMDB allProdMQ;
	
	private UtilidadMDB utilidadMQ ;
	
	private RestauranteMDB restauranteMQ ;
	
	private static String path;


	private RotondAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		allProdMQ = new AllProductosMDB(factory, ctx);
		utilidadMQ = new UtilidadMDB(factory, ctx);
		restauranteMQ= new RestauranteMDB(factory,ctx);
		restauranteMQ.start();
		allProdMQ.start();
		utilidadMQ.start();
		
	}
	
	public void stop() throws JMSException
	{
		restauranteMQ.close();
		utilidadMQ.close();
		allProdMQ.close();
	}
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(RotondAndesTm tm)
	{
	   this.tm = tm;
	}
	
	private static RotondAndesDistributed getInst()
	{
		return instance;
	}
	
	public static RotondAndesDistributed getInstance(RotondAndesTm tm)
	{
		if(instance == null)
		{
			try {
				instance = new RotondAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static RotondAndesDistributed getInstance()
	{
		if(instance == null)
		{
			RotondAndesTm tm = new RotondAndesTm(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		RotondAndesTm tm = new RotondAndesTm(path);
		return getInstance(tm);
	}
	
	public ListaProductos getLocalVideos() throws Exception
	{
		return tm.darProductosLocales();
	}
	
	public ListaProductos getRemoteVideos() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return allProdMQ.getRemoteVideos();
	}
	
	public ListaUtilidad getLocaUtilidad(String nombre,String fecha ,String fecha2 ) throws Exception
	{
		return tm.utilidad(nombre, fecha, fecha2);
	}
	
	public ListaUtilidad getRemoteUtilidad(String nombre,String fecha ,String fecha2) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return utilidadMQ.getRemoteUtilidad(nombre, fecha, fecha2);
	}
	
	public void getLocalDelete(String nombre) throws Exception
	{
		tm.deleteRestaurante(nombre);
	}
	
	public void getRemoteDelete(String nombre) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		restauranteMQ.getRemoteDelete(nombre);
	}
}
