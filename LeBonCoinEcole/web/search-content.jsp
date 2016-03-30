<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<div class="search-container">
    <aside class="categories">
        <c:forEach var="c" items="${categories}">
            <div class="category">
                ${c.name}
            </div>
        </c:forEach>
    </aside>

    <section class="items">

        <form action="" method="GET">
            <select>
                <option selected="selected" >Aucune école</option>
                <c:forEach var="s" items="${schools}">
                    <option>${s.name}</option>
                </c:forEach>
            </select>
            <input type="text" maxlength="5" placeholder="Departement - Ex 06600" />
            <input type="text" placeholder="Mots clés" /> 
            <button class="button" type="submit">Envoyer</button>
        </form>


        <nav class="paginations">
            <c:if test="${param.page > 1}">
                <div  class="page"><a class="clickable" href="search?page=1"><<</a></div>
                <div class="page"><a class="clickable" href="search?page=${param.page - 1}"><</a></div>
            </c:if>

            <c:forEach var="i" begin="1" end="${nbPages}">
                <c:choose>
                    <c:when test="${i == param.page}">
                        <div class="page selected">${i}</div>
                    </c:when>
                    <c:when test="${i != param.page}">
                        <div class="page"><a class="clickable" href="search?page=${i}">${i}</a></div>
                    </c:when>

                </c:choose>
            </c:forEach>
            <c:if test="${param.page < nbPages}">
                <div class="page"><a class="clickable" href="search?page=${param.page + 1}">></a></div>
                <div  class="page"><a class="clickable" href="search?page=${nbPages}">>></a></div>
            </c:if>
        </nav>

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

        <nav class="paginations">
            <c:if test="${param.page > 1}">
                <div  class="page"><a class="clickable" href="search?page=1"><<</a></div>
                <div class="page"><a class="clickable" href="search?page=${param.page - 1}"><</a></div>
            </c:if>

            <c:forEach var="i" begin="1" end="${nbPages}">
                <c:choose>
                    <c:when test="${i == param.page}">
                        <div class="page selected">${i}</div>
                    </c:when>
                    <c:when test="${i != param.page}">
                        <div class="page"><a class="clickable" href="search?page=${i}">${i}</a></div>
                    </c:when>

                </c:choose>
            </c:forEach>
            <c:if test="${param.page < nbPages}">
                <div class="page"><a class="clickable" href="search?page=${param.page + 1}">></a></div>
                <div  class="page"><a class="clickable" href="search?page=${nbPages}">>></a></div>
            </c:if>
        </nav>
    </section>
</div>