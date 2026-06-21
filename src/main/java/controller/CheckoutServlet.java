package controller;

import java.io.IOException;

import dao.OrdineDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modello.Carrello;
import modello.Ordine;
import modello.Utente;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CheckoutServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession();

        Utente utente = (Utente) sessione.getAttribute("utenteLoggato");
        Carrello carrello = (Carrello) sessione.getAttribute("carrello");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (carrello == null || carrello.isVuoto()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/checkout.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession();

        Utente utente = (Utente) sessione.getAttribute("utenteLoggato");
        Carrello carrello = (Carrello) sessione.getAttribute("carrello");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (carrello == null || carrello.isVuoto()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        String emailConsegna = request.getParameter("emailConsegna");
        String noteConsegna = request.getParameter("noteConsegna");
        String metodoPagamento = request.getParameter("metodoPagamento");
        String nomeCarta = request.getParameter("nomeCarta");
        String numeroCarta = request.getParameter("numeroCarta");
        String scadenzaCarta = request.getParameter("scadenzaCarta");
        String cvv = request.getParameter("cvv");
        if (emailConsegna == null || emailConsegna.trim().equals("")
                || metodoPagamento == null || metodoPagamento.trim().equals("")
                || nomeCarta == null || nomeCarta.trim().equals("")
                || numeroCarta == null || numeroCarta.trim().equals("")
                || scadenzaCarta == null || scadenzaCarta.trim().equals("")
                || cvv == null || cvv.trim().equals("")) {

            request.setAttribute("errore", "Compila tutti i campi obbligatori, inclusi i dati di pagamento.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/checkout.jsp");
            dispatcher.forward(request, response);
            return;
        }
        if (!numeroCarta.matches("\\d{16}")) {
            request.setAttribute("errore", "Il numero carta deve contenere 16 cifre.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/checkout.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (!cvv.matches("\\d{3}")) {
            request.setAttribute("errore", "Il CVV deve contenere 3 cifre.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/checkout.jsp");
            dispatcher.forward(request, response);
            return;
        }
        Ordine ordine = new Ordine();
        ordine.setIdUtente(utente.getId());
        ordine.setTotale(carrello.getTotale());
        ordine.setEmailConsegna(emailConsegna.trim());
        ordine.setNoteConsegna(noteConsegna);
        ordine.setMetodoPagamento(metodoPagamento);
        ordine.setStato("IN_ELABORAZIONE");

        OrdineDAO ordineDAO = new OrdineDAO();
        boolean salvato = ordineDAO.salvaOrdine(ordine, carrello);

        if (!salvato) {
            request.setAttribute("errore", "Errore durante il salvataggio dell'ordine.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/checkout.jsp");
            dispatcher.forward(request, response);
            return;
        }

        carrello.svuota();

        response.sendRedirect(request.getContextPath() + "/storico-ordini");
    }
}
