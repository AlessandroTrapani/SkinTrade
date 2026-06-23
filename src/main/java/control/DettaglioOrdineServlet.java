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
import model.DettaglioOrdine;
import model.Ordine;
import model.Utente;

@WebServlet("/dettaglio-ordine")
public class DettaglioOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DettaglioOrdineServlet() {
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

		String idParametro = request.getParameter("id");

		if (idParametro == null || idParametro.trim().equals("")) {
			response.sendRedirect(request.getContextPath() + "/storico-ordini");
			return;
		}

		int idOrdine;

		try {
			idOrdine = Integer.parseInt(idParametro);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/storico-ordini");
			return;
		}

		OrdineDAO ordineDAO = new OrdineDAO();

		Ordine ordine = ordineDAO.trovaOrdinePerIdEUtente(idOrdine, utente.getId());

		if (ordine == null) {
			response.sendRedirect(request.getContextPath() + "/storico-ordini");
			return;
		}

		ArrayList<DettaglioOrdine> dettagli = ordineDAO.trovaDettagliPerOrdine(idOrdine);

		request.setAttribute("ordine", ordine);
		request.setAttribute("dettagli", dettagli);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/dettaglio-ordine.jsp");
		dispatcher.forward(request, response);
	}
}