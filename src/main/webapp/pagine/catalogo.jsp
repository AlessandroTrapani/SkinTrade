<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="modello.Prodotto" %>

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
            <a href="${pageContext.request.contextPath}/login">Login</a>
        </nav>
    </div>
</header>

<main class="contenitore contenuto-pagina">
    <h2>Catalogo prodotti</h2>
    <p>Consulta gli oggetti digitali disponibili su SkinTrade.</p>

    <div class="griglia-prodotti">
        <%
            List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");

            if (prodotti != null && !prodotti.isEmpty()) {
                for (Prodotto prodotto : prodotti) {
        %>

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

        <%
                }
            } else {
        %>

        <p>Nessun prodotto disponibile.</p>

        <%
            }
        %>
    </div>
</main>

<footer class="piede-sito">
    <p>SkinTrade - Progetto Tecnologie Software per il Web</p>
</footer>

</body>
</html>