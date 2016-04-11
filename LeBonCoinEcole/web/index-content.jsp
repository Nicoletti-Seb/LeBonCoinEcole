<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

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
                    <a href="annonce?id=${a.id}" class="item clickable" >
                        <img src="//static.leboncoin.fr/img/no-picture.png"/>
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