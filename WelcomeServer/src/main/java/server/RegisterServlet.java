package server;
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
 *
 */
@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(RegisterServlet.class.getCanonicalName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Registers a device with the Demo server.");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter(Constants.FROM);
		String regId = req.getParameter(Constants.REG_ID);
		
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Contact contact = Contact.find(email, em);
			if (contact == null) {
				contact = new Contact(email, regId);
			} else {
				contact.setRegId(regId);
			}
			em.persist(contact);
			logger.log(Level.WARNING, "Registered: " + contact.getId());
		} finally {
			em.close();
		}
	}	
	
}
