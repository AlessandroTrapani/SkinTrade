package control;

import java.io.IOException;

import dao.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utente;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().equals("") || password == null || password.trim().equals("")) {
            request.setAttribute("errore", "Inserisci email e password.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        UtenteDAO utenteDAO = new UtenteDAO();
        Utente utente = utenteDAO.trovaPerEmailEPassword(email.trim(), password);

        if (utente == null) {
            request.setAttribute("errore", "Credenziali non valide.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        HttpSession sessione = request.getSession();
        sessione.setAttribute("utenteLoggato", utente);
        sessione.setAttribute("tokenAccesso", "TOKEN_VALIDO");

        if (utente.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/admin/home");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}