<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<header>
    
    <div class="container-title" >
        <div class="title">leboncoin - universitaire</div>
        
        <c:choose>
            <c:when test="${empty sessionScope.student}">
                <form method="POST" action="MonCompte">
                    <input type="text" placeholder="Identifiant" name="username"/>
                    <input type="password" placeholder="Mot de passe" name="password"/>
                    <input type="hidden" name="action" value="connexion"/>
                    <button class="button" type="submit">Ok</button>
                </form>
            </c:when>
            <c:otherwise>
                <form method="POST" action="MonCompte">
                    <input type="hidden" name="action" value="deconnexion"/>
                    <button class="button" type="submit">Déconnexion</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>

</header>
<nav>
    <div class="container-menu">
        <div class="menu">Rechercher</div>
        <div class="menu">Deposer une annonce</div>
        <div class="menu">Retirer une annonce</div>
        <div class="menu"><a href="MonCompte">Mon compte</a></div>
    </div>
</nav>