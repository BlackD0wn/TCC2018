/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author hook
 */
public abstract class ConnectionFactory {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("tcc2");
    private static EntityManager em = emf.createEntityManager();

    public static EntityManager getConnection() {
        return em;
    }
}
