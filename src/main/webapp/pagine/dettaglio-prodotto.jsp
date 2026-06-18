<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modello.Prodotto" %>

<%
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Dettaglio prodotto - SkinTrade</title>
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
            <a href="${pageContext.request.contextPath}/login">Login</a>
        </nav>
    </div>
</header>

<main class="contenitore contenuto-pagina">

    <% if (prodotto != null) { %>

        <a href="${pageContext.request.contextPath}/catalogo">Torna al catalogo</a>

        <div class="dettaglio-prodotto">
            <div class="immagine-dettaglio">
                <span>Immagine prodotto</span>
            </div>

            <div class="info-dettaglio">
                <h2><%= prodotto.getNome() %></h2>

                <p><strong>Gioco:</strong> <%= prodotto.getGioco() %></p>
                <p><strong>Categoria:</strong> <%= prodotto.getCategoria() %></p>
                <p><strong>Rarità:</strong> <%= prodotto.getRarita() %></p>
                <p><strong>Condizione:</strong> <%= prodotto.getCondizione() %></p>
                <p><strong>Disponibili:</strong> <%= prodotto.getQuantita() %></p>

                <p class="prezzo">€ <%= prodotto.getPrezzo() %></p>

                <p><%= prodotto.getDescrizione() %></p>

                <form action="${pageContext.request.contextPath}/carrello" method="post">
                    <input type="hidden" name="azione" value="aggiungi">
                    <input type="hidden" name="idProdotto" value="<%= prodotto.getId() %>">

                    <label for="quantita">Quantità:</label>
                    <input type="number" id="quantita" name="quantita" value="1" min="1" max="<%= prodotto.getQuantita() %>">

                    <button class="bottone" type="submit">Aggiungi al carrello</button>
                </form>
            </div>
        </div>

    <% } else { %>

        <p>Prodotto non trovato.</p>

    <% } %>

</main>

<footer class="piede-sito">
    <p>SkinTrade - Progetto Tecnologie Software per il Web</p>
</footer>

</body>
</html>