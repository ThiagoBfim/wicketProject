package n3m6.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import n3m6.entity.Modelo;

public interface ModeloRepository extends JpaRepository<Modelo, Integer> {

	List<Modelo> findByDescricaoStartsWithIgnoreCase(String input);

	Modelo findByDescricao(String descricao);

}
