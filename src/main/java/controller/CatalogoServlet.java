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
import modello.Prodotto;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CatalogoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        request.setAttribute("prodotti", prodotti);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/pagine/catalogo.jsp");
        dispatcher.forward(request, response);
    }
}