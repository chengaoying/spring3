package cn.javass.spring.chapter12;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;

public class TestBean61 {
    
    @PersistenceContext(unitName = "entityManagerFactory", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
    
    @PersistenceUnit(unitName = "entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;
    
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
