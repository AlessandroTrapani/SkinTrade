<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="modello.Ordine" %>

<%
    ArrayList<Ordine> ordini = (ArrayList<Ordine>) request.getAttribute("ordini");
	String dataInizio = (String) request.getAttribute("dataInizio");
	String dataFine = (String) request.getAttribute("dataFine");
	String idUtente = (String) request.getAttribute("idUtente");
	String emailConsegna = (String) request.getAttribute("emailConsegna");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione ordini - SkinTrade Admin</title>
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
    <h2>Gestione ordini</h2>
	
	<form class="form-filtri" action="${pageContext.request.contextPath}/admin/ordini" method="get">
    <div>
        <label for="dataInizio">Da data</label>
        <input 
            type="date" 
            id="dataInizio" 
            name="dataInizio"
            value="<%= dataInizio != null ? dataInizio : "" %>"
        >
    </div>

    <div>
        <label for="dataFine">A data</label>
        <input 
            type="date" 
            id="dataFine" 
            name="dataFine"
            value="<%= dataFine != null ? dataFine : "" %>"
        >
    </div>

    <div>
        <label for="idUtente">ID utente</label>
        <input 
            type="number" 
            id="idUtente" 
            name="idUtente"
            min="1"
            value="<%= idUtente != null ? idUtente : "" %>"
            placeholder="Es. 1"
        >
    </div>

    <div>
        <label for="emailConsegna">Email cliente</label>
        <input 
            type="text" 
            id="emailConsegna" 
            name="emailConsegna"
            value="<%= emailConsegna != null ? emailConsegna : "" %>"
            placeholder="utente@skintrade.it"
        >
    </div>

    <div class="azioni-filtri">
        <button class="bottone" type="submit">Filtra</button>
        <a class="bottone-secondario" href="${pageContext.request.contextPath}/admin/ordini">Reset</a>
    </div>
</form>
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