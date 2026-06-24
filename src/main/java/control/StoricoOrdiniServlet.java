package control;

import java.io.IOException;
import java.util.ArrayList;

import dao.OrdineDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Ordine;
import model.Utente;

/**
 * Servlet che gestisce lo storico ordini dell'utente autenticato.
 * Recupera dal database tutti gli ordini associati all'utente in sessione
 * e li inoltra alla JSP dello storico ordini.
 */
@WebServlet("/storico-ordini")
public class StoricoOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StoricoOrdiniServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sessione = request.getSession();

		Utente utente = (Utente) sessione.getAttribute("utenteLoggato");

		if (utente == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		OrdineDAO ordineDAO = new OrdineDAO();
		ArrayList<Ordine> ordini = ordineDAO.trovaOrdiniPerUtente(utente.getId());

		request.setAttribute("ordini", ordini);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/storico-ordini.jsp");
		dispatcher.forward(request, response);
	}
}