<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.ProgettoTsw.model.UserBean" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>P&D Club — Prodotti Agroalimentari dall'Irpinia</title>
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Lato:wght@300;400;700&display=swap" rel="stylesheet">
    <!-- Foglio di stile esterno -->
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

        <!-- Navigazione a destra -->
        <nav class="nav-links">
            <a href="<%= request.getContextPath() %>/user?action=home">Home</a>
            <a href="<%= request.getContextPath() %>/product?action=shop">Shop</a>
            <a href="#contatti">Contatti</a>

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

    <!-- ==================== HERO SECTION ==================== -->
    <!--
        Per aggiungere un'immagine di sfondo: mettete il file in /images/hero-bg.jpg
        e decommentate la riga nel CSS (.hero { background-image: ... })
    -->
    <section class="hero">
        <div class="hero-overlay"></div>
        <div class="hero-content">
            <h1 class="hero-title">La Terra che Amiamo</h1>
            <p class="hero-subtitle">
                Prodotti agroalimentari d'eccellenza, coltivati con passione<br>
                nel cuore dell'Irpinia.
            </p>
            <a href="<%= request.getContextPath() %>/product?action=shop" class="btn-primary">
                Scopri i Prodotti
            </a>
        </div>
    </section>

    <!-- ==================== CHI SIAMO ==================== -->
    <section class="about-home" id="chi-siamo">
        <div class="about-home-inner">
            <h2>Chi Siamo</h2>
            <div class="about-divider"></div>
            <p>
                <strong>P&amp;D Club</strong> nasce dall'amore per la nostra terra, l'<strong>Irpinia</strong>,
                una delle aree più ricche e incontaminate della Campania. Selezioniamo con cura i migliori
                prodotti agroalimentari del nostro territorio — dall'olio extravergine d'oliva ai formaggi,
                dai legumi alle conserve artigianali — e li portiamo direttamente sulla tua tavola.
            </p>
            <p>
                Lavoriamo fianco a fianco con piccoli produttori locali che condividono la nostra filosofia:
                rispetto per la terra, metodi di coltivazione tradizionali e una qualità senza compromessi.
                Ogni prodotto che trovi nel nostro shop racconta una storia di passione e dedizione.
            </p>
        </div>
    </section>

    <!-- ==================== I NOSTRI VALORI ==================== -->
    <section class="values-section">
        <h2>Perché Sceglierci</h2>
        <div class="about-divider center"></div>
        <div class="values-grid">
            <div class="value-card">
                <div class="value-icon">&#127807;</div> <!-- 🌿 -->
                <h3>Territorio</h3>
                <p>
                    Prodotti coltivati nelle colline dell'Irpinia, dove il clima e il terreno
                    regalano sapori unici e autentici.
                </p>
            </div>
            <div class="value-card">
                <div class="value-icon">&#9989;</div> <!-- ✅ -->
                <h3>Qualità Certificata</h3>
                <p>
                    Selezioniamo solo prodotti con certificazioni di qualità — DOP, IGP e biologico —
                    per garantirti il meglio.
                </p>
            </div>
            <div class="value-card">
                <div class="value-icon">&#129309;</div> <!-- 🤝 -->
                <h3>Filiera Corta</h3>
                <p>
                    Dal produttore alla tua tavola senza intermediari.
                    Freschezza garantita e supporto diretto ai nostri agricoltori.
                </p>
            </div>
        </div>
    </section>

    <!-- ==================== CALL TO ACTION ==================== -->
    <section class="cta-section">
        <h2>Pronto a gustare l'Irpinia?</h2>
        <p>Esplora il nostro catalogo e scopri i sapori autentici della nostra terra.</p>
        <a href="<%= request.getContextPath() %>/product?action=shop" class="btn-primary btn-lg">
            Vai allo Shop
        </a>
    </section>

    <!-- ==================== FOOTER ==================== -->
    <footer class="site-footer" id="contatti">
        <div class="footer-grid">
            <!-- Colonna 1: Brand -->
            <div class="footer-col">
                <h4>P&amp;D Club</h4>
                <p>Prodotti agroalimentari d'eccellenza dall'Irpinia. La qualità della nostra terra, direttamente a casa tua.</p>
            </div>
            <!-- Colonna 2: Link rapidi -->
            <div class="footer-col">
                <h4>Navigazione</h4>
                <ul>
                    <li><a href="<%= request.getContextPath() %>/user?action=home">Home</a></li>
                    <li><a href="<%= request.getContextPath() %>/product?action=shop">Shop</a></li>
                    <li><a href="#contatti">Contatti</a></li>
                    <li><a href="<%= request.getContextPath() %>/user?action=viewLogin">Area Clienti</a></li>
                </ul>
            </div>
            <!-- Colonna 3: Contatti -->
            <div class="footer-col">
                <h4>Contatti</h4>
                <ul>
                    <li>&#128205; Irpinia, Avellino (AV)</li>
                    <li>&#9993; info@pdclub.it</li>
                    <li>&#128222; +39 012 345 6789</li>
                </ul>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2025 P&amp;D Club — Tutti i diritti riservati</p>
        </div>
    </footer>

    <!-- Script per il menu hamburger mobile -->
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
