package control.admin;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet che gestisce la dashboard dell'area amministratore.
 * Inoltra la richiesta alla pagina principale dell'area admin.
 */
@WebServlet("/admin/home")
public class HomeAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HomeAdminServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/home.jsp");
		dispatcher.forward(request, response);
	}
}