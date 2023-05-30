package br.com.projetoJpaJsf.datatablelazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import br.com.projetoJpaJsf.dao.DaoUsuario;
import br.com.projetoJpaJsf.model.UsuarioPessoa;

public class LazyDataTableModelUserPessoa<T> extends LazyDataModel<UsuarioPessoa>{

	private DaoUsuario<UsuarioPessoa> dao = new DaoUsuario<UsuarioPessoa>();
	public List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	
	private String sql = " FROM UsuarioPessoa ";
	
	
	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<UsuarioPessoa> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		
		// Vai fazer paginação de cinco em cinco
		list = dao.getEntityManager().createQuery(getSql()).setFirstResult(first).setMaxResults(pageSize).getResultList();
		
		sql = " FROM UsuarioPessoa ";
		
		setPageSize(pageSize);
		
		Integer qtdRegistro = Integer.parseInt(dao.getEntityManager().createQuery("SELECT COUNT(1) " + getSql()).getSingleResult().toString());
		setRowCount(qtdRegistro);
		
		return list;
	}
	
	public String getSql() {
		return sql;
	}
	
	public List<UsuarioPessoa> getList() {
		return list;
	}
	
	public void pesquisa(String campoPesquisa) {
		sql +=" WHERE nome LIKE '%"+campoPesquisa+"%'";
	}

}
