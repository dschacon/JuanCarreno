package vos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


public class Consulta12{
	
	@JsonProperty(value="tipo1")
	private List<Usuario> tipo1;
	@JsonProperty(value="tipo2")
	private List<Usuario> tipo2;
	@JsonProperty(value="tipo3")
	private List<Usuario> tipo3;
	
	public Consulta12()
	{
		tipo1 = null;
		tipo2 = null;
		tipo3 = null;
	}

	public List<Usuario> getTipo1() {
		return tipo1;
	}

	public void setTipo1(List<Usuario> tipo1) {
		this.tipo1 = tipo1;
	}

	public List<Usuario> getTipo2() {
		return tipo2;
	}

	public void setTipo2(List<Usuario> tipo2) {
		this.tipo2 = tipo2;
	}

	public List<Usuario> getTipo3() {
		return tipo3;
	}

	public void setTipo3(List<Usuario> tipo3) {
		this.tipo3 = tipo3;
	}

}
