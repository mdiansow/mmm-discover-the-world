package fr.istic.m2gla.mmm.server;

import com.google.appengine.api.datastore.*;
import fr.istic.m2gla.mmm.model.Contact;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by mds on 19/03/15.
 * Class ${CLASS}
 */
@Path("contact")
public class Services {


    private static final Logger logger = Logger.getLogger(Services.class.getCanonicalName());
    private static DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

    @Path("email={email}&lat={lat}&long={long}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Contact> getUserWithCoordinate(@PathParam("email") String mail, @PathParam("lat") double lat, @PathParam("long") double longi) {
        List<Contact> contacts = new ArrayList<>();

        Query query = new Query("Contact").addSort("date", Query.SortDirection.DESCENDING);
        List<Entity> contactEntities = dataStore.prepare(query).asList(FetchOptions.Builder.withLimit(10));
        for (Entity e : contactEntities) {
            String email = String.valueOf(e.getProperty("email"));
            String regId = String.valueOf(e.getProperty("regId"));
            double latitude = (double) e.getProperty("latitude");
            double longitude = (double) e.getProperty("longitude");
            Contact c = new Contact(email, regId, latitude, longitude);
            contacts.add(c);
        }
        Key contactKey = KeyFactory.createKey("Contact", mail);

        query = new Query("Contact", contactKey).addSort("date", Query.SortDirection.DESCENDING);
        Entity contact = dataStore.prepare(query).asSingleEntity();

        if (contact != null) {
            contact.setProperty("latitude", lat);
            contact.setProperty("longitude", longi);
            dataStore.put(contact);
        }

        return contacts;
    }
}
