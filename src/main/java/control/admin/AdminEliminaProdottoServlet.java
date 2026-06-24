package control.admin;

import java.io.IOException;

import dao.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utente;

/**
 * Servlet dell'area amministratore che gestisce l'eliminazione logica dei prodotti.
 * Il prodotto non viene cancellato fisicamente dal database, ma marcato come eliminato
 * per mantenere consistenti gli ordini già effettuati.
 */
@WebServlet("/admin/elimina-prodotto")
public class AdminEliminaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminEliminaProdottoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sessione = request.getSession();

		Utente utente = (Utente) sessione.getAttribute("utenteLoggato");

		if (utente == null || !utente.isAdmin()) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String idParametro = request.getParameter("id");

		if (idParametro == null || idParametro.trim().equals("")) {
			response.sendRedirect(request.getContextPath() + "/admin/prodotti");
			return;
		}

		int idProdotto;

		try {
			idProdotto = Integer.parseInt(idParametro);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/prodotti");
			return;
		}

		ProdottoDAO prodottoDAO = new ProdottoDAO();
		prodottoDAO.elimina(idProdotto);

		response.sendRedirect(request.getContextPath() + "/admin/prodotti");
	}
}