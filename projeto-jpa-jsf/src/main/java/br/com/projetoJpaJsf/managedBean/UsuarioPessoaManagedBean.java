package br.com.projetoJpaJsf.managedBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import br.com.projetoJpaJsf.dao.DaoEmail;
import br.com.projetoJpaJsf.dao.DaoUsuario;
import br.com.projetoJpaJsf.model.EmailUsuarioPesssoa;
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
	
	private BarChartModel barChartModel = new BarChartModel();
	
	private EmailUsuarioPesssoa emailUser = new EmailUsuarioPesssoa(); 
	private DaoEmail<EmailUsuarioPesssoa> daoEmail = new DaoEmail<EmailUsuarioPesssoa>();
	
	private String campoPesquisa;

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
		
		montarGrafico();
	}

	private void montarGrafico() {
		//Para cada linha de usuário tem que iniciar um objeto ChartSeries - Grupo de usuários
		ChartSeries userSalario = new ChartSeries();
		
		// Carregando o gráfico do salário de usuários - add salário para o grupo 
		for (UsuarioPessoa usuarioPessoa : list) {
			
			// Recebe como parametro um objeto nome e um objeto número. Vale ressaltar q esse método é um Map, ou seja, 
			//uma lista que recebe dois atributos, no caso, o nome e o salário - aqui faz um add salarios.
			userSalario.set(usuarioPessoa.getNome(), usuarioPessoa.getSalario());
		}
		
		// Adiciona o grupo no barChatModel
		barChartModel.addSeries(userSalario);
		
		// Título
		barChartModel.setTitle("Gráfico de Salários");
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
	
	public BarChartModel getBarChartModel() {
		return barChartModel;
	}
	
	public void setEmailUser(EmailUsuarioPesssoa emailUser) {
		this.emailUser = emailUser;
	}
	
	public EmailUsuarioPesssoa getEmailUser() {
		return emailUser;
	}
	
	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}
	
	public String getCampoPesquisa() {
		return campoPesquisa;
	}
	

	/* Action que serão chamadas na tela */
	public String salvar() {
		daoGeneric.salvar(usuarioPessoa);

		// adiciona o usuario na lista
		list.add(usuarioPessoa);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação:", "Cadastrado com Sucesso!"));
		return "";
		
	 /* Lembrando, vamos colocar para retornar um tipo String para que retorne na mesma página. */
	}

	public String novo() {
		usuarioPessoa = new UsuarioPessoa();
		return "";
	}

	public String remover() {

		try {
			
			//daoGeneric.removerUsuario(usuarioPessoa);
			daoGeneric.removerUsuario(usuarioPessoa);

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
	
	public void pesquisaCep(AjaxBehaviorEvent event) {
		
		try {
			//System.out.println("Cep digitado: " + usuarioPessoa.getCep());
			
			// Capturar a url do cep digitado pelo usuário
			URL url = new URL("https://viacep.com.br/ws/"+usuarioPessoa.getCep()+"/json/");
			
			// Abrir uma conexão dessa url
			URLConnection connection = url.openConnection();
			
			// O InputStream é onde os fluxos de dados irão retornar 
			InputStream is = connection.getInputStream();
			
			// BufferedReader é utilizado para lê o fluxo de dados
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			//Teremos que varrer esse BufferedReader para poder ser lido e colocado numa variável de texto
			String cep = "";
			
			// StringBuilder vai guardar todas as informações do CEP (cep, logradouro, complemento, ...)
			StringBuilder jsonCep = new StringBuilder();
			
			//Enquanto houver dados a ser lido na linha (readLine) cai nessa repetição
			while((cep = br.readLine()) != null) {
				
				jsonCep.append(cep);  // vai adicionando todos os valores referente ao cep
			}
			
			//System.out.println(jsonCep.toString());
			
			// Vamos inicializar um usuário para carregar as informações do cep para dentro do nosso objeto
			UsuarioPessoa userCepPessoa = new Gson().fromJson(jsonCep.toString(), UsuarioPessoa.class);
			
			//System.out.println(userCepPessoa);
			
			/* Atribuindo os valores controlado pelo managedbean (usuarioPessoa) que vai capturar os valores na 
			 * variavel (objeto) auxiliar.*/
			usuarioPessoa.setCep(userCepPessoa.getCep());
			usuarioPessoa.setLogradouro(userCepPessoa.getLogradouro());
			usuarioPessoa.setComplemento(userCepPessoa.getComplemento());
			usuarioPessoa.setBairro(userCepPessoa.getBairro());
			usuarioPessoa.setLocalidade(userCepPessoa.getLocalidade());
			usuarioPessoa.setUf(userCepPessoa.getUf());
			usuarioPessoa.setUnidade(userCepPessoa.getUnidade());
			usuarioPessoa.setIbge(userCepPessoa.getIbge());
			usuarioPessoa.setGia(userCepPessoa.getGia());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addEmail() {
		//System.out.println("Teste: " + emailUser);
		
		// Tem que amarrar o email a pessoa, logo vamos setar o email ao usuario antes de gravar no banco.
		emailUser.setUsuarioPessoa(usuarioPessoa);
		
		// Gravar no banco de dados o email e já deixa na memória 
	    emailUser = daoEmail.atualizar(emailUser);
	    
	    // Adiciona na lista de email
	    usuarioPessoa.getEmails().add(emailUser);
	    
	    // E para deixar o dialog já pronto para cadastrar um novo email, basta fazer um new.
	    emailUser = new EmailUsuarioPesssoa();
	    
	    // Criando a mensagem de cadastrado e-mail com sucesso
	    FacesContext.getCurrentInstance().addMessage(null, 
	    		new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Email cadastrado com Sucesso!"));
	}
	
	// Para remover o email temos que passar o id pelo lado do servidor por meio do parametro (param)
	public void removerEmail() throws Exception {
		//Vamos recuperar o parametro (param) atraves do FacesContext que é por onde consegue todas as informações do JSF
	    String codigoemail = FacesContext.getCurrentInstance().getExternalContext()
	    		.getRequestParameterMap().get("codigoemail");
	    
	    // Atribuir o id email na variavel removerEmail
	    EmailUsuarioPesssoa removerEmail = new EmailUsuarioPesssoa();
	    removerEmail.setId(Long.parseLong(codigoemail));
	    
	    // Deletar por id usando o dao
	    daoEmail.deletarPorId(removerEmail);
	    
	    //Captura o id e remove da lista de emails da memória
	    usuarioPessoa.getEmails().remove(removerEmail);
	    
	    // Criando a mensagem de removido e-mail com sucesso
	    FacesContext.getCurrentInstance().addMessage(null, 
	    		new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Email removido com Sucesso!"));
	}
	
	public void pesquisar(){
		list = daoGeneric.pesquisar(campoPesquisa);
		montarGrafico();
	}
	
	public void upload(FileUploadEvent image){
		
		// Converter para uma base64 - converter binário para String
		String imagem = "data:image/png;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContent());
		
		// Setar para o objeto pessoa o atributo image
		usuarioPessoa.setImagem(imagem);
	}
	
	public void download() throws IOException{
		
		// Criando um Map para capturar o parametro da tela de download
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		// Capturando o parametro do componente (no caso é o id da pessoa) de download da tela
		String fileDownloadId = params.get("fileDownloadId");
			
		// Consulta no banco o id do usuario pessoa
		UsuarioPessoa pessoa = daoGeneric.pesquisar(Long.parseLong(fileDownloadId), UsuarioPessoa.class);
			
		// Preparar a imagem para o download
		byte[] imagem = new Base64().decodeBase64(pessoa.getImagem().split("\\,")[1]);
			
		// Dar a resposta para o JSF
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
		// Adiciona a resposta pelo método header cujo o parametro é  por padrão sempre 'Content-Disposition' e 'attachment; filename=download.png'.
		// Esse parametros diz respeito que o download vai ser feito de forma direta, ou seja, não vai para outra tela.
		response.addHeader("Content-Disposition","attachment; filename=download.png");
			
		// Responde o conteúdo  - o valor do parametro diz respeito ao formato do arquivo
		response.setContentType("application/octet-stream");
			
		// Responde o tamanho do conteúdo (no caso o tamanho do arquivo)
		response.setContentLength(imagem.length);
			
		// Escreve o objeto imagem 
		response.getOutputStream().write(imagem);
			
		// Finaliza a resposta fazendo um flush
		response.getOutputStream().flush();
			
		// Aqui avisa ao JSF para capturar todo o contexto da resposta que já está completa.
		FacesContext.getCurrentInstance().responseComplete();
	}

}
