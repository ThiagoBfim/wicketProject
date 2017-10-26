package n3m6.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(of = "id")
@SuppressWarnings("serial")
@Data
public class Fabricante implements Serializable {

	@Id
	@GeneratedValue(generator = "fabricante", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fabricante", sequenceName = "fabricante_sequence", allocationSize = 1)
	private Integer id;

	@NotEmpty
	private String nome;

	@NotEmpty
	private String pais;

	@Transient
	private Boolean selecionado = Boolean.FALSE;

	@Override
	public String toString() {
		if (StringUtils.isEmpty(getNome())) {
			return "";
		}
		return getNome() + " - " + getPais();
	}

}
