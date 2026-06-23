package controller.admin;

import java.io.IOException;
import java.util.ArrayList;

import dao.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modello.Prodotto;
import modello.Utente;

@WebServlet("/admin/prodotti")
public class AdminProdottiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminProdottiServlet() {
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

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        ArrayList<Prodotto> prodotti = prodottoDAO.trovaTuttiAdmin();

        request.setAttribute("prodotti", prodotti);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/prodotti.jsp");
        dispatcher.forward(request, response);
    }
}