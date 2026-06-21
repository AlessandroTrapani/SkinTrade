<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="modello.Prodotto" %>

<%
    List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");

    String ricerca = (String) request.getAttribute("ricerca");
    String giocoSelezionato = (String) request.getAttribute("gioco");
    String categoriaSelezionata = (String) request.getAttribute("categoria");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Catalogo - SkinTrade</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stile.css">
</head>
<body>

<header class="intestazione-sito">
    <div class="contenitore contenuto-intestazione">
        <h1 class="logo">SkinTrade</h1>

        <nav class="menu-principale">
    <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
    <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
    <a href="${pageContext.request.contextPath}/carrello">Carrello</a>

    <% if (session.getAttribute("utenteLoggato") == null) { %>
        <a href="${pageContext.request.contextPath}/login">Login</a>
    <% } else { %>
        <a href="${pageContext.request.contextPath}/storico-ordini">I miei ordini</a>

        <%
            modello.Utente utenteMenu = (modello.Utente) session.getAttribute("utenteLoggato");
            if (utenteMenu != null && utenteMenu.isAdmin()) {
        %>
            <a href="${pageContext.request.contextPath}/admin/home">Admin</a>
        <% } %>

        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    <% } %>
</nav>
    </div>
</header>

<main class="contenitore contenuto-pagina">
    <h2>Catalogo prodotti</h2>
    <p>Consulta gli oggetti digitali disponibili su SkinTrade.</p>

    <form class="form-filtri" action="${pageContext.request.contextPath}/catalogo" method="get">
        <div>
            <label for="ricerca">Cerca per nome</label>
            <input 
                type="text" 
                id="ricerca" 
                name="ricerca" 
                value="<%= ricerca != null ? ricerca : "" %>"
                placeholder="Esempio: AK-47"
            >
        </div>

        <div>
            <label for="gioco">Gioco</label>
            <select id="gioco" name="gioco">
                <option value="">Tutti</option>

                <option value="Counter-Strike 2" <%= "Counter-Strike 2".equals(giocoSelezionato) ? "selected" : "" %>>
                    Counter-Strike 2
                </option>
            </select>
        </div>

        <div>
            <label for="categoria">Categoria</label>
            <select id="categoria" name="categoria">
                <option value="">Tutte</option>

                <option value="Skin" <%= "Skin".equals(categoriaSelezionata) ? "selected" : "" %>>
                    Skin
                </option>

                <option value="Adesivo" <%= "Adesivo".equals(categoriaSelezionata) ? "selected" : "" %>>
                    Adesivo
                </option>

                <option value="Coltello" <%= "Coltello".equals(categoriaSelezionata) ? "selected" : "" %>>
                    Coltello
                </option>
            </select>
        </div>

        <div class="azioni-filtri">
            <button class="bottone" type="submit">Filtra</button>
            <a class="bottone-secondario" href="${pageContext.request.contextPath}/catalogo">Reset</a>
        </div>
    </form>

    <% if (prodotti != null && !prodotti.isEmpty()) { %>

        <div class="griglia-prodotti">

            <% for (Prodotto prodotto : prodotti) { %>

                <div class="scheda-prodotto">
                    <div class="immagine-prodotto">
                        <span>Immagine</span>
                    </div>

                    <h3><%= prodotto.getNome() %></h3>

                    <p><strong>Gioco:</strong> <%= prodotto.getGioco() %></p>
                    <p><strong>Categoria:</strong> <%= prodotto.getCategoria() %></p>
                    <p><strong>Rarità:</strong> <%= prodotto.getRarita() %></p>
                    <p><strong>Condizione:</strong> <%= prodotto.getCondizione() %></p>
                    <p><strong>Disponibili:</strong> <%= prodotto.getQuantita() %></p>

                    <p class="prezzo">€ <%= prodotto.getPrezzo() %></p>

                    <a class="bottone" href="${pageContext.request.contextPath}/dettaglio-prodotto?id=<%= prodotto.getId() %>">
                        Dettaglio
                    </a>
                </div>

            <% } %>

        </div>

    <% } else { %>

        <p>Nessun prodotto trovato con i filtri selezionati.</p>

    <% } %>

</main>

<footer class="piede-sito">
    <p>SkinTrade - Progetto Tecnologie Software per il Web</p>
</footer>

</body>
</html>