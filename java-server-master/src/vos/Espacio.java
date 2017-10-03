package vos;

public class Espacio 
{
	private int id;
	
	private int capacidad;
	
	private boolean esAbierto;
	
	private boolean esApto;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public boolean isEsAbierto() {
		return esAbierto;
	}

	public void setEsAbierto(boolean esAbierto) {
		this.esAbierto = esAbierto;
	}

	public boolean isEsApto() {
		return esApto;
	}

	public void setEsApto(boolean esApto) {
		this.esApto = esApto;
	}
}
