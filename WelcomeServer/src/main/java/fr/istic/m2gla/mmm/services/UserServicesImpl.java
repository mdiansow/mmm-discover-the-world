package fr.istic.m2gla.mmm.services;

import fr.istic.m2gla.mmm.dao.DaoImpl;
import fr.istic.m2gla.mmm.dao.IDao;
import fr.istic.m2gla.mmm.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by mds on 11/03/15.
 * Class ${CLASS}
 */
@Path("/user")
public class UserServicesImpl implements IUserServices {
    private IDao<User> userDAO;

    public UserServicesImpl() {
        this.userDAO = new DaoImpl(User.class);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public Collection<User> findAll() {
        return this.userDAO.findAll();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public User createUser(User user) {
        return userDAO.create(user);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/rm")
    @Override
    public Response removeUser(User user) {
        int status = 424;
        String message = userDAO.remove(user);
        if (message.equals("SUCCESS")) {
            status = 200;
        }
        return Response.ok().status(status).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/update")
    @Override
    public User updateUser(User user) {
        return userDAO.update(user);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{userID}")
    @Override
    public User findByID(@PathParam("userID")long userID) {
        return userDAO.findById(userID);
    }

}
