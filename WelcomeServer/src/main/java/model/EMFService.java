package model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author appsrox.com
 */
public class EMFService {
    private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("transactions-optional");

    private EMFService() {
    }

    public static EntityManagerFactory get() {
        return emfInstance;
    }
}
