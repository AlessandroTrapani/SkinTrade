<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Carrello" %>
<%@ page import="model.ElementoCarrello" %>

<%
Carrello carrello = (Carrello) session.getAttribute("carrello");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Carrello - SkinTrade</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/stile.css">
</head>
<body>

<header class="intestazione-sito">
    <div class="contenitore contenuto-intestazione">
        <h1 class="logo">SkinTrade</h1>

        <nav class="menu-principale">
    <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
    <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
    <a href="${pageContext.request.contextPath}/carrello">Carrello</a>

    <%
    if (session.getAttribute("utenteLoggato") == null) {
    %>
        <a href="${pageContext.request.contextPath}/login">Login</a>
    <%
    } else {
    %>
        <a href="${pageContext.request.contextPath}/storico-ordini">I miei ordini</a>

        <%
        model.Utente utenteMenu = (model.Utente) session.getAttribute("utenteLoggato");
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
    <h2>Carrello</h2>

    <% if (carrello == null || carrello.isVuoto()) { %>

        <p>Il carrello è vuoto.</p>
        <a class="bottone" href="${pageContext.request.contextPath}/catalogo">Vai al catalogo</a>

    <% } else { %>

        <table class="tabella-carrello">
            <thead>
                <tr>
                    <th>Prodotto</th>
                    <th>Prezzo</th>
                    <th>Quantità</th>
                    <th>Totale</th>
                    <th>Azioni</th>
                </tr>
            </thead>

            <tbody>
                <% for (ElementoCarrello elemento : carrello.getElementi()) { %>
                    <tr>
                        <td><%= elemento.getProdotto().getNome() %></td>
                        <td>€ <%= elemento.getProdotto().getPrezzo() %></td>

                        <td>
                            <form action="${pageContext.request.contextPath}/carrello" method="post">
                                <input type="hidden" name="azione" value="aggiorna">
                                <input type="hidden" name="idProdotto" value="<%= elemento.getProdotto().getId() %>">

                                <input type="number" name="quantita" value="<%= elemento.getQuantita() %>" min="1" max="<%= elemento.getProdotto().getQuantita() %>">

                                <button type="submit">Aggiorna</button>
                            </form>
                        </td>

                        <td>€ <%= elemento.getTotale() %></td>

                        <td>
                            <form action="${pageContext.request.contextPath}/carrello" method="post">
                                <input type="hidden" name="azione" value="rimuovi">
                                <input type="hidden" name="idProdotto" value="<%= elemento.getProdotto().getId() %>">
                                <button type="submit">Rimuovi</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <div class="riepilogo-carrello">
            <h3>Totale: € <%= carrello.getTotale() %></h3>

            <form action="${pageContext.request.contextPath}/carrello" method="post">
                <input type="hidden" name="azione" value="svuota">
                <button class="bottone bottone-secondario" type="submit">Svuota carrello</button>
            </form>

            <a class="bottone" href="${pageContext.request.contextPath}/checkout">Procedi all'ordine</a>
        </div>

    <% } %>
</main>

<footer class="piede-sito">
    <p>SkinTrade - Progetto Tecnologie Software per il Web</p>
</footer>

</body>
</html>