/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
    extender abstractDAO

 */
package DAO;

import bean.Usuario;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hook
 */
public class UsuarioDAO extends AbstractDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return Connection.ConnectionFactory.getConnection();
    }

    @Override
    public List<Usuario> procurarTodos() {
        List<Usuario> list = null;
        list = getEntityManager().createQuery("From Usuario order by nome").getResultList();
        return list;
    }

}
