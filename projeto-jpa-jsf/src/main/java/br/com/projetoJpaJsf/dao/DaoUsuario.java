package br.com.projetoJpaJsf.dao;

import br.com.projetoJpaJsf.model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGeneric<UsuarioPessoa>{
	
	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		
		//Abrir a transação
		getEntityManager().getTransaction().begin();
		
		String sqlDeleteFone = "delete from telefoneusuariopesssoa where usuariopessoa_id = " + pessoa.getId();
		
		// getEntityManger inicia a transação | executeUpdate() ele atualiza ou deleta
		getEntityManager().createNativeQuery(sqlDeleteFone).executeUpdate();
		getEntityManager().getTransaction().commit();
		
		super.deletarPorId(pessoa);
	}

}
