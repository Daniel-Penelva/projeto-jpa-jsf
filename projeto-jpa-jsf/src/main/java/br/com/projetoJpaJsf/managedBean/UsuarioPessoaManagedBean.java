package br.com.projetoJpaJsf.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.exception.ConstraintViolationException;

import br.com.projetoJpaJsf.dao.DaoUsuario;
import br.com.projetoJpaJsf.model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {

	/*
	 * Criando e instanciando objeto para poder injetar os dados da tela para dentro
	 * do objeto
	 */

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();
	private DaoUsuario<UsuarioPessoa> daoGeneric = new DaoUsuario<UsuarioPessoa>();

	/*
	 * Depois que esse managed bean é construido na memória será executado apenas
	 * uma vez esse método, no caso a lista de usuário vai ser executado apenas uma
	 * vez. Ou seja, vai ser carregado apenas uma vez no banco de dados. Portanto,
	 * vamos utilizar essa lista para adicionar o usuário quando for salvo e para
	 * remover o usuário quando for excluido.
	 */
	@PostConstruct
	public void init() {
		list = daoGeneric.listarTodos(UsuarioPessoa.class);
	}

	/*
	 * Getters e Setters para poder injetar os dados da tela para dentro do objeto
	 */
	
	public List<UsuarioPessoa> getList() {
		return list;
	}

	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

	/* Action que serão chamadas na tela */
	public String salvar() {
		daoGeneric.salvar(usuarioPessoa);

		// adiciona o usuario na lista
		list.add(usuarioPessoa);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação:", "Cadastrado com Sucesso!"));
		return "";
	}

	public String novo() {
		usuarioPessoa = new UsuarioPessoa();
		return "";
	}

	public String remover() {

		try {
			daoGeneric.removerUsuario(usuarioPessoa);
			;

			// exclui o usuário da lista
			list.remove(usuarioPessoa);

			usuarioPessoa = new UsuarioPessoa();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Removido com sucesso!"));

		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Existem telefone para usuário!"));
			} else {
				e.printStackTrace();
			}
		}

		return "";
	}

}
