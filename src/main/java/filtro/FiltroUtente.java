package filtro;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modello.Utente;

@WebFilter({
    "/checkout",
    "/storico-ordini",
    "/dettaglio-ordine"
})
public class FiltroUtente implements Filter {

    public FiltroUtente() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest richiesta = (HttpServletRequest) request;
        HttpServletResponse risposta = (HttpServletResponse) response;

        HttpSession sessione = richiesta.getSession(false);

        Utente utente = null;
        String tokenAccesso = null;

        if (sessione != null) {
            utente = (Utente) sessione.getAttribute("utenteLoggato");
            tokenAccesso = (String) sessione.getAttribute("tokenAccesso");
        }

        if (utente == null || tokenAccesso == null || !"TOKEN_VALIDO".equals(tokenAccesso)) {
            risposta.sendRedirect(richiesta.getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}