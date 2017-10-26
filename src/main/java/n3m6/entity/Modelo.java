package n3m6.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(of = "id")
@SuppressWarnings("serial")
public @Data class Modelo implements Serializable {

	@Id
	@GeneratedValue(generator = "modelo", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "modelo", sequenceName = "modelo_sequence", allocationSize = 1)
	private Integer id;

	@NotEmpty
	private String descricao;

}
