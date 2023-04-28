package br.com.projetoJpaJsf.teste;

import org.junit.Test;

import br.com.projetoJpaJsf.util.HibernateUtil;

public class TesteHibernate {
	
	@Test
	public void testeHibernateUtil() {
		HibernateUtil.geEntityManager();
	}
}
