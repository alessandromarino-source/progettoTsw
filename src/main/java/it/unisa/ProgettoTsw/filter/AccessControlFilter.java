package it.unisa.ProgettoTsw.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import it.unisa.ProgettoTsw.model.UserBean;

@WebFilter("/*")
public class AccessControlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String contextPath = request.getContextPath();
        // Path della risorsa richiesta senza il context path (es. "/user", "/admin", "/css/style.css")
        String path = request.getRequestURI().substring(contextPath.length());

        // 1. Le risorse statiche (CSS, JS, immagini) passano sempre
        if (isStaticResource(path)) {
            chain.doFilter(req, res);
            return;
        }

        // 2. Recupero lo stato di autenticazione dalla sessione
        HttpSession session = request.getSession(false);
        UserBean user = (session != null) ? (UserBean) session.getAttribute("user") : null;
        String token = (session != null) ? (String) session.getAttribute("token") : null;
        boolean loggato = (user != null && token != null);

        // 3. Area amministratore: serve essere loggati E avere ruolo 'admin'
        if (path.startsWith("/admin")) {
            if (loggato && "admin".equalsIgnoreCase(user.getRuolo())) {
                chain.doFilter(req, res);
            } else if (loggato) {
                // Loggato ma non admin: accesso negato, torno alla home
                response.sendRedirect(contextPath + "/user?action=home");
            } else {
                // Non loggato: vai al login
                response.sendRedirect(contextPath + "/user?action=viewLogin");
            }
            return;
        }

        // 4. Azioni riservate ai clienti registrati (qualsiasi ruolo, ma loggati)
        if (isProtected(path, request)) {
            if (loggato) {
                chain.doFilter(req, res);
            } else {
                response.sendRedirect(contextPath + "/user?action=viewLogin");
            }
            return;
        }

        // 5. Tutto il resto e' pubblico (home, shop, carrello, login, registrazione...)
        chain.doFilter(req, res);
    }

    // Azioni che richiedono il login: profilo, storico ordini, checkout
    private boolean isProtected(String path, HttpServletRequest request) {
        // Tutto il servlet degli ordini (checkout + storico) e' riservato
        if (path.startsWith("/order")) {
            return true;
        }
        // Alcune azioni del servlet /user
        if (path.startsWith("/user")) {
            String action = request.getParameter("action");
            return "profilo".equalsIgnoreCase(action) || "ordini".equalsIgnoreCase(action);
        }
        return false;
    }

    // Riconosce CSS, JavaScript e immagini, che non vanno mai filtrati
    private boolean isStaticResource(String path) {
        return path.startsWith("/css") || path.startsWith("/styles")
                || path.startsWith("/scripts") || path.startsWith("/js")
                || path.startsWith("/images")
                || path.endsWith(".css") || path.endsWith(".js")
                || path.endsWith(".png") || path.endsWith(".jpg")
                || path.endsWith(".jpeg") || path.endsWith(".gif")
                || path.endsWith(".ico");
    }
}
