package n3m6.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import n3m6.entity.Carro;
import n3m6.repository.CarroRepository;

@Service
public class CarroService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CarroRepository repository;

	public List<Carro> listar() {
		return repository.findAll();
	}

	public Carro obter(Integer id) {
		return repository.findOne(id);
	}

	@Transactional
	public Carro salvar(Carro carro) {
		return repository.save(carro);
	}

	@Transactional
	public void remover(Integer id) {
		repository.delete(id);
	}

	@Transactional
	public Carro findByPlaca(String placa) {
		return repository.findByPlaca(placa);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Carro> filtrar(Carro filtro) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Carro.class);
		criteria.createAlias("modelo", "modelo");
		criteria.createAlias("fabricante", "fabricante");
		adicionarFiltro(filtro, criteria);

		return criteria.list();
	}

	private void adicionarFiltro(Carro filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getPlaca())) {
				criteria.add(Restrictions.ilike("placa", filtro.getPlaca(), MatchMode.ANYWHERE));
			}
			if (filtro.getModelo() != null && !StringUtils.isEmpty(filtro.getModelo().getDescricao())) {
				criteria.add(
						Restrictions.ilike("modelo.descricao", filtro.getModelo().getDescricao(), MatchMode.ANYWHERE));
			}
			if (filtro.getCategoria() != null) {
				criteria.add(Restrictions.eq("categoria", filtro.getCategoria()));
			}
			if (filtro.getTracao() != null) {
				criteria.add(Restrictions.eq("tracao", filtro.getTracao()));
			}
			if (filtro.getFabricante() != null && !StringUtils.isEmpty(filtro.getFabricante().getNome())) {
				criteria.add(
						Restrictions.ilike("fabricante.nome", filtro.getFabricante().getNome(), MatchMode.ANYWHERE));
			}
		}
	}

}
