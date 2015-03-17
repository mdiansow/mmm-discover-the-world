package server;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import model.Contact;
import model.EMFService;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author appsrox.com
 */
@SuppressWarnings("serial")
public class SendServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SendServlet.class.getCanonicalName());

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Sends a message to the GCM server.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = req.getParameter(Constants.MSG);
        String from = req.getParameter(Constants.FROM);
        String to = req.getParameter(Constants.TO);

        Contact contact = null;
        EntityManager em = EMFService.get().createEntityManager();
        try {
            contact = Contact.find(to, em);
            if (contact == null) return;
        } finally {
            em.close();
        }

        String regId = contact.getRegId();
        Sender sender = new Sender(Constants.API_KEY);
        Message message = new Message.Builder()
//			.delayWhileIdle(true)
                .addData(Constants.TO, to).addData(Constants.FROM, from).addData(Constants.MSG, msg)
                .build();

        try {
            Result result = sender.send(message, regId, 5);
/*			List<String> regIds = new ArrayList<String>();
            regIds.add(regId);
			MulticastResult result = sender.send(message, regIds, 5);*/

            logger.log(Level.WARNING, "Result: " + result.toString());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

}
