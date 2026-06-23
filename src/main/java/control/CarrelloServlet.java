package control;

import dao.ProdottoDAO;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.Prodotto;

@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CarrelloServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/carrello.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String azione = request.getParameter("azione");

        HttpSession sessione = request.getSession();

        Carrello carrello = (Carrello) sessione.getAttribute("carrello");

        if (carrello == null) {
            carrello = new Carrello();
            sessione.setAttribute("carrello", carrello);
        }

        if (azione == null) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        if (azione.equals("aggiungi")) {
            aggiungiAlCarrello(request, carrello);
        } else if (azione.equals("aggiorna")) {
            aggiornaCarrello(request, carrello);
        } else if (azione.equals("rimuovi")) {
            rimuoviDalCarrello(request, carrello);
        } else if (azione.equals("svuota")) {
            carrello.svuota();
        }

        response.sendRedirect(request.getContextPath() + "/carrello");
    }

    private void aggiungiAlCarrello(HttpServletRequest request, Carrello carrello) {
        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
        int quantita = Integer.parseInt(request.getParameter("quantita"));

        Prodotto prodotto = cercaProdottoPerId(idProdotto);

        if (prodotto != null && quantita > 0) {
            carrello.aggiungiProdotto(prodotto, quantita);
        }
    }

    private void aggiornaCarrello(HttpServletRequest request, Carrello carrello) {
        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
        int quantita = Integer.parseInt(request.getParameter("quantita"));

        if (quantita > 0) {
            carrello.aggiornaQuantita(idProdotto, quantita);
        }
    }

    private void rimuoviDalCarrello(HttpServletRequest request, Carrello carrello) {
        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
        carrello.rimuoviProdotto(idProdotto);
    }

    private Prodotto cercaProdottoPerId(int idProdotto) {
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        return prodottoDAO.trovaPerId(idProdotto);
    }

}