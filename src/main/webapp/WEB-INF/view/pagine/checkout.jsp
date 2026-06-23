<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modello.Carrello" %>
<%@ page import="modello.ElementoCarrello" %>
<%@ page import="modello.Utente" %>

<%
    Carrello carrello = (Carrello) session.getAttribute("carrello");
    Utente utente = (Utente) session.getAttribute("utenteLoggato");
    String errore = (String) request.getAttribute("errore");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Checkout - SkinTrade</title>
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
    <h2>Conferma ordine</h2>

    <% if (errore != null) { %>
        <p class="messaggio-errore"><%= errore %></p>
    <% } %>

    <div class="box-form">
        <h3>Dati consegna digitale</h3>

        <form action="${pageContext.request.contextPath}/checkout" method="post">
            <label for="emailConsegna">Email consegna *</label>
            <input type="email" id="emailConsegna" name="emailConsegna"
                   value="<%= utente != null ? utente.getEmail() : "" %>">

            <label for="noteConsegna">Note consegna</label>
            <input type="text" id="noteConsegna" name="noteConsegna"
                   placeholder="Esempio: contattami prima della consegna">

            <label for="metodoPagamento">Metodo pagamento *</label>
            <select id="metodoPagamento" name="metodoPagamento">
                <option value="">Seleziona</option>
                <option value="Carta">Carta</option>
                <option value="PayPal">PayPal</option>
                <option value="Saldo SkinTrade">Saldo SkinTrade</option>
            </select>
            <h3>Simulazione pagamento</h3>

<label for="nomeCarta">Nome intestatario *</label>
<input 
    type="text" 
    id="nomeCarta" 
    name="nomeCarta" 
    placeholder="Mario Rossi"
>

<label for="numeroCarta">Numero carta *</label>
<input 
    type="text" 
    id="numeroCarta" 
    name="numeroCarta" 
    maxlength="16"
    placeholder="1234567812345678"
>

<label for="scadenzaCarta">Scadenza *</label>
<input 
    type="month" 
    id="scadenzaCarta" 
    name="scadenzaCarta"
>

<label for="cvv">CVV *</label>
<input 
    type="password" 
    id="cvv" 
    name="cvv" 
    maxlength="3"
    placeholder="123"
>


            <h3>Riepilogo carrello</h3>

            <% if (carrello != null && !carrello.isVuoto()) { %>
                <table class="tabella-carrello">
                    <thead>
                        <tr>
                            <th>Prodotto</th>
                            <th>Prezzo</th>
                            <th>Quantità</th>
                            <th>Totale</th>
                        </tr>
                    </thead>

                    <tbody>
                        <% for (ElementoCarrello elemento : carrello.getElementi()) { %>
                            <tr>
                                <td><%= elemento.getProdotto().getNome() %></td>
                                <td>€ <%= elemento.getProdotto().getPrezzo() %></td>
                                <td><%= elemento.getQuantita() %></td>
                                <td>€ <%= elemento.getTotale() %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>

                <h3>Totale ordine: € <%= carrello.getTotale() %></h3>
            <% } %>

            <button class="bottone" type="submit">Conferma ordine</button>
        </form>
    </div>
</main>

<footer class="piede-sito">
    <p>SkinTrade - Progetto Tecnologie Software per il Web</p>
</footer>

</body>
</html>