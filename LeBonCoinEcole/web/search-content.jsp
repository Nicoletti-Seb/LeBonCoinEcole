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
        <header>
            <form id="search-form" method="GET">
                <select name="school">
                    <option  selected="selected" value="">Aucune école</option>
                    <c:forEach var="s" items="${schools}">
                        <option>${s.name}</option>
                    </c:forEach>
                </select>
                <input name="areaCode" type="text" maxlength="5" placeholder="Departement - Ex 06600" />

                <label for="minPrice" >Prix min :</label>
                <input name="minPrice" id="minPrice" type="numeric" min="0" />

                <label for="maxPrice" >Prix max :</label>
                <input name="maxPrice" id="maxPrice" type="numeric" min="0" max="999" />

                <input name ="key" type="text" placeholder="Mots clés" /> 

                <button class="button" type="submit">Envoyer</button>
            </form>
        </header>

        <c:choose>
            <c:when test="${nbPages >= 1}">
                <section>
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
                        <a href="annonce?id=${a.id}" class="item clickable" >
                            <img src="//static.leboncoin.fr/img/no-picture.png" /> 
                            <div class="description">
                                <h2 class="title"> ${a.title} </h2>
                                <p class="desc">${a.description}</p>
                                <p class="price">${a.price} &euro;</p>
                                <p class="date">${a.startDate}</p>
                            </div>
                        </a>
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
            </c:when>

            <c:when test="${nbPages <= 0}">
                <section class="no-result">
                    <p>
                        Aucun résultat n'a été trouvé pour cette recherche...
                    </p>
                </section>
            </c:when>
        </c:choose>
    </section>
</article>