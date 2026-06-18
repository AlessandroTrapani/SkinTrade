package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modello.Carrello;
import modello.Prodotto;

@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CarrelloServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/carrello.jsp");
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
        List<Prodotto> prodotti = creaListaProdotti();

        for (Prodotto prodotto : prodotti) {
            if (prodotto.getId() == idProdotto) {
                return prodotto;
            }
        }

        return null;
    }

    private List<Prodotto> creaListaProdotti() {
        List<Prodotto> prodotti = new ArrayList<>();

        prodotti.add(new Prodotto(
                1,
                "AK-47 Redline",
                "Counter-Strike 2",
                "Skin",
                "Classificata",
                "Testata sul campo",
                29.99,
                5,
                "ak-redline.jpg",
                "Skin per AK-47 con design rosso e nero."
        ));

        prodotti.add(new Prodotto(
                2,
                "AWP Asiimov",
                "Counter-Strike 2",
                "Skin",
                "Coperta",
                "Segnata dalle battaglie",
                74.90,
                2,
                "awp-asiimov.jpg",
                "Una delle skin AWP più riconoscibili."
        ));

        prodotti.add(new Prodotto(
                3,
                "Sticker Dragon Lore",
                "Counter-Strike 2",
                "Adesivo",
                "Rara",
                "Nuovo",
                12.50,
                10,
                "sticker-dragon.jpg",
                "Adesivo collezionabile per armi."
        ));

        return prodotti;
    }
}