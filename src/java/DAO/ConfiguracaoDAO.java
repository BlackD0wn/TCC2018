/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import bean.Configuracao;
import javax.persistence.EntityManager;

/**
 *
 * @author hook
 */
public class ConfiguracaoDAO extends AbstractDAO<Configuracao> {

    private EntityManager em;

    public ConfiguracaoDAO() {
        super(Configuracao.class);
        em = Connection.ConnectionFactory.getConnection();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
