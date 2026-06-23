package control.admin;

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

@WebServlet("/admin/ordini")
public class AdminOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminOrdiniServlet() {
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

		String dataInizio = request.getParameter("dataInizio");
		String dataFine = request.getParameter("dataFine");
		String idUtente = request.getParameter("idUtente");
		String emailConsegna = request.getParameter("emailConsegna");

		OrdineDAO ordineDAO = new OrdineDAO();
		ArrayList<Ordine> ordini = ordineDAO.cercaOrdiniAdmin(dataInizio, dataFine, idUtente, emailConsegna);

		request.setAttribute("ordini", ordini);
		request.setAttribute("dataInizio", dataInizio);
		request.setAttribute("dataFine", dataFine);
		request.setAttribute("idUtente", idUtente);
		request.setAttribute("emailConsegna", emailConsegna);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/ordini.jsp");
		dispatcher.forward(request, response);
	}
}