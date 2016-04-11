<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<section class="items">    
    Mes annonces : ${announcements}
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