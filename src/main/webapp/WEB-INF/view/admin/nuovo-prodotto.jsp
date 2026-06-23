<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String errore = (String) request.getAttribute("errore");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Nuovo prodotto - SkinTrade Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/stile.css">
</head>
<body>

<header class="intestazione-sito">
    <div class="contenitore contenuto-intestazione">
        <h1 class="logo">SkinTrade Admin</h1>

       <nav class="menu-principale">
    <a href="${pageContext.request.contextPath}/admin/home">Dashboard</a>
    <a href="${pageContext.request.contextPath}/admin/prodotti">Prodotti</a>
    <a href="${pageContext.request.contextPath}/admin/ordini">Ordini</a>
    <a href="${pageContext.request.contextPath}/catalogo">Catalogo sito</a>
    <a href="${pageContext.request.contextPath}/index.jsp">Home sito</a>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</nav>
    </div>
</header>

<main class="contenitore contenuto-pagina">
    <a href="${pageContext.request.contextPath}/admin/prodotti">Torna ai prodotti</a>

    <h2>Nuovo prodotto</h2>

    <% if (errore != null) { %>
        <p class="messaggio-errore"><%= errore %></p>
    <% } %>

    <div class="box-form">
        <form action="${pageContext.request.contextPath}/admin/nuovo-prodotto" method="post">
            <label for="nome">Nome *</label>
            <input type="text" id="nome" name="nome">

            <label for="gioco">Gioco *</label>
            <input type="text" id="gioco" name="gioco" placeholder="Esempio: Counter-Strike 2">

            <label for="categoria">Categoria *</label>
            <input type="text" id="categoria" name="categoria" placeholder="Esempio: Skin, Adesivo, Coltello">

            <label for="rarita">Rarità</label>
            <input type="text" id="rarita" name="rarita" placeholder="Esempio: Classificata">

            <label for="condizione">Condizione</label>
            <input type="text" id="condizione" name="condizione" placeholder="Esempio: Testata sul campo">

            <label for="prezzo">Prezzo *</label>
            <input type="number" id="prezzo" name="prezzo" min="0" step="0.01">

            <label for="quantita">Quantità *</label>
            <input type="number" id="quantita" name="quantita" min="0">

            <label for="immagine">Nome immagine</label>
            <input type="text" id="immagine" name="immagine" placeholder="Esempio: ak-redline.jpg">

            <label for="descrizione">Descrizione</label>
            <textarea id="descrizione" name="descrizione" rows="5"></textarea>

            <button class="bottone" type="submit">Salva prodotto</button>
        </form>
    </div>
</main>

<footer class="piede-sito">
    <p>SkinTrade - Area amministratore</p>
</footer>

</body>
</html>