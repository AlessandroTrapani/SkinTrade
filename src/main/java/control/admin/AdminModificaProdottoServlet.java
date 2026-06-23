package control.admin;

import java.io.IOException;

import dao.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import model.Utente;

@WebServlet("/admin/modifica-prodotto")
public class AdminModificaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminModificaProdottoServlet() {
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
		Prodotto prodotto = prodottoDAO.trovaPerId(idProdotto);

		if (prodotto == null) {
			response.sendRedirect(request.getContextPath() + "/admin/prodotti");
			return;
		}

		request.setAttribute("prodotto", prodotto);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/modifica-prodotto.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sessione = request.getSession();

		Utente utente = (Utente) sessione.getAttribute("utenteLoggato");

		if (utente == null || !utente.isAdmin()) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String idParametro = request.getParameter("id");
		String nome = request.getParameter("nome");
		String gioco = request.getParameter("gioco");
		String categoria = request.getParameter("categoria");
		String rarita = request.getParameter("rarita");
		String condizione = request.getParameter("condizione");
		String prezzoParametro = request.getParameter("prezzo");
		String quantitaParametro = request.getParameter("quantita");
		String immagine = request.getParameter("immagine");
		String descrizione = request.getParameter("descrizione");

		if (idParametro == null || idParametro.trim().equals("") || nome == null || nome.trim().equals("")
				|| gioco == null || gioco.trim().equals("") || categoria == null || categoria.trim().equals("")
				|| prezzoParametro == null || prezzoParametro.trim().equals("") || quantitaParametro == null
				|| quantitaParametro.trim().equals("")) {

			request.setAttribute("errore", "Compila tutti i campi obbligatori.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/modifica-prodotto.jsp");
			dispatcher.forward(request, response);
			return;
		}

		int idProdotto;
		double prezzo;
		int quantita;

		try {
			idProdotto = Integer.parseInt(idParametro);
			prezzo = Double.parseDouble(prezzoParametro);
			quantita = Integer.parseInt(quantitaParametro);
		} catch (NumberFormatException e) {
			request.setAttribute("errore", "ID, prezzo e quantità devono essere valori numerici.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/modifica-prodotto.jsp");
			dispatcher.forward(request, response);
			return;
		}

		if (prezzo < 0 || quantita < 0) {
			request.setAttribute("errore", "Prezzo e quantità non possono essere negativi.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/modifica-prodotto.jsp");
			dispatcher.forward(request, response);
			return;
		}

		Prodotto prodotto = new Prodotto();
		prodotto.setId(idProdotto);
		prodotto.setNome(nome.trim());
		prodotto.setGioco(gioco.trim());
		prodotto.setCategoria(categoria.trim());
		prodotto.setRarita(rarita);
		prodotto.setCondizione(condizione);
		prodotto.setPrezzo(prezzo);
		prodotto.setQuantita(quantita);
		prodotto.setImmagine(immagine);
		prodotto.setDescrizione(descrizione);

		ProdottoDAO prodottoDAO = new ProdottoDAO();
		boolean modificato = prodottoDAO.modifica(prodotto);

		if (!modificato) {
			request.setAttribute("errore", "Errore durante la modifica del prodotto.");
			request.setAttribute("prodotto", prodotto);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/modifica-prodotto.jsp");
			dispatcher.forward(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/admin/prodotti");
	}
}