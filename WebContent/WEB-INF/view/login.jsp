<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
    <title>Login</title>
</head>
<body>

    <h2>Accedi all'E-Commerce</h2>

    <%
        String errore = (String) request.getAttribute("errorMessage");
        String successo = (String) request.getAttribute("successMessage");
        
        if (errore != null) {
    %>
        <p style="color: red;"><%= errore %></p>
    <%
        }
        if (successo != null) {
    %>
        <p style="color: green; font-weight: bold;"><%= successo %></p>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/user?action=login" method="POST">
        Email: <input type="email" name="email" required><br><br>
        Password: <input type="password" name="password" required><br><br>
        
        <input type="submit" value="Effettua Login">
    </form>

    <p>Nuovo utente? <a href="registrazione.jsp">Registrati qui</a></p>

</body>
</html>