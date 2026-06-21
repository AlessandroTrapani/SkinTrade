<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String errore = (String) request.getAttribute("errore");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Registrazione - SkinTrade</title>
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
    <div class="box-form">
        <h2>Registrazione</h2>

        <% if (errore != null) { %>
            <p class="messaggio-errore"><%= errore %></p>
        <% } %>

        <form action="${pageContext.request.contextPath}/registrazione" method="post">
            <label for="nome">Nome</label>
            <input type="text" id="nome" name="nome">

            <label for="cognome">Cognome</label>
            <input type="text" id="cognome" name="cognome">

            <label for="email">Email</label>
            <input type="email" id="email" name="email">

            <label for="password">Password</label>
            <input type="password" id="password" name="password">

            <button class="bottone" type="submit">Registrati</button>
        </form>

        <p>
            Hai già un account?
            <a href="${pageContext.request.contextPath}/login">Accedi</a>
        </p>
    </div>
</main>

<footer class="piede-sito">
    <p>SkinTrade - Progetto Tecnologie Software per il Web</p>
</footer>

</body>
</html>