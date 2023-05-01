package br.com.projetoJpaJsf.managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.projetoJpaJsf.dao.DaoUsuario;
import br.com.projetoJpaJsf.model.UsuarioPessoa;

@ManagedBean(name = "telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {
	
	UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	DaoUsuario<UsuarioPessoa> daoUsuario = new DaoUsuario<UsuarioPessoa>();
	
	@PostConstruct
	public void init() {
		
		/* Abrir um contexto para capturar o codigo que define o parametro para poder carregar a página de telefones. Esse parametro
		 * define o managed bean */
		String coduser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigouser");
		usuarioPessoa = daoUsuario.pesquisar(Long.parseLong(coduser), UsuarioPessoa.class);
	}
	
	// Métodos Getters e Setters para que o JSF possa controlar os objetos que vem da tela
	
	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}
	
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

}
