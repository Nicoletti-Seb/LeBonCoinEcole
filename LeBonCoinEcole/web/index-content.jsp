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
        <c:forEach var="a" items="${announcements}">
            <article class="item">
                <img src="//static.leboncoin.fr/img/no-picture.png" /> 
                <div class="description">
                    <h2 class="title"> ${a.title} </h2>
                    <p class="desc">${a.description}</p>
                    <p class="price">${a.price} &euro;</p>
                    <p class="date">${a.startDate} hier</p>
                </div>
            </article>
        </c:forEach>
    </section>
</article>