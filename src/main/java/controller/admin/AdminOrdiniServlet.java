package controller.admin;

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
import modello.Ordine;
import modello.Utente;

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

        OrdineDAO ordineDAO = new OrdineDAO();
        ArrayList<Ordine> ordini = ordineDAO.trovaTuttiOrdini();

        request.setAttribute("ordini", ordini);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/ordini.jsp");
        dispatcher.forward(request, response);
    }
}