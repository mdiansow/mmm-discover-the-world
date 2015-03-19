package server;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author appsrox.com
 */
@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getCanonicalName());

    private static DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();


    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Registers a device with the Demo server.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(Constants.FROM);
        String regId = req.getParameter(Constants.REG_ID);

        Date date = new Date();

        Key contactKey = KeyFactory.createKey("Contact", email);

        Query query = new Query("Contact", contactKey).addSort("date", Query.SortDirection.DESCENDING);
        Entity contact = dataStore.prepare(query).asSingleEntity();

        if (contact == null) {
            contact = new Entity("Contact", email);
            contact.setProperty("email", email);
            contact.setProperty("regId", regId);
            contact.setProperty("date", date);
        } else {
            contact.setProperty("regId", regId);
        }
        dataStore.put(contact);
        logger.log(Level.FINE, "Registered: " + contact.getProperty("regId"));
    }

}
