<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.ProgettoTsw.model.UserBean" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accedi — P&D Club</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Lato:wght@300;400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <!-- ==================== HEADER ==================== -->
    <header>
        <div class="logo-container">
            <a href="<%= request.getContextPath() %>/user?action=home">
                <img src="<%= request.getContextPath() %>/images/logo.jpeg" alt="Logo P&D Club">
            </a>
            <a href="<%= request.getContextPath() %>/user?action=home" class="logo-text">
                <span class="logo-pd">P&amp;D</span>
                <span class="logo-club">CLUB</span>
            </a>
        </div>

        <nav class="nav-links">
            <a href="<%= request.getContextPath() %>/user?action=home">Home</a>
            <a href="<%= request.getContextPath() %>/product?action=shop">Shop</a>
            <a href="<%= request.getContextPath() %>/user?action=home#contatti">Contatti</a>
            <a href="<%= request.getContextPath() %>/cart?action=view" class="icon-link">
                <img src="<%= request.getContextPath() %>/images/shopping_cart.png" alt="Carrello">
                <span>Carrello</span>
            </a>
            <a href="<%= request.getContextPath() %>/user?action=viewLogin" class="icon-link">
                <img src="<%= request.getContextPath() %>/images/account.png" alt="Accedi">
                <span>Accedi</span>
            </a>
        </nav>

        <button class="hamburger" id="hamburgerBtn" aria-label="Apri menu">
            <span></span><span></span><span></span>
        </button>
    </header>

    <!-- ==================== FORM LOGIN ==================== -->
    <main class="form-page">
        <div class="form-container">
            <h2>Bentornato!</h2>
            <p class="form-subtitle">Accedi al tuo account P&amp;D Club</p>

            <%-- Messaggi dal server --%>
            <%
                String errore = (String) request.getAttribute("errorMessage");
                String successo = (String) request.getAttribute("successMessage");
                if (errore != null) {
            %>
                <div class="alert alert-error"><%= errore %></div>
            <%  }
                if (successo != null) {
            %>
                <div class="alert alert-success"><%= successo %></div>
            <%  } %>

            <form id="loginForm" action="<%= request.getContextPath() %>/user?action=login" method="POST" novalidate>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="nome@esempio.it" required>
                    <span class="error-message" id="emailError"></span>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="La tua password" required>
                    <span class="error-message" id="passwordError"></span>
                </div>

                <button type="submit" class="btn-primary">Accedi</button>
            </form>

            <p class="form-footer">
                Non hai un account? <a href="<%= request.getContextPath() %>/user?action=viewRegister">Registrati</a>
            </p>
        </div>
    </main>

    <!-- Script validazione esterno -->
    <script src="<%= request.getContextPath() %>/scripts/validation.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var hamburger = document.getElementById('hamburgerBtn');
            var navLinks = document.querySelector('.nav-links');
            if (hamburger) {
                hamburger.addEventListener('click', function() {
                    hamburger.classList.toggle('active');
                    navLinks.classList.toggle('nav-open');
                });
            }
        });
    </script>

</body>
</html>
