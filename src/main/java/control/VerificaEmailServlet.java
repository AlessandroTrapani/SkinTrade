package control;

import java.io.IOException;

import dao.UtenteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet utilizzata tramite AJAX per verificare la disponibilità di un'email.
 * Riceve l'email dalla richiesta, controlla la sua presenza nel database
 * e restituisce una risposta JSON alla pagina di registrazione.
 */
@WebServlet("/verifica-email")
public class VerificaEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public VerificaEmailServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		if (email == null || email.trim().equals("")) {
			response.getWriter().write("{\"valida\":false,\"messaggio\":\"Email non valida.\"}");
			return;
		}

		UtenteDAO utenteDAO = new UtenteDAO();
		boolean emailEsistente = utenteDAO.emailEsistente(email.trim());

		if (emailEsistente) {
			response.getWriter().write("{\"valida\":false,\"messaggio\":\"Email già registrata.\"}");
		} else {
			response.getWriter().write("{\"valida\":true,\"messaggio\":\"Email disponibile.\"}");
		}
	}
}