package br.com.projetoJpaJsf.dao;

import br.com.projetoJpaJsf.model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGeneric<UsuarioPessoa>{
	
	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		
		//Abrir a transação
		getEntityManager().getTransaction().begin();
		
		// O próprio JPA vai remover a pessoa
	    getEntityManager().remove(pessoa);
		
		getEntityManager().getTransaction().commit();
		
		super.deletarPorId(pessoa);
	}

}

/* Dao para excluir em cascata os valores da relação de entidade entre UsuarioPessoa e TelefoneUsuarioPessoa. */