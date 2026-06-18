package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modello.Utente;

@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegistrazioneServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/registrazione.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (nome == null || nome.trim().equals("")
                || cognome == null || cognome.trim().equals("")
                || email == null || email.trim().equals("")
                || password == null || password.trim().equals("")) {

            request.setAttribute("errore", "Compila tutti i campi.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/registrazione.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Utente utente = new Utente(3, nome.trim(), cognome.trim(), email.trim(), password, "USER");

        HttpSession sessione = request.getSession();
        sessione.setAttribute("utenteLoggato", utente);

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}