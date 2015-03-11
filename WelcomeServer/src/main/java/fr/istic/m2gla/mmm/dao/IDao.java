package fr.istic.m2gla.mmm.dao;

import java.util.List;

/**
 * Created by mds on 11/03/15.
 * Class ${CLASS}
 */
public interface IDao<T> {

    /**
     * Find all objects
     *
     * @return
     */
    List<T> findAll();

    /**
     * Find object by ID
     *
     * @param id long
     * @return T
     */
    T findById(long id);

    /**
     * Create new T object
     *
     * @param e T
     * @return T
     */
    T create(T e);

    /**
     * Remove object
     *
     * @param e T
     */
    String remove(T e);

    /**
     * Update object
     *
     * @param e T
     * @return T
     */
    T update(T e);
}
