package controller.admin;

import java.io.IOException;

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

@WebServlet("/admin/nuovo-prodotto")
public class AdminNuovoProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminNuovoProdottoServlet() {
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/nuovo-prodotto.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession();

        Utente utente = (Utente) sessione.getAttribute("utenteLoggato");

        if (utente == null || !utente.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String nome = request.getParameter("nome");
        String gioco = request.getParameter("gioco");
        String categoria = request.getParameter("categoria");
        String rarita = request.getParameter("rarita");
        String condizione = request.getParameter("condizione");
        String prezzoParametro = request.getParameter("prezzo");
        String quantitaParametro = request.getParameter("quantita");
        String immagine = request.getParameter("immagine");
        String descrizione = request.getParameter("descrizione");

        if (nome == null || nome.trim().equals("")
                || gioco == null || gioco.trim().equals("")
                || categoria == null || categoria.trim().equals("")
                || prezzoParametro == null || prezzoParametro.trim().equals("")
                || quantitaParametro == null || quantitaParametro.trim().equals("")) {

            request.setAttribute("errore", "Compila tutti i campi obbligatori.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/nuovo-prodotto.jsp");
            dispatcher.forward(request, response);
            return;
        }

        double prezzo;
        int quantita;

        try {
            prezzo = Double.parseDouble(prezzoParametro);
            quantita = Integer.parseInt(quantitaParametro);
        } catch (NumberFormatException e) {
            request.setAttribute("errore", "Prezzo e quantità devono essere valori numerici.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/nuovo-prodotto.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (prezzo < 0 || quantita < 0) {
            request.setAttribute("errore", "Prezzo e quantità non possono essere negativi.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/nuovo-prodotto.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Prodotto prodotto = new Prodotto();
        prodotto.setNome(nome.trim());
        prodotto.setGioco(gioco.trim());
        prodotto.setCategoria(categoria.trim());
        prodotto.setRarita(rarita);
        prodotto.setCondizione(condizione);
        prodotto.setPrezzo(prezzo);
        prodotto.setQuantita(quantita);
        prodotto.setImmagine(immagine);
        prodotto.setDescrizione(descrizione);

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        boolean salvato = prodottoDAO.salva(prodotto);

        if (!salvato) {
            request.setAttribute("errore", "Errore durante il salvataggio del prodotto.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/nuovo-prodotto.jsp");
            dispatcher.forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/prodotti");
    }
}