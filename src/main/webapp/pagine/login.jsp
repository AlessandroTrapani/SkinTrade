<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String errore = (String) request.getAttribute("errore");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Login - SkinTrade</title>
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
        <h2>Login</h2>

        <% if (errore != null) { %>
            <p class="messaggio-errore"><%= errore %></p>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <label for="email">Email</label>
            <input type="email" id="email" name="email">

            <label for="password">Password</label>
            <input type="password" id="password" name="password">

            <button class="bottone" type="submit">Accedi</button>
        </form>

        <p>
            Non hai un account?
            <a href="${pageContext.request.contextPath}/registrazione">Registrati</a>
        </p>

        <p class="testo-aiuto">
            Utente test: utente@skintrade.it / utente<br>
            Admin test: admin@skintrade.it / admin
        </p>
    </div>
</main>

<footer class="piede-sito">
    <p>SkinTrade - Progetto Tecnologie Software per il Web</p>
</footer>

</body>
</html>