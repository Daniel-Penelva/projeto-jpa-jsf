package br.com.projetoJpaJsf.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

@Entity
public class EmailUsuarioPesssoa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/* Anotação para o campo ser obrigatório */
	@Column(nullable = false)
	private String email;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailUsuarioPesssoa other = (EmailUsuarioPesssoa) obj;
		return Objects.equals(id, other.id);
	}

}
