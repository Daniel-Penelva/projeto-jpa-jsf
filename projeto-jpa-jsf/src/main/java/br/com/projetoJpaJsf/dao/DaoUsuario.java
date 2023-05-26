package br.com.projetoJpaJsf.dao;

import br.com.projetoJpaJsf.model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGeneric<UsuarioPessoa>{
	
	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		
		//Abrir a transação
		getEntityManager().getTransaction().begin();
		
		String sqlDeleteFone = "delete from telefoneusuariopesssoa where usuariopessoa_id = " + pessoa.getId();
		
		// getEntityManger inicia a transação | executeUpdate() ele atualiza ou deleta
		getEntityManager().createNativeQuery(sqlDeleteFone).executeUpdate();
		
		String sqlDeleteEmail = "delete from emailusuariopesssoa where usuariopessoa_id = " + pessoa.getId();
		getEntityManager().createNativeQuery(sqlDeleteEmail).executeUpdate();
		
		getEntityManager().getTransaction().commit();
		
		super.deletarPorId(pessoa);
	}

}

/* Dao para excluir em cascata os valores da relação de entidade entre UsuarioPessoa e TelefoneUsuarioPessoa. */