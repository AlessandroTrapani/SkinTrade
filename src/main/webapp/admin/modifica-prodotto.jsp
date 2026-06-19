<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modello.Prodotto" %>

<%
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
    String errore = (String) request.getAttribute("errore");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Modifica prodotto - SkinTrade Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stile.css">
</head>
<body>

<header class="intestazione-sito">
    <div class="contenitore contenuto-intestazione">
        <h1 class="logo">SkinTrade Admin</h1>

        <nav class="menu-principale">
            <a href="${pageContext.request.contextPath}/admin/home">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/prodotti">Prodotti</a>
            <a href="${pageContext.request.contextPath}/admin/ordini">Ordini</a>
            <a href="${pageContext.request.contextPath}/index.jsp">Sito</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </nav>
    </div>
</header>

<main class="contenitore contenuto-pagina">
    <a href="${pageContext.request.contextPath}/admin/prodotti">Torna ai prodotti</a>

    <h2>Modifica prodotto</h2>

    <% if (errore != null) { %>
        <p class="messaggio-errore"><%= errore %></p>
    <% } %>

    <% if (prodotto != null) { %>

        <div class="box-form">
            <form action="${pageContext.request.contextPath}/admin/modifica-prodotto" method="post">
                <input type="hidden" name="id" value="<%= prodotto.getId() %>">

                <label for="nome">Nome *</label>
                <input type="text" id="nome" name="nome" value="<%= prodotto.getNome() %>">

                <label for="gioco">Gioco *</label>
                <input type="text" id="gioco" name="gioco" value="<%= prodotto.getGioco() %>">

                <label for="categoria">Categoria *</label>
                <input type="text" id="categoria" name="categoria" value="<%= prodotto.getCategoria() %>">

                <label for="rarita">Rarità</label>
                <input type="text" id="rarita" name="rarita" value="<%= prodotto.getRarita() != null ? prodotto.getRarita() : "" %>">

                <label for="condizione">Condizione</label>
                <input type="text" id="condizione" name="condizione" value="<%= prodotto.getCondizione() != null ? prodotto.getCondizione() : "" %>">

                <label for="prezzo">Prezzo *</label>
                <input type="number" id="prezzo" name="prezzo" min="0" step="0.01" value="<%= prodotto.getPrezzo() %>">

                <label for="quantita">Quantità *</label>
                <input type="number" id="quantita" name="quantita" min="0" value="<%= prodotto.getQuantita() %>">

                <label for="immagine">Nome immagine</label>
                <input type="text" id="immagine" name="immagine" value="<%= prodotto.getImmagine() != null ? prodotto.getImmagine() : "" %>">

                <label for="descrizione">Descrizione</label>
                <textarea id="descrizione" name="descrizione" rows="5"><%= prodotto.getDescrizione() != null ? prodotto.getDescrizione() : "" %></textarea>

                <button class="bottone" type="submit">Salva modifiche</button>
            </form>
        </div>

    <% } else { %>

        <p>Prodotto non trovato.</p>

    <% } %>
</main>

<footer class="piede-sito">
    <p>SkinTrade - Area amministratore</p>
</footer>

</body>
</html>