package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaUtilidad {

	@JsonProperty(value="utilidades")
	private List<Utilidad> utilidades;
	

	public ListaUtilidad( @JsonProperty(value="utilidades")List<Utilidad> videos){
		this.utilidades = videos;
	}


	public List<Utilidad> getUtilidades() {
		return utilidades;
	}


	public void setUtilidades(List<Utilidad> productos) {
		this.utilidades = productos;
	}
}
