package vos;

public class PreferenciaProducto 
{
	private int numeroUsuario;
	
	private String nombreProducto;

	public PreferenciaProducto() {
		
	}
	
	public int getNumeroUsuario() {
		return numeroUsuario;
	}

	public void setNumeroUsuario(int numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	
}
