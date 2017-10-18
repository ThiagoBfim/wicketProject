package n3m6.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import n3m6.entity.enuns.Categoria;
import n3m6.entity.enuns.Tracao;
import n3m6.entity.validator.PLACA;

@Entity
@EqualsAndHashCode(of = "id")
@SuppressWarnings("serial")
public @Data class Carro implements Serializable {

	@Id
	@GeneratedValue(generator = "carro", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "carro", sequenceName = "carro_sequence", allocationSize = 1)
	private Integer id;

	@NotEmpty
	@PLACA
	private String placa;

	@ManyToOne
	@JoinColumn(name = "modelo_id")
	@NotNull
	private Modelo modelo;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Tracao tracao;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Categoria categoria;

	@NotEmpty
	private @Getter @Setter String fabricante;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Tracao getTracao() {
		return tracao;
	}

	public void setTracao(Tracao tracao) {
		this.tracao = tracao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

}
