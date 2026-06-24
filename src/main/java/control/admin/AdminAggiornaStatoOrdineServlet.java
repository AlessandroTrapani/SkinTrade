package control.admin;

import java.io.IOException;

import dao.OrdineDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utente;

/**
 * Servlet dell'area amministratore che permette di aggiornare lo stato di un ordine.
 * Riceve il nuovo stato dal form, valida il valore ricevuto e aggiorna
 * il record corrispondente nel database.
 */
@WebServlet("/admin/aggiorna-stato-ordine")
public class AdminAggiornaStatoOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminAggiornaStatoOrdineServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sessione = request.getSession();

		Utente utente = (Utente) sessione.getAttribute("utenteLoggato");

		if (utente == null || !utente.isAdmin()) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String idParametro = request.getParameter("idOrdine");
		String stato = request.getParameter("stato");

		if (idParametro == null || idParametro.trim().equals("") || stato == null || stato.trim().equals("")) {
			response.sendRedirect(request.getContextPath() + "/admin/ordini");
			return;
		}

		int idOrdine;

		try {
			idOrdine = Integer.parseInt(idParametro);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/ordini");
			return;
		}

		if (!stato.equals("IN_ELABORAZIONE") && !stato.equals("COMPLETATO") && !stato.equals("ANNULLATO")) {
			response.sendRedirect(request.getContextPath() + "/admin/dettaglio-ordine?id=" + idOrdine);
			return;
		}

		OrdineDAO ordineDAO = new OrdineDAO();
		ordineDAO.aggiornaStatoOrdine(idOrdine, stato);

		response.sendRedirect(request.getContextPath() + "/admin/dettaglio-ordine?id=" + idOrdine);
	}
}