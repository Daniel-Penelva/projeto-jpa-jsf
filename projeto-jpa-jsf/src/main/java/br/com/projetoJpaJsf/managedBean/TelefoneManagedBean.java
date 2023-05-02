package br.com.projetoJpaJsf.managedBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.projetoJpaJsf.dao.DaoTelefone;
import br.com.projetoJpaJsf.dao.DaoUsuario;
import br.com.projetoJpaJsf.model.TelefoneUsuarioPesssoa;
import br.com.projetoJpaJsf.model.UsuarioPessoa;

@ManagedBean(name = "telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {
	
	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private TelefoneUsuarioPesssoa telefone = new TelefoneUsuarioPesssoa();
	private DaoUsuario<UsuarioPessoa> daoUsuario = new DaoUsuario<UsuarioPessoa>();
	private DaoTelefone<TelefoneUsuarioPesssoa> daoTelefone = new DaoTelefone<TelefoneUsuarioPesssoa>();
	
	@PostConstruct
	public void init() {
		
		/* Abrir um contexto para capturar o codigo que define o parametro para poder carregar a página de telefones. Esse parametro
		 * define o managed bean */
		String coduser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigouser");
		usuarioPessoa = daoUsuario.pesquisar(Long.parseLong(coduser), UsuarioPessoa.class);
	}
	
	
	/* Action que serão chamadas na tela */
	public String salvar() {
		
		/* Tem que setar o usuário que está sendo carregado na página telefone.xhtml, no caso, vamos setar o 'codigouser' que 
		 * corresponde a chave estrangeira do usuario. */
		telefone.setUsuarioPessoa(usuarioPessoa);
		
		daoTelefone.salvar(telefone);
		telefone = new TelefoneUsuarioPesssoa();
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação:", "Cadastrado com Sucesso!"));
		
		return "";
		
		/* Lembrando, vamos colocar para retornar um tipo String para que retorne na mesma página. */
	}

	
	
	// Métodos Getters e Setters para que o JSF possa controlar os objetos que vem da tela
	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}
	
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}
	
	public void setDaoTelefone(DaoTelefone<TelefoneUsuarioPesssoa> daoTelefone) {
		this.daoTelefone = daoTelefone;
	}
	
	public DaoTelefone<TelefoneUsuarioPesssoa> getDaoTelefone() {
		return daoTelefone;
	}

	public TelefoneUsuarioPesssoa getTelefone() {
		return telefone;
	}

	public void setTelefone(TelefoneUsuarioPesssoa telefone) {
		this.telefone = telefone;
	}
	
	
	
	

}
