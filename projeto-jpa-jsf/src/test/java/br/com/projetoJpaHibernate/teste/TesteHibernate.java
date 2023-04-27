package br.com.projetoJpaHibernate.teste;

import org.junit.Test;

import br.com.projetoJpaHibernate.util.HibernateUtil;

public class TesteHibernate {
	
	@Test
	public void testeHibernateUtil() {
		HibernateUtil.geEntityManager();
	}
}
