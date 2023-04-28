package br.com.projetoJpaJsf.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.projetoJpaJsf.dao.DaoGeneric;
import br.com.projetoJpaJsf.model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {

	/* Criando e instanciando objeto */
	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<>();
	private List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();

	
	/* Getters e Setters para poder injetar os dados da tela para dentro do objeto */
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}
	
	public List<UsuarioPessoa> getList() {
		list = daoGeneric.listarTodos(UsuarioPessoa.class);
		return list;
	}
	
	/* Action que serão chamadas na tela */
	public String salvar() {
		daoGeneric.salvar(usuarioPessoa);
		return "";
	}
	
	public String novo() {
		usuarioPessoa = new UsuarioPessoa();
		return "";
	}
	
	

}
