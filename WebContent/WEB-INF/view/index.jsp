<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.ProgettoTsw.model.UserBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Prodotti Agroalimentari</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <header>
        <!-- Logo a sinistra -->
        <div class="logo-container">
            <a href="<%= request.getContextPath() %>/user?action=home">
                <img src="<%= request.getContextPath() %>/images/logo.jpeg" alt="Logo Azienda">
            </a>
        </div>

        <!-- Tutti i link e le icone raggruppati a destra -->
        <nav class="nav-links">
            <a href="<%= request.getContextPath() %>/user?action=home">Home</a>
            <a href="<%= request.getContextPath() %>/product?action=shop">Shop</a>
            <a href="<%= request.getContextPath() %>/user?action=contatti">Contatti</a>
            
            <!-- Carrello con icona affiancata al testo -->
            <a href="<%= request.getContextPath() %>/cart?action=view" class="icon-link">
                <img src="<%= request.getContextPath() %>/images/shopping_cart.png" alt="Carrello">
                <span>Carrello</span>
            </a>

            <!-- Gestione Login / Profilo con icone affiancate al testo -->
            <%
                UserBean utenteLoggato = (UserBean) session.getAttribute("user");
                if (utenteLoggato != null) {
            %>
                <a href="<%= request.getContextPath() %>/user?action=profilo" class="icon-link">
                    <img src="<%= request.getContextPath() %>/images/icona-omino.png" alt="Profilo">
                    <span>Profilo</span>
                </a>
            <%
                } else {
            %>
                <a href="<%= request.getContextPath() %>/user?action=viewLogin" class="icon-link">
                    <img src="<%= request.getContextPath() %>/images/account.png" alt="Accedi">
                    <span>Accedi</span>
                </a>
            <%
                }
            %>
        </nav>
    </header>

</body>
</html>