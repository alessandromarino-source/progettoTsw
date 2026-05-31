<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accesso - E-Commerce</title>
</head>
<body>

    <h1>Accedi al tuo Account</h1>

    <%
        // Se la Servlet rileva un errore, ricarica questa pagina inserendo un messaggio nella richiesta
        String messaggioErrore = (String) request.getAttribute("errore");
        if (messaggioErrore != null) {
    %>
        <div class="error-box">
            <p><strong>Attenzione:</strong> <%= messaggioErrore %></p>
        </div>
    <%
        }
    %>

    <%-- Il form impacchetta i datie li spedisce alla Servlet di autenticazione --%>
    <form action="login" method="post">
        
        <label for="email">Indirizzo Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>
        
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password" required><br><br>
        
        <input type="submit" value="Accedi">
        
    </form>

</body>
</html>