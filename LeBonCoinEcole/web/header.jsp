<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<header>
    
    <div class="container-title" >
        <div class="title">
            <a class="clickable" href="index">leboncoin - universitaire</a>
        </div>
        
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
        <div class="menu"><a class="clickable" href="search">Rechercher</a></div>
        <div class="menu"><a class="clickable" href="addAnnouncement">Deposer une annonce</a></div>
        <c:if test="${!empty sessionScope.student}">
            <div class="menu"><a class="clickable" href="MyAnnouncements">Mes annonces</a></div>
        </c:if>
        <div class="menu"><a class="clickable" href="MonCompte">Mon compte</a></div>
    </div>
</nav>