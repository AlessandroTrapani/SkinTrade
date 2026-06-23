package control;

import java.io.IOException;

import dao.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;

@WebServlet("/dettaglio-prodotto")
public class DettaglioProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DettaglioProdottoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idParametro = request.getParameter("id");

		if (idParametro == null || idParametro.trim().equals("")) {
			response.sendRedirect(request.getContextPath() + "/catalogo");
			return;
		}

		int idProdotto;

		try {
			idProdotto = Integer.parseInt(idParametro);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/catalogo");
			return;
		}

		ProdottoDAO prodottoDAO = new ProdottoDAO();
		Prodotto prodotto = prodottoDAO.trovaPerId(idProdotto);

		if (prodotto == null) {
			response.sendRedirect(request.getContextPath() + "/catalogo");
			return;
		}

		request.setAttribute("prodotto", prodotto);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/dettaglio-prodotto.jsp");
		dispatcher.forward(request, response);
	}
}