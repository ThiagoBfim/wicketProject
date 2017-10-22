package n3m6.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import n3m6.entity.Fabricante;

public interface FabricanteRepository extends JpaRepository<Fabricante, Integer> {

	List<Fabricante> findByNomeIgnoreCaseContaining(String nome);

}
