package fr.istic.m2gla.mmm.server;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author appsrox.com
 */
@SuppressWarnings("serial")
public class SendServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SendServlet.class.getCanonicalName());
    private static DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();


    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Sends a message to the GCM fr.istic.m2gla.mmm.server.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = req.getParameter(Constants.MSG);
        String from = req.getParameter(Constants.FROM);
        String to = req.getParameter(Constants.TO);

        Key contactKey = KeyFactory.createKey("Contact", to);

        Query query = new Query("Contact", contactKey).addSort("date", Query.SortDirection.DESCENDING);
        Entity contact = dataStore.prepare(query).asSingleEntity();

        if (contact == null) {
            logger.log(Level.INFO, "Contact not exist.");
            return;
        }
        logger.log(Level.INFO, "Contact email \t" + contact.getProperty("regId"));


        String regId = String.valueOf(contact.getProperty("regId"));

        Message message = new Message.Builder()
                .collapseKey("message")
                .timeToLive(3)
                .delayWhileIdle(true)
                .addData("message", msg) //you can get this message on client side app
                .build();

        Sender sender = new Sender(Constants.API_KEY);

        logger.log(Level.FINE, "Message\t" + message);

        try {
            Result result = sender.send(message, regId, 5);

            Date date = new Date();
            Key messageKey = KeyFactory.createKey("Message", from);
            Entity messageEntity = new Entity("Message", messageKey);
            messageEntity.setProperty("message", "" + message.toString());
            messageEntity.setProperty("date", date);
            messageEntity.setProperty("from", from);
            messageEntity.setProperty("to", to);
            messageEntity.setProperty("regID", regId);
            dataStore.put(messageEntity);

            logger.log(Level.WARNING, "Result: " + result.toString());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

}
