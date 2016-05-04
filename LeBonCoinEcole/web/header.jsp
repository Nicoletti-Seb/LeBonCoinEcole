<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<header>
    
    <div class="container-title" >
        <div class="title">
            <a class="clickable" href="${pageContext.request.contextPath}/index">leboncoin - universitaire</a>
        </div>
        
        <div class="login">
            <c:choose>
                <c:when test="${!empty sessionScope.student}">
                    <form method="POST" action="${pageContext.request.contextPath}/connexion">
                        <input type="hidden" name="action" value="deconnexion"/>
                        <button class="button" type="submit">D�connexion</button>
                    </form>
                </c:when>
                <c:when test="${!empty sessionScope.administrator}">
                    <form method="POST" action="${pageContext.request.contextPath}/connexion">
                        <input type="hidden" name="action" value="deconnexion"/>
                        <button class="button" type="submit">D�connexion</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form method="POST" action="${pageContext.request.contextPath}/connexion">
                        <input type="text" placeholder="Identifiant" name="username"/>
                        <input type="password" placeholder="Mot de passe" name="password"/>
                        <input type="hidden" name="action" value="connexion"/>
                        <button class="button" type="submit">Ok</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</header>
<nav>
    <div class="container-menu">
        <c:choose>
            <c:when test="${!empty sessionScope.student}">
                <div class="menu"><a class="clickable" href="search">Rechercher</a></div>
                <div class="menu"><a class="clickable" href="addAnnouncement">Deposer une annonce</a></div>
                <div class="menu"><a class="clickable" href="myAnnouncements">Mes annonces</a></div>
                <div class="menu"><a class="clickable" href="account">Mon compte</a></div>   
            </c:when>
            <c:when test="${!empty sessionScope.administrator}">
                <div class="menu"><a class="clickable" href="${pageContext.request.contextPath}/search">Rechercher</a></div>
                <div class="menu"><a class="clickable" href="${pageContext.request.contextPath}/admin/categories">Cat�gories</a></div>
                <div class="menu"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools">Ecoles</a></div>
                <div class="menu"><a class="clickable" href="${pageContext.request.contextPath}/admin/students">Etudiants</a></div>
                <div class="menu"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements">Annonces</a></div>
            </c:when>
            <c:otherwise>
                <div class="menu"><a class="clickable" href="search">Rechercher</a></div>
                <div class="menu"><a class="clickable" href="account">Cr�er mon compte</a></div>
            </c:otherwise>
        </c:choose>
    </div>
</nav>