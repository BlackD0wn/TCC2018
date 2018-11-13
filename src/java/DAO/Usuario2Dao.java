/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import bean.Usuario;
import java.util.ArrayList;
import javax.persistence.EntityManager;

/**
 *
 * @author hook
 */
public class Usuario2Dao {
    
    
    public boolean create(Usuario entity) {
      try{
        getEntityManager().getTransaction().begin();
        getEntityManager().merge(entity); 
        getEntityManager().getTransaction().commit();
        
      }catch(Exception e){
          e.printStackTrace();
        getEntityManager().getTransaction().rollback();
         return false;
      }
      return true;
    }
    
     public ArrayList<Usuario> findAll() {
      
        ArrayList<Usuario>  list = new ArrayList<Usuario>();
        list = (ArrayList<Usuario>) getEntityManager().createQuery("From Usuario").getResultList();
      
      return list;
    }

    private EntityManager getEntityManager() {
        return Connection.ConnectionFactory.getConnection();
    
    }
    
}
