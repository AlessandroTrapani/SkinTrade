package controller;

import java.io.IOException;

import dao.UtenteDAO;
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

        UtenteDAO utenteDAO = new UtenteDAO();

        if (utenteDAO.emailEsistente(email.trim())) {
            request.setAttribute("errore", "Email già registrata.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/registrazione.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome.trim());
        nuovoUtente.setCognome(cognome.trim());
        nuovoUtente.setEmail(email.trim());
        nuovoUtente.setPassword(password);
        nuovoUtente.setRuolo("UTENTE");

        boolean salvato = utenteDAO.salva(nuovoUtente);

        if (!salvato) {
            request.setAttribute("errore", "Errore durante la registrazione.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/registrazione.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Utente utenteLoggato = utenteDAO.trovaPerEmailEPassword(email.trim(), password);

        HttpSession sessione = request.getSession();
        sessione.setAttribute("utenteLoggato", utenteLoggato);
        sessione.setAttribute("tokenAccesso", "TOKEN_VALIDO");
        
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}