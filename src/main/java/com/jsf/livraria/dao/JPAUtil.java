package com.jsf.livraria.dao;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("livraria");

	@Produces
	@RequestScoped /* Will call this method on each request  */
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/* Will call this method every end of the request */
	public void close(@Disposes EntityManager em) {
		em.close();
	}
}
