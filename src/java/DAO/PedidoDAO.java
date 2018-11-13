/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import bean.Movimentacao;
import bean.Pedido;
import bean.Produto;
import com.google.gson.Gson;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**asa
 *
 * @author hook
 */
public class PedidoDAO extends AbstractDAO<Pedido>{
    
    private EntityManager em;
 
    public PedidoDAO() {
        super(Pedido.class);
        em = Connection.ConnectionFactory.getConnection();
    }
    
      public List<Pedido> findByProduct(Produto p){
        List<Pedido> list = null;
        System.out.println(p.getId());
        try{
            list = getEntityManager().createQuery("from Pedido where p_id = :busca").setParameter("busca", p.getId()).getResultList();         
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }
      
    public boolean pedidosEmAberto(Produto p){
        List<Pedido> list = null;
        System.out.println(p.getId());
        try{
            list = getEntityManager().createQuery("from Pedido where p_id = :busca and enviado = false and recebido = false").setParameter("busca", p.getId()).getResultList();         
            if (list != null) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        
        return false;
    }  
      
    public List<Pedido> findEnviados(){
        List<Pedido> list = null;
        try{
            list = getEntityManager().createQuery("from Pedido where enviado = true and recebido = false").getResultList();         
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Pedido> findNaoEnviados(){
        List<Pedido> list = null;
        try{
            list = getEntityManager().createQuery("from Pedido where enviado = false").getResultList();         
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }   
    
      public List<Pedido> findNaoEnviadosPorProduto(Produto p){
        List<Pedido> list = null;
        try{
            list = getEntityManager().createQuery("from Pedido where p_id = :busca and enviado = false and recebido = false").setParameter("busca", p.getId()).getResultList();
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Pedido> findRecebido(){
        List<Pedido> list = null;
        try{
            list = getEntityManager().createQuery("from Pedido where recebido = true").getResultList();         
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    } 
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
}
