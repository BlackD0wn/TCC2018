/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.google.gson.Gson;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hook
 */
public abstract class AbstractDAO<T> {

    private Class<T> entityClass;
    private String nomeClasse;

    public AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        nomeClasse = this.entityClass.getSimpleName();
    }

    protected abstract EntityManager getEntityManager();

    public boolean create(T entity) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("rollback");
            getEntityManager().getTransaction().rollback();
            return false;
        }
        return true;
    }

    public boolean edit(T entity) {

        System.out.println("edit: " + new Gson().toJson(entity));
        getEntityManager().getTransaction().begin();
        getEntityManager().merge(entity);
        getEntityManager().getTransaction().commit();
        return true;
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> procurarTodos() {
        List<T> list = null;
        list = getEntityManager().createQuery("From " + nomeClasse).getResultList();
        return list;
    }

    public List<T> findRange(int from, int to) {
        List<T> list = null;
        list = procurarTodos();
        list = list.subList(from, to);
        return list;
    }

    public T findByName(String busca) {
        T entity;

        try {
            entity = (T) getEntityManager().createQuery("from " + nomeClasse + " f where f.nome = :busca").setParameter("busca", busca).getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
        return entity;
    }

    public List<T> findByPartName(String busca) {
        List<T> list = null;
        list = getEntityManager().createQuery("from " + nomeClasse + " f where f.nome like :busca").setParameter("busca", "%" + busca + "%").getResultList();
        System.out.println(new Gson().toJson(list) + "\nTamanho:" + list.size());
        return list;
    }

    public int count() {
        return procurarTodos().size();
    }

}
