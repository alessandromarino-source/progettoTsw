package it.unisa.ProgettoTsw.control;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.unisa.ProgettoTsw.dao.UserDao;
import it.unisa.ProgettoTsw.dao.UserDaoImplements;
import it.unisa.ProgettoTsw.model.UserBean;

@WebServlet("/user")
public class UserControl extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // 1. Variabile di istanza per il DAO (come fa il prof)
    private UserDao userDao;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        // 2. Recupero del DataSource dal contesto dell'applicazione
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        userDao = new UserDaoImplements(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 3. Smistamento dell'azione
        String targetPage = processAction(request);
        
        // 4. Dispatching verso la vista corretta
        RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 5. Delegazione del POST al GET
        doGet(request, response);
    }

    private String processAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        String targetPage = "login.jsp"; // Vista di default
        
        try {
            if (action != null) {
                if (action.equalsIgnoreCase("login")) {
                    targetPage = loginUser(request);
                } else if (action.equalsIgnoreCase("logout")) {
                    targetPage = logoutUser(request);
                } else if (action.equalsIgnoreCase("register")) {
                    targetPage = registerUser(request);
                }
            }
        } catch (SQLException e) {
            // 6. Gestione eccezioni identica a quella del prof
            System.err.println("Error:" + e.getMessage());
            request.setAttribute("errorMessage", "Errore interno del server.");
            targetPage = "login.jsp";
        }
        
        return targetPage;
    }


    private String loginUser(HttpServletRequest request) throws SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserBean user = userDao.doRetrieveByEmailAndPassword(email, password);

        if (user != null) {
            // Creiamo la sessione (o la recuperiamo se esiste)
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "index.jsp"; // Login corretto: redirect alla Home
        } else {
            request.setAttribute("errorMessage", "Email o Password errate. Riprova.");
            return "login.jsp"; // Login errato: torno al form
        }
    }

    private String logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Distrugge la sessione
        }
        return "login.jsp";
    }

    private String registerUser(HttpServletRequest request) throws SQLException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String indirizzo = request.getParameter("indirizzo");
        String telefono = request.getParameter("telefono");
        String ruolo = "cliente"; // Ruolo predefinito per i nuovi utenti

        // 1. Controllo preventivo sul database per evitare duplicati tramite doRetrieveByEmail
        UserBean existingUser = userDao.doRetrieveByEmail(email);
        if (existingUser != null && existingUser.getIdUtente() != 0) {
            request.setAttribute("errorMessage", "Questa email è già associata a un account.");
            return "registrazione.jsp"; // Ritorno al form di registrazione
        }

        // 2.Inizializzo il Bean con i dati inviati dalla richiesta HTTP
        UserBean newUser = new UserBean();
        newUser.setNome(nome);
        newUser.setCognome(cognome);
        newUser.setEmail(email);
        newUser.setPasswordHash(password);
        newUser.setIndirizzoSpedizione(indirizzo);
        newUser.setTelefono(telefono);
        newUser.setRuolo(ruolo);

        // Salvo l'account nel database tramite il dao
        userDao.doSave(newUser);

        //mando l'utente al login
        request.setAttribute("successMessage", "Registrazione completata con successo! Effettua l'accesso.");
        return "login.jsp";
    }
}