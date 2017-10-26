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
	private Modelo modelo = new Modelo();

	@Enumerated(EnumType.STRING)
	@NotNull
	private Tracao tracao;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "fabricante_id")
	@NotNull
	private Fabricante fabricante = new Fabricante();

	public String getFabricanteFormat() {
		return getFabricante() == null ? null : getFabricante().toString();
	}

}
