/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import bean.Fornecedor;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hook
 */
public class FornecedorDAO extends AbstractDAO<Fornecedor> {

    private EntityManager em;

    public FornecedorDAO() {
        super(Fornecedor.class);
        em = Connection.ConnectionFactory.getConnection();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Fornecedor> procurarTodos() {
        List<Fornecedor> list = null;
        list = getEntityManager().createQuery("From Fornecedor order by nome").getResultList();
        return list;
    }
}
