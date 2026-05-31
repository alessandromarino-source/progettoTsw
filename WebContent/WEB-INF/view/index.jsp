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
        <div class="logo-container">
            <a href="<%= request.getContextPath() %>/user?action=home">
                <img src="<%= request.getContextPath() %>/img/logo.png" alt="Logo Azienda">
            </a>
        </div>

        <div class="nav-links">
            <a href="<%= request.getContextPath() %>/user?action=home">Home</a>
            <a href="<%= request.getContextPath() %>/product?action=shop">Shop</a>
            <a href="<%= request.getContextPath() %>/user?action=contatti">Contatti</a>
            
            <div class="icon-container">
                <a href="<%= request.getContextPath() %>/cart?action=view">
                    <img src="<%= request.getContextPath() %>/img/icona-carrello.png" alt="Carrello">
                </a>

                <%
                    UserBean utenteLoggato = (UserBean) session.getAttribute("user");
                    if (utenteLoggato != null) {
                %>
                    <a href="<%= request.getContextPath() %>/user?action=profilo">
                        <img src="<%= request.getContextPath() %>/img/icona-omino.png" alt="Profilo">
                    </a>
                <%
                    } else {
                %>
                    <a href="<%= request.getContextPath() %>/user?action=viewLogin">
                        <img src="<%= request.getContextPath() %>/img/icona-omino.png" alt="Accedi">
                    </a>
                <%
                    }
                %>
            </div>
        </div>
    </header>

    <img src="<%= request.getContextPath() %>/img/banner-campi.jpg" alt="I nostri campi agricoli" class="hero-image">

    <div class="about-section">
        <h2>La Nostra Realtà Agroalimentare</h2>
        <p>
            Benvenuti nella nostra azienda. Da generazioni lavoriamo a stretto contatto con la terra 
            e con le materie prime del nostro territorio, unendo le antiche tradizioni contadine alle più 
            moderne tecnologie di produzione sicura. 
        </p>
        <p>
            La qualità dei nostri prodotti agroalimentari non nasce per caso. Coltiviamo legami solidi e diretti 
            con una rete di <strong>fornitori locali selezionati</strong>, garantendo una filiera corta e trasparente. 
            Ogni processo all'interno della nostra struttura è pensato per preservare il sapore autentico della natura 
            e portarlo direttamente sulla vostra tavola, rispettando l'ambiente e valorizzando il lavoro umano.
        </p>
    </div>

</body>
</html>