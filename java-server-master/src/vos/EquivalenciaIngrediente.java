package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class EquivalenciaIngrediente 
{
	@JsonProperty(value="nombreIngrediente")
	private String nombreIngrediente;
	
	@JsonProperty(value="ingredienteEquiv")
	private String ingredienteEquiv;

	public EquivalenciaIngrediente(@JsonProperty(value="nombreIngrediente")String nombreI, @JsonProperty(value="ingredienteEquiv")String ingredienteE)
	{
		this.nombreIngrediente = nombreI;
		this.ingredienteEquiv = ingredienteE;
	}

	public String getNombreIngrediente() {
		return nombreIngrediente;
	}

	public void setNombreIngrediente(String nombreIngrediente) {
		this.nombreIngrediente = nombreIngrediente;
	}

	public String getIngredienteEquiv() {
		return ingredienteEquiv;
	}

	public void setIngredienteEquiv(String ingredienteEquiv) {
		this.ingredienteEquiv = ingredienteEquiv;
	}
}
