package control;

import java.io.IOException;
import java.util.ArrayList;

import dao.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CatalogoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ricerca = request.getParameter("ricerca");
        String gioco = request.getParameter("gioco");
        String categoria = request.getParameter("categoria");

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        ArrayList<Prodotto> prodotti = prodottoDAO.cercaProdotti(ricerca, gioco, categoria);

        request.setAttribute("prodotti", prodotti);
        request.setAttribute("ricerca", ricerca);
        request.setAttribute("gioco", gioco);
        request.setAttribute("categoria", categoria);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/pagine/catalogo.jsp");
        dispatcher.forward(request, response);
    }
}