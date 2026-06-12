<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.ProgettoTsw.model.UserBean" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrati — P&D Club</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Lato:wght@300;400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <!-- ==================== HEADER ==================== -->
    <header>
        <!-- Logo + Scritta P&D CLUB a sinistra -->
        <div class="logo-container">
            <a href="<%= request.getContextPath() %>/user?action=home">
                <img src="<%= request.getContextPath() %>/images/logo.jpeg" alt="Logo P&D Club">
            </a>
            <a href="<%= request.getContextPath() %>/user?action=home" class="logo-text">
                <span class="logo-pd">P&amp;D</span>
                <span class="logo-club">CLUB</span>
            </a>
        </div>
            <!-- Home -->
            <nav class="nav-links">
            <a href="<%= request.getContextPath() %>/user?action=home" class="icon-link">
            	<img src="<%=request.getContextPath()%>/images/home-image.png" alt="Home">
            <span>Home</span>
            </a>
            
            <!-- shop -->
            <a href="<%= request.getContextPath() %>/product?action=shop" class="icon-link">
            <img src="<%=request.getContextPath() %>/images/shop-image.png" alt="shop">
            <span>Shop</span>
            </a>
            
            <!-- contatti -->
            <a href="#contatti" class="icon-link">
            <img src="<%=request.getContextPath() %>/images/contatti-image.png" alt="contatti">
            <span>Contatti</span>
            </a>

            <!-- Carrello -->
            <a href="<%= request.getContextPath() %>/cart?action=view" class="icon-link">
                <img src="<%= request.getContextPath() %>/images/shopping_cart.png" alt="Carrello">
                <span>Carrello</span>
            </a>
            

            <!-- Accedi / Profilo + Esci (in base allo stato di login) -->
            <%
                UserBean utenteLoggato = (UserBean) session.getAttribute("user");
                if (utenteLoggato != null) {
            %>
                <a href="<%= request.getContextPath() %>/user?action=profilo" class="icon-link">
                    <img src="<%= request.getContextPath() %>/images/account.png" alt="Profilo">
                    <span>Profilo</span>
                </a>
                <a href="<%= request.getContextPath() %>/user?action=logout">Esci</a>
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

        <!-- Hamburger per mobile -->
        <button class="hamburger" id="hamburgerBtn" aria-label="Apri menu">
            <span></span>
            <span></span>
            <span></span>
        </button>
    </header>

    <!-- ==================== FORM REGISTRAZIONE ==================== -->
    <main class="form-page">
        <div class="form-container">
            <h2>Crea il tuo Account</h2>
            <p class="form-subtitle">Unisciti alla famiglia P&amp;D Club</p>

            <%-- Messaggio errore dal server --%>
            <%
                String errore = (String) request.getAttribute("errorMessage");
                if (errore != null) {
            %>
                <div class="alert alert-error"><%= errore %></div>
            <%  } %>

            <form id="registerForm" action="<%= request.getContextPath() %>/user?action=register" method="POST" novalidate>

                <!-- Riga Nome + Cognome affiancati -->
                <div class="form-row">
                    <div class="form-group">
                        <label for="nome">Nome</label>
                        <input type="text" id="nome" name="nome" placeholder="Mario" required>
                        <span class="error-message" id="nomeError"></span>
                    </div>
                    <div class="form-group">
                        <label for="cognome">Cognome</label>
                        <input type="text" id="cognome" name="cognome" placeholder="Rossi" required>
                        <span class="error-message" id="cognomeError"></span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="nome@esempio.it" required>
                    <span class="error-message" id="emailError"></span>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Minimo 8 caratteri" required>
                    <span class="error-message" id="passwordError"></span>
                </div>

                <div class="form-group">
                    <label for="indirizzo">Indirizzo di Spedizione</label>
                    <input type="text" id="indirizzo" name="indirizzo" placeholder="Via Roma 1, Avellino (AV)" required>
                    <span class="error-message" id="indirizzoError"></span>
                </div>

                <div class="form-group">
                    <label for="telefono">Telefono <span style="font-weight:300;">(facoltativo)</span></label>
                    <input type="tel" id="telefono" name="telefono" placeholder="+39 333 1234567">
                    <span class="error-message" id="telefonoError"></span>
                </div>

                <button type="submit" class="btn-primary">Registrati</button>
            </form>

            <p class="form-footer">
                Hai già un account? <a href="<%= request.getContextPath() %>/user?action=viewLogin">Accedi</a>
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
