/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import bean.Permissoes;
import javax.persistence.EntityManager;

/**
 *
 * @author hook
 */
public class PermissoesDAO extends AbstractDAO<Permissoes> {

    public PermissoesDAO() {
        super(Permissoes.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return Connection.ConnectionFactory.getConnection();

    }

}
