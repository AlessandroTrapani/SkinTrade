<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="modello.Ordine" %>

<%
    ArrayList<Ordine> ordini = (ArrayList<Ordine>) request.getAttribute("ordini");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Storico ordini - SkinTrade</title>
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
    <h2>Storico ordini</h2>

    <% if (ordini == null || ordini.isEmpty()) { %>

        <p>Non hai ancora effettuato ordini.</p>
        <a class="bottone" href="${pageContext.request.contextPath}/catalogo">Vai al catalogo</a>

    <% } else { %>

        <table class="tabella-carrello">
            <thead>
                <tr>
                    <th>ID ordine</th>
                    <th>Data</th>
                    <th>Totale</th>
                    <th>Email consegna</th>
                    <th>Pagamento</th>
                    <th>Stato</th>
                    <th>Dettaglio</th>
                </tr>
            </thead>

            <tbody>
                <% for (Ordine ordine : ordini) { %>
                    <tr>
                        <td>#<%= ordine.getId() %></td>
                        <td><%= ordine.getDataOrdine() %></td>
                        <td>€ <%= ordine.getTotale() %></td>
                        <td><%= ordine.getEmailConsegna() %></td>
                        <td><%= ordine.getMetodoPagamento() %></td>
                        <td><%= ordine.getStato() %></td>
                        <td>
                            <a class="link-azione" href="${pageContext.request.contextPath}/dettaglio-ordine?id=<%= ordine.getId() %>">
                                Vedi
                            </a>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>

    <% } %>

</main>

<footer class="piede-sito">
    <p>SkinTrade - Progetto Tecnologie Software per il Web</p>
</footer>

</body>
</html>