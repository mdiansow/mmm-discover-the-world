package fr.istic.m2gla.mmm.services;

import fr.istic.m2gla.mmm.model.User;

import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by mds on 11/03/15.
 * Class ${CLASS}
 */
public interface IUserServices {
    Collection<User> findAll();

    User createUser(User user);

    Response removeUser(User user);

    User updateUser(User user);

    User findByID(long userID);
}
