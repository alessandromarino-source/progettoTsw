<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
    <title>Registrazione Agroalimentare</title>
</head>
<body>

    <h2>Crea un nuovo account</h2>

    <%
        String errore = (String) request.getAttribute("errorMessage");
        if (errore != null) {
    %>
        <p style="color: red; font-weight: bold;"><%= errore %></p>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/user?action=register" method="POST">
        Nome: <input type="text" name="nome" required><br><br>
        Cognome: <input type="text" name="cognome" required><br><br>
        Email: <input type="email" name="email" required><br><br>
        Password: <input type="password" name="password" required><br><br>
        Indirizzo Spedizione: <input type="text" name="indirizzo" required><br><br>
        Telefono: <input type="text" name="telefono"><br><br>
        
        <input type="submit" value="Invia Dati">
    </form>

    <p>Hai già un account? <a href="login.jsp">Accedi qui</a></p>

</body>
</html>