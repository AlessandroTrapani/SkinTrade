<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>SkinTrade</title>
    <link rel="stylesheet" href="css/stile.css">
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
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    <% } %>
</nav>
    </div>
</header>

<main>
    <section class="sezione-principale">
        <div class="contenitore">
            <h2>Oggetti digitali per videogiochi</h2>
            <p>
                Acquista skin, adesivi e oggetti cosmetici in modo semplice.
            </p>
            <a class="bottone" href="catalogo">Vai al catalogo</a>
        </div>
    </section>

    <section class="sezione-info">
        <div class="contenitore griglia-schede">
            <div class="scheda">
                <h3>Catalogo</h3>
                <p>Consulta gli oggetti disponibili e filtra per gioco, categoria e prezzo.</p>
            </div>

            <div class="scheda">
                <h3>Carrello</h3>
                <p>Aggiungi prodotti, modifica le quantità o rimuovi gli oggetti scelti.</p>
            </div>

            <div class="scheda">
                <h3>Ordini</h3>
                <p>Registrati, completa l’ordine e consulta lo storico degli acquisti.</p>
            </div>
        </div>
    </section>
</main>

<footer class="piede-sito">
    <p>SkinTrade - Progetto Tecnologie Software per il Web</p>
</footer>

</body>
</html>