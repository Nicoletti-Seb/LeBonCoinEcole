<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<article class="myAnnouncements-container">
    
    <header>
        <c:choose>
            <c:when test="${param.type}">
                <h1 class="title">Mes recherches</h1>
            </c:when>
            <c:otherwise>
                <h1 class="title">Mes annonces</h1>
            </c:otherwise>
        </c:choose>
    </header>
    
     <c:choose>
        <c:when test="${nbPages >= 1}">
            <form id="myAnnouncement-form" method="GET"></form>
            <form id="removeAnnouncement-form" method="POST">
                <input type="hidden" name="action" value="remove"/>
                <c:if test="${param.type}">
                    <input type="hidden" name="type" value="true" />
                </c:if>
            </form>
            <!-- Pagination top -->
            <nav class="paginations">
                <c:if test="${param.page > 1}">
                    <input id="start" type="submit" class="hidden" name="page" form="myAnnouncement-form" value="1">
                    <label class="page" for="start" ><<</label>

                    <input id="previous" type="submit" class="hidden" name="page" form="myAnnouncement-form" value="${param.page - 1}">
                    <label class="page" for="previous" ><</label>
                </c:if>

                <c:forEach var="i" begin="1" end="${nbPages}">
                    <c:choose>
                        <c:when test="${i == param.page}">
                            <label class="page selected">${i}</label>
                        </c:when>
                        <c:otherwise>
                            <input id="page-${i}" type="submit" class="hidden" name="page" form="myAnnouncement-form" value="${i}">
                            <label class="page" for="page-${i}" >${i}</label>
                        </c:otherwise>

                    </c:choose>
                </c:forEach>
                <c:if test="${param.page < nbPages}">                            
                    <input id="next" type="submit" class="hidden" name="page" form="myAnnouncement-form" value="${param.page + 1}">
                    <label class="page" for="next" >></label>

                    <input id="end" type="submit" class="hidden" name="page" form="myAnnouncement-form" value="${nbPages}">
                    <label class="page" for="end" >>></label>
                </c:if>
            </nav>
            <!-- END Pagination top -->

            <!-- Announcements  -->
            <section class="items">
                <c:forEach var="a" items="${announcements}">
                    <a href="addAnnouncement?action=update&id=${a.id}" class="item clickable" >
                        <img src="${a.url}" /> 
                        <div class="description">
                            <h2 class="title"> ${a.title} </h2>
                            <p class="desc">${a.description}</p>
                            <p class="price">${a.price} &euro;</p>
                            <p class="date">${a.startDate}</p>
                            <button class="button button_remove" type="submit" name="id" value="${a.id}" form="removeAnnouncement-form" >
                                x
                            </button>
                        </div>
                    </a>
                    
                </c:forEach>
            </section>
            <!-- END Announcements  -->

            <!-- Pagination bottom -->
            <nav class="paginations">
                <c:if test="${param.page > 1}">
                    <input id="start" type="submit" class="hidden" name="page" form="myAnnouncement-form" value="1">
                    <label class="page" for="start" ><<</label>

                    <input id="previous" type="submit" class="hidden" name="page" form="myAnnouncement-form" value="${param.page - 1}">
                    <label class="page" for="previous" ><</label>
                </c:if>

                <c:forEach var="i" begin="1" end="${nbPages}">
                    <c:choose>
                        <c:when test="${i == param.page}">
                            <label class="page selected">${i}</label>
                        </c:when>
                        <c:otherwise>
                            <input id="page-${i}" type="submit" class="hidden" name="page" form="myAnnouncement-form" value="${i}">
                            <label class="page" for="page-${i}" >${i}</label>
                        </c:otherwise>

                    </c:choose>
                </c:forEach>
                <c:if test="${param.page < nbPages}">                            
                    <input id="next" type="submit" class="hidden" name="page" form="myAnnouncement-form" value="${param.page + 1}">
                    <label class="page" for="next" >></label>

                    <input id="end" type="submit" class="hidden" name="page" form="myAnnouncement-form" value="${nbPages}">
                    <label class="page" for="end" >>></label>
                </c:if>
            </nav>
            <!-- END Pagination bottom -->
        </c:when>

        <c:otherwise>
            <section class="no-result">
                <p>
                    Vous n'avez actuellement aucune annonce...
                </p>
            </section>
        </c:otherwise>
    </c:choose>
</article>