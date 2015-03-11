package fr.istic.m2gla.mmm.dao;

import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by mds on 11/03/15.
 * Class ${CLASS}
 */
public class DaoImpl<T> implements IDao<T> {

    private final EntityManager em;
    private final Class<T> tClass;

    public DaoImpl(Class<T> tClass) {
        em = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        this.tClass = tClass;
    }

    @Override
    public List findAll() {
        String req = "select e from " + tClass.getName() + " as e";
        return em.createQuery(req).getResultList();
    }

    @Override
    public T findById(long id) {
        if (id < 0) {
            throw new PersistenceException("Id may not be null or negative");
        }
        return em.find(tClass, id);
    }

    @Override
    public T create(T e) {
        if (e == null) {
            throw new PersistenceException("Entity to persist may not be null");//throw Persistence exception
        }
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(e);
            tr.commit();
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        } catch (ConstraintViolationException ex) {

        }

        return e;
    }

    @Override
    public String remove(T e) {
        if (e == null) {
            throw new PersistenceException("Entity to persist may not be null");//throw Persistence exception
        }
        String message = "FAIL";
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.remove(em.contains(e) ? e : em.merge(e));
            tr.commit();
            message = "SUCCESS";
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        } catch (ConstraintViolationException ex) {

        }
        return message;
    }

    @Override
    public T update(T e) {
        if (e == null) {
            throw new PersistenceException("Entity to persist may not be null");//throw Persistence exception
        }
        return e;
    }
}
