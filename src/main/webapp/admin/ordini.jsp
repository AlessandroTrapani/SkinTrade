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
    <title>Gestione ordini - SkinTrade Admin</title>
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
    <h2>Gestione ordini</h2>

    <% if (ordini == null || ordini.isEmpty()) { %>

        <p>Non ci sono ordini registrati.</p>

    <% } else { %>

        <table class="tabella-carrello">
            <thead>
                <tr>
                    <th>ID ordine</th>
                    <th>ID utente</th>
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
                        <td><%= ordine.getIdUtente() %></td>
                        <td><%= ordine.getDataOrdine() %></td>
                        <td>€ <%= ordine.getTotale() %></td>
                        <td><%= ordine.getEmailConsegna() %></td>
                        <td><%= ordine.getMetodoPagamento() %></td>
                        <td><%= ordine.getStato() %></td>
                        <td>
                            <a class="link-azione" href="${pageContext.request.contextPath}/admin/dettaglio-ordine?id=<%= ordine.getId() %>">
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
    <p>SkinTrade - Area amministratore</p>
</footer>

</body>
</html>