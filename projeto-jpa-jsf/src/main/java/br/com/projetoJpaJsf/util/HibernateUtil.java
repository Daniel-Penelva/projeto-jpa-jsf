package br.com.projetoJpaJsf.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

	/* Acessa o arquivo persistence.xml 
	 * É através do EntityManagerFactory (fabrica de entityManager) que é possível criar 
	 * o gerenciador de entidade EntityManager. */
	public static EntityManagerFactory factory = null;
	
	static {
		init();
	}

	private static void init() {
		try {
			if (factory == null) {
				/* Ele recebe como parâmetro o nome do projeto que está no arquivo persistence.xml */
				factory = Persistence.createEntityManagerFactory("projeto-jpa-jsf");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static EntityManager geEntityManager() {
		
		/* Gerenciador de entidade, é ele que vai prover a parte de persistência. */
		return factory.createEntityManager();
	}
	
	/* Esse método é para ser usado em consultas de chave primária, 
	 * ou seja, para consultar usuário. Ele retorna a primary key. */
	public static Object getPrimaryKey(Object entidade) {
		return factory.getPersistenceUnitUtil().getIdentifier(entidade);
	}
}
