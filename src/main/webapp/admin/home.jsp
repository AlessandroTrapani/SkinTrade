<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Area Admin - SkinTrade</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stile.css">
</head>
<body>

<header class="intestazione-sito">
    <div class="contenitore contenuto-intestazione">
        <h1 class="logo">SkinTrade Admin</h1>

        <nav class="menu-principale">
            <a href="${pageContext.request.contextPath}/index.jsp">Home sito</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </nav>
    </div>
</header>

<main class="contenitore contenuto-pagina">
    <h2>Dashboard amministratore</h2>

    <p>Da questa area puoi gestire il catalogo e gli ordini del sito.</p>

    <div class="griglia-schede">
        <div class="scheda">
            <h3>Gestione prodotti</h3>
            <p>Visualizza, aggiungi, modifica ed elimina i prodotti del catalogo.</p>
            <a class="bottone" href="${pageContext.request.contextPath}/admin/prodotti">Vai ai prodotti</a>
        </div>

        <div class="scheda">
            <h3>Gestione ordini</h3>
            <p>Visualizza gli ordini effettuati dagli utenti e aggiorna il loro stato.</p>
            <a class="bottone" href="${pageContext.request.contextPath}/admin/ordini">Vai agli ordini</a>
        </div>
    </div>
</main>

<footer class="piede-sito">
    <p>SkinTrade - Progetto Tecnologie Software per il Web</p>
</footer>

</body>
</html>