<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="modello.Prodotto" %>

<%
    ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>) request.getAttribute("prodotti");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione prodotti - SkinTrade Admin</title>
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
    <div class="intestazione-sezione">
        <h2>Gestione prodotti</h2>

        <a class="bottone" href="${pageContext.request.contextPath}/admin/nuovo-prodotto">
            Aggiungi prodotto
        </a>
    </div>

    <% if (prodotti == null || prodotti.isEmpty()) { %>

        <p>Non ci sono prodotti nel catalogo.</p>

    <% } else { %>

        <table class="tabella-carrello">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Gioco</th>
                    <th>Categoria</th>
                    <th>Rarità</th>
                    <th>Condizione</th>
                    <th>Prezzo</th>
                    <th>Quantità</th>
                    <th>Stato</th>
                    <th>Azioni</th>
                    
                </tr>
            </thead>

            <tbody>
                <% for (Prodotto prodotto : prodotti) { %>
                    <tr>
                        <td><%= prodotto.getId() %></td>
                        <td><%= prodotto.getNome() %></td>
                        <td><%= prodotto.getGioco() %></td>
                        <td><%= prodotto.getCategoria() %></td>
                        <td><%= prodotto.getRarita() %></td>
                        <td><%= prodotto.getCondizione() %></td>
                        <td>€ <%= prodotto.getPrezzo() %></td>
                        <td><%= prodotto.getQuantita() %></td>
                        <td><%= prodotto.getStato() %></td>
                        <td>
                            <a class="link-azione" href="${pageContext.request.contextPath}/admin/modifica-prodotto?id=<%= prodotto.getId() %>">
                                Modifica
                            </a>

                            |

                            <a class="link-azione link-pericolo" href="${pageContext.request.contextPath}/admin/elimina-prodotto?id=<%= prodotto.getId() %>">
                                Elimina
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