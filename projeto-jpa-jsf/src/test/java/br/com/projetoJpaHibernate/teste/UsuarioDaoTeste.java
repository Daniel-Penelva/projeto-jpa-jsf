package br.com.projetoJpaHibernate.teste;

import java.util.List;

import org.junit.Test;

import br.com.projetoJpaHibernate.dao.DaoGeneric;
import br.com.projetoJpaHibernate.model.TelefoneUsuarioPesssoa;
import br.com.projetoJpaHibernate.model.UsuarioPessoa;

public class UsuarioDaoTeste {

	// @Test
	public void usuarioDaoTestSalvar() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setNome("Daniel");
		pessoa.setSobrenome("Caio");
		pessoa.setEmail("caio@gmail.com");
		pessoa.setLogin("daniel-caio");
		pessoa.setSenha("123");
		pessoa.setIdade(32);

		daoGeneric.salvar(pessoa);
	}

	// @Test
	public void usuarioDaoTestBuscar() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(2L);

		pessoa = daoGeneric.pesquisar(pessoa);

		/* Imprimi o toString */
		System.out.println(pessoa);
	}

	// @Test
	public void usuarioDaoTestBuscar2() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar(1L, UsuarioPessoa.class);

		/* Imprimi o toString */
		System.out.println(pessoa);
	}

	// @Test
	public void usuarioDaoTestAtualizar() {

		// Cria o entityManager
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		// Busca o usuário pelo id
		UsuarioPessoa pessoa = daoGeneric.pesquisar(2L, UsuarioPessoa.class);

		// Seta novos valores
		pessoa.setIdade(27);
		pessoa.setEmail("psilva@gmail.com");

		// Chama o método atualizar
		pessoa = daoGeneric.atualizar(pessoa);

		/* Imprimi o toString */
		System.out.println(pessoa);
	}

	// @Test
	public void usuarioDaoTestDeletar() {
		// Cria o entityManager
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		// Busca o usuário pelo id
		UsuarioPessoa pessoa = daoGeneric.pesquisar(3L, UsuarioPessoa.class);

		daoGeneric.deletarPorId(pessoa);

	}

	// @Test
	public void usuarioDaoTestListarTodos() {
		// Cria o entityManager
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> lista = daoGeneric.listarTodos(UsuarioPessoa.class);

		for (UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
			System.out.println(" --------------------------------------------------------- ");
		}
	}

	// @Test
	public void testeQueryList() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		// List<UsuarioPessoa> lista = daoGeneric.getEntityManager().createQuery(" from
		// UsuarioPessoa").getResultList();
		List<UsuarioPessoa> lista = daoGeneric.getEntityManager()
				.createQuery(" from UsuarioPessoa where nome = 'Daniel'").getResultList();

		for (UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
		}
	}

	// @Test
	public void testeQueryListMaxResult() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		// List<UsuarioPessoa> lista = daoGeneric.getEntityManager().createQuery(" from
		// UsuarioPessoa order by nome").setMaxResults(4).getResultList();
		List<UsuarioPessoa> lista = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa order by id")
				.setMaxResults(4).getResultList();

		for (UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
		}
	}

	// @Test
	public void testeQueryListParametro() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		// List<UsuarioPessoa> lista = daoGeneric.getEntityManager().createQuery("from
		// UsuarioPessoa where nome =
		// :nome").setParameter("nome","Biana").getResultList();

		List<UsuarioPessoa> lista = daoGeneric.getEntityManager()
				.createQuery("from UsuarioPessoa where nome = :nome and sobrenome = :sobrenome")
				.setParameter("nome", "Daniel").setParameter("sobrenome", "Penelva").getResultList();

		for (UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
		}
	}

	// @Test
	public void testQuerySomaIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		Long somaIdade = (Long) daoGeneric.getEntityManager()
				.createQuery("select sum(u.idade) from UsuarioPessoa as u ").getSingleResult();

		Double mediaIdade = (Double) daoGeneric.getEntityManager()
				.createQuery("select avg(u.idade) from UsuarioPessoa as u").getSingleResult();

		System.out.println("Soma de todas as idades = " + somaIdade);
		System.out.println("Media da idade dos usuários: " + mediaIdade);
	}

	// @Test
	public void testNameQuery() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> lista = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.todos")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
		}
	}

	// @Test
	public void testNameQueryPorNome() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> lista = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.buscarPorNomes")
				.setParameter("nome", "Daniel").getResultList();

		for (UsuarioPessoa usuarioPessoa : lista) {
			System.out.println(usuarioPessoa);
		}
	}

	//@Test
	public void testGravarTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();

		UsuarioPessoa usuarioPessoa = (UsuarioPessoa) daoGeneric.pesquisar(1L, UsuarioPessoa.class);

		TelefoneUsuarioPesssoa telefone = new TelefoneUsuarioPesssoa();
		telefone.setTipo("casa");
		telefone.setNumero("(21) 45699999");
		telefone.setUsuarioPessoa(usuarioPessoa);

		daoGeneric.salvar(telefone);
	}

	@Test
	public void testConsultarTelefones() {
		DaoGeneric daoGeneric = new DaoGeneric();

		UsuarioPessoa usuarioPessoa = (UsuarioPessoa) daoGeneric.pesquisar(1L, UsuarioPessoa.class);
		
		for (TelefoneUsuarioPesssoa telefonesUser : usuarioPessoa.getTelefones()) {
			System.out.println("Tipo de Telefone: " + telefonesUser.getTipo());
			System.out.println("Número do telefone: " + telefonesUser.getNumero());
			System.out.println("Nome do usuário: " + telefonesUser.getUsuarioPessoa().getNome());
			System.out.println(" --------------------------------------------- ");
		}
	}
}
