package br.com.projetoJpaHibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.projetoJpaHibernate.util.HibernateUtil;

public class DaoGeneric<E> {

	/*
	 * Cria um entityManager para abrir a conexão com o banco de dados para prover a
	 * parte de persistência
	 */
	private EntityManager entityManager = HibernateUtil.geEntityManager();

	/* Usa-se o método persist() que salva no BD */
	public void salvar(E entidade) {

		/* Captura uma transação para a criação do entityManager */
		EntityTransaction transaction = entityManager.getTransaction();

		/* Abrir a transação */
		transaction.begin();

		/* Persistir no banco os dados do usuário */
		entityManager.persist(entidade);

		/* Salva (ou comita) no banco de dados */
		transaction.commit();
	}

	public E pesquisar(E entidade) {
		Object id = HibernateUtil.getPrimaryKey(entidade);

		E pesquisa = (E) entityManager.find(entidade.getClass(), id);
		return pesquisa;
	}

	public E pesquisar(Long id, Class<E> entidade) {

		E pesquisa = (E) entityManager.find(entidade, id);
		return pesquisa;
	}

	/* Usa-se o método merge() que atualiza e/ou salva no BD */
	public E atualizar(E entidade) {

		/* Captura uma transação para a criação do entityManager */
		EntityTransaction transaction = entityManager.getTransaction();

		/* Abrir a transação */
		transaction.begin();

		/* Salva e/ou atualiza no banco os dados do usuário */
		E entidadeSalva = entityManager.merge(entidade);

		/* Salva (ou comita) no banco de dados */
		transaction.commit();

		return entidadeSalva;
	}

	public void deletarPorId(E entidade) {
		try {

			/* Captura uma transação para a criação do entityManager */
			EntityTransaction transaction = entityManager.getTransaction();

			/* Abrir a transação */
			transaction.begin();

			Object id = HibernateUtil.getPrimaryKey(entidade);

			if (id != null) {

				/* Para deletar usa-se o método executeUpdate() */
				entityManager.createQuery("delete from " + entidade.getClass().getName() + " where id = " + id)
						.executeUpdate();

				transaction.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<E> listarTodos(Class<E> entidade) {

		/* Captura uma transação para a criação do entityManager */
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		/* entidade.getName() é o UsuarioPessoa
		 * A função getResultList() vai listar todos os usuários no banco de dados */
		List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();

		transaction.commit();

		return lista;
	}
	
	
	/* Sendo public o EntityManager é possível acessar de outras partes do projeto */
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
