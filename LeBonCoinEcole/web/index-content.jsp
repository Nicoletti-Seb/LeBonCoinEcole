<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<header>
    <c:if test="${param.alert == 'error'}">
        <div class="info">
            La connexion a échoué !
        </div>
    </c:if>
    
    <c:if test="${param.alert == 'deconnexion'}">
        <div class="info">
            Vous êtes bien déconnecté. A bientôt !
        </div>
    </c:if>
</header>

<article class="search-container">
    <aside>
        <header>
            <h2>Catégories</h2>
        </header>
        <%@include file="form-categories-selector.jsp" %>
    </aside>

    <section class="items">
        <c:choose>
            <c:when test="${not empty announcements}">
                <c:forEach var="a" items="${announcements}">
                    <a href="announcement?id=${a.id}" class="item clickable" >
                        <img src="${a.url}"/>
                        <div class="description">
                            <h2 class="title"> ${a.title} </h2>
                            <p class="desc">${a.description}</p>
                            <p class="price">${a.price} &euro;</p>
                            <p class="date">${a.startDate}</p>
                        </div>
                    </a>
                </c:forEach>
            </c:when>

            <c:when test="${empty announcements}">
                <section class="no-result">
                    <p>
                        Aucun résultat n'a été trouvé pour cette recherche...
                    </p>
                </section>
            </c:when>
        </c:choose>
    </section>
</article>