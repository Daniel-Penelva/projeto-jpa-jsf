package br.com.projetoJpaJsf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

@Entity
public class TelefoneUsuarioPesssoa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/* Anotação para o campo ser obrigatório */
	@Column(nullable = false)
	private String tipo;

	@Column(nullable = false)
	private String numero;

	/* Anotação para relacionamento de muitos para um, ou seja, 
	 * um ou vários telefones só pode ter um usuário */
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private UsuarioPessoa usuarioPessoa;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

}
