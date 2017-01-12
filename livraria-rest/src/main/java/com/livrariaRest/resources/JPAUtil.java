package com.livrariaRest.resources;

import javax.persistence.*;

public class JPAUtil {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence_livraria");

	public static EntityManager createManager() {
		return factory.createEntityManager();
	}
}