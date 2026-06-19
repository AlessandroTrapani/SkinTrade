<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="modello.Ordine" %>
<%@ page import="modello.DettaglioOrdine" %>

<%
    Ordine ordine = (Ordine) request.getAttribute("ordine");
    ArrayList<DettaglioOrdine> dettagli = (ArrayList<DettaglioOrdine>) request.getAttribute("dettagli");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Dettaglio ordine admin - SkinTrade</title>
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

    <a href="${pageContext.request.contextPath}/admin/ordini">Torna agli ordini</a>

    <% if (ordine != null) { %>

        <h2>Dettaglio ordine #<%= ordine.getId() %></h2>

        <div class="riepilogo-carrello">
            <p><strong>ID utente:</strong> <%= ordine.getIdUtente() %></p>
            <p><strong>Data:</strong> <%= ordine.getDataOrdine() %></p>
            <p><strong>Totale:</strong> € <%= ordine.getTotale() %></p>
            <p><strong>Email consegna:</strong> <%= ordine.getEmailConsegna() %></p>
            <p><strong>Metodo pagamento:</strong> <%= ordine.getMetodoPagamento() %></p>
            <p><strong>Stato:</strong> <%= ordine.getStato() %></p>

            <% if (ordine.getNoteConsegna() != null && !ordine.getNoteConsegna().trim().equals("")) { %>
                <p><strong>Note consegna:</strong> <%= ordine.getNoteConsegna() %></p>
            <% } %>
        </div>

        <h3>Prodotti acquistati</h3>

        <% if (dettagli != null && !dettagli.isEmpty()) { %>

            <table class="tabella-carrello">
                <thead>
                    <tr>
                        <th>Prodotto</th>
                        <th>Prezzo</th>
                        <th>Quantità</th>
                        <th>Totale riga</th>
                    </tr>
                </thead>

                <tbody>
                    <% for (DettaglioOrdine dettaglio : dettagli) { %>
                        <tr>
                            <td><%= dettaglio.getNomeProdotto() %></td>
                            <td>€ <%= dettaglio.getPrezzo() %></td>
                            <td><%= dettaglio.getQuantita() %></td>
                            <td>€ <%= dettaglio.getTotaleRiga() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

        <% } else { %>

            <p>Nessun dettaglio disponibile per questo ordine.</p>

        <% } %>

    <% } else { %>

        <p>Ordine non trovato.</p>

    <% } %>

</main>

<footer class="piede-sito">
    <p>SkinTrade - Area amministratore</p>
</footer>

</body>
</html>