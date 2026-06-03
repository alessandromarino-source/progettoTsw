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

@WebServlet("/user")  // Lo slash singolo gestisce le pagine ma NON ruba i file CSS/immagini
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
        
        // 4. Dispatching verso la vista corretta (dentro WEB-INF/view/)
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
        // Di default, se l'azione è vuota o sconosciuta, mostriamo la Home Page protetta
        String targetPage = "/WEB-INF/view/index.jsp"; 
        
        try {
            if (action != null) {
                if (action.equalsIgnoreCase("home")) {
                    targetPage = "/WEB-INF/view/index.jsp";
                } else if (action.equalsIgnoreCase("viewLogin")) {
                    // Semplice inoltro al form di Login nascosto
                    targetPage = "/WEB-INF/view/login.jsp";
                } else if (action.equalsIgnoreCase("viewRegister")) {
                    // Semplice inoltro al form di Registrazione nascosto
                    targetPage = "/WEB-INF/view/registrazione.jsp";
                } else if (action.equalsIgnoreCase("login")) {
                    targetPage = loginUser(request);
                } else if (action.equalsIgnoreCase("logout")) {
                    targetPage = logoutUser(request);
                } else if (action.equalsIgnoreCase("register")) {
                    targetPage = registerUser(request);
                }
            }
        } catch (SQLException e) {
            // 6. Gestione eccezioni: loggo l'errore e torno alla pagina appropriata
            System.err.println("Error:" + e.getMessage());
            request.setAttribute("errorMessage", "Email o password non corrette. Se non hai ancora un account, registrati!");
            
            // Torno alla pagina giusta in base all'azione che ha causato l'errore
            if ("register".equalsIgnoreCase(action)) {
                targetPage = "/WEB-INF/view/registrazione.jsp";
            } else {
                targetPage = "/WEB-INF/view/login.jsp";
            }
        }
        
        return targetPage;
    }

    private String loginUser(HttpServletRequest request) throws SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserBean user = userDao.doRetrieveByEmailAndPassword(email, password);

        if (user != null) {
        		HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("token", java.util.UUID.randomUUID().toString()); // <-- token in sessione
            return "/WEB-INF/view/index.jsp";
        } else {
            request.setAttribute("errorMessage", "Email o password non corrette. Se non hai ancora un account, registrati!");
            return "/WEB-INF/view/login.jsp"; // Login errato: torno al form protetto
        }
    }

    private String logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Distrugge la sessione
        }
        return "/WEB-INF/view/index.jsp"; // Dopo il logout torna alla home come ospite
    }

    private String registerUser(HttpServletRequest request) throws SQLException {
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String indirizzo = request.getParameter("indirizzo");
        String telefono = request.getParameter("telefono");
        String ruolo = "cliente"; 

        // 1. Validazione server-side dei campi obbligatori
        if (nome == null || nome.trim().isEmpty() || 
            cognome == null || cognome.trim().isEmpty() ||
            email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty() ||
            indirizzo == null || indirizzo.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Tutti i campi obbligatori devono essere compilati.");
            return "/WEB-INF/view/registrazione.jsp";
        }

        // 2. Validazione formato con espressioni regolari (stesse del JavaScript)
        if (!nome.trim().matches("^[A-Za-zÀ-ÖØ-öø-ÿ' ]{2,50}$")) {
            request.setAttribute("errorMessage", "Il nome deve contenere solo lettere (minimo 2 caratteri).");
            return "/WEB-INF/view/registrazione.jsp";
        }

        if (!cognome.trim().matches("^[A-Za-zÀ-ÖØ-öø-ÿ' ]{2,50}$")) {
            request.setAttribute("errorMessage", "Il cognome deve contenere solo lettere (minimo 2 caratteri).");
            return "/WEB-INF/view/registrazione.jsp";
        }

        if (!email.trim().matches("^\\S+@\\S+\\.\\S+$")) {
            request.setAttribute("errorMessage", "Inserisci un indirizzo email valido.");
            return "/WEB-INF/view/registrazione.jsp";
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            request.setAttribute("errorMessage", "La password deve avere almeno 8 caratteri, una maiuscola, una minuscola e un numero.");
            return "/WEB-INF/view/registrazione.jsp";
        }

        if (indirizzo.trim().length() < 5) {
            request.setAttribute("errorMessage", "L'indirizzo deve contenere almeno 5 caratteri.");
            return "/WEB-INF/view/registrazione.jsp";
        }

        // 3. Controllo preventivo sul database per evitare duplicati
        UserBean existingUser = userDao.doRetrieveByEmail(email);
        if (existingUser != null && existingUser.getIdUtente() != 0) {
            request.setAttribute("errorMessage", "Questa email è già associata a un account.");
            return "/WEB-INF/view/registrazione.jsp"; 
        }

        // 4. Inizializzo il Bean con i dati inviati dalla richiesta HTTP
        UserBean newUser = new UserBean();
        newUser.setNome(nome.trim());
        newUser.setCognome(cognome.trim());
        newUser.setEmail(email.trim());
        newUser.setPasswordHash(password);
        newUser.setIndirizzoSpedizione(indirizzo.trim());
        newUser.setTelefono(telefono != null ? telefono.trim() : "");
        newUser.setRuolo(ruolo);

        // Salvo l'account nel database tramite il DAO
        userDao.doSave(newUser);

        request.setAttribute("successMessage", "Registrazione completata con successo! Effettua l'accesso.");
        return "/WEB-INF/view/login.jsp";
    }
}