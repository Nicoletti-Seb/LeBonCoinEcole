<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<section id="display-info">
    <h1>Liste des annonces</h1>
    <!--<button id="btn-add" class="button">+ Ajouter une annonce</button>-->
    
    <c:choose>
        <c:when test="${nbPages > 1}">
            <nav class="paginations">
                <c:if test="${param.page > 1}">
                    <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements?page=1"><<</a></div>
                    <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements?page=${param.page - 1}"><</a></div>
                </c:if>

                <c:forEach var="i" begin="1" end="${nbPages}">
                    <c:choose>
                        <c:when test="${i == param.page}">
                            <div class="page selected">${i}</div>
                        </c:when>
                        <c:when test="${i != param.page}">
                            <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements?page=${i}">${i}</a></div>
                        </c:when>
                    </c:choose>
                </c:forEach>

                <c:if test="${param.page < nbPages}">
                    <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements?page=${param.page + 1}">></a></div>
                    <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements?page=${nbPages}">>></a></div>
                </c:if>
            </nav>
        </c:when>
    </c:choose>
    
    <form method="POST" action="${pageContext.request.contextPath}/admin/announcements">
        <input type="hidden" name="action" value="delete"/> 
        <table class="listing">
            <tr>
                <th style="width: 70px">Image</th>
                <th>Titre</th>
                <th>Description</th>
                <th style="width: 150px;">Prix</th>
                <th style="width: 150px;">Date</th>
                <th style="width: 150px;">Supprimer</th>
            </tr>

            <c:forEach var="announcement" items="${allAnnouncements}">
                <tr>
                    <td><img class="image-avatar" src="${announcement.url}"</td>
                    <td>${announcement.title}</td>
                    <td>${announcement.description}</td>
                    <td>${announcement.price} &euro;</td>
                    <td>${announcement.startDate}</td>
                    <td>
                        <button type="submit" class="button" name="id" value="${announcement.id}">X</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
        
    <c:choose>
        <c:when test="${nbPages > 1}">
            <nav class="paginations">
                <c:if test="${param.page > 1}">
                    <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements?page=1"><<</a></div>
                    <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements?page=${param.page - 1}"><</a></div>
                </c:if>

                <c:forEach var="i" begin="1" end="${nbPages}">
                    <c:choose>
                        <c:when test="${i == param.page}">
                            <div class="page selected">${i}</div>
                        </c:when>
                        <c:when test="${i != param.page}">
                            <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements?page=${i}">${i}</a></div>
                        </c:when>
                    </c:choose>
                </c:forEach>

                <c:if test="${param.page < nbPages}">
                    <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements?page=${param.page + 1}">></a></div>
                    <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/announcements?page=${nbPages}">>></a></div>
                </c:if>
            </nav>
        </c:when>
        
        <c:when test="${nbPages <= 0}">
            <section class="no-result">
                <p>
                    Aucune annonces dans la base de données
                </p>
            </section>
        </c:when>
    </c:choose>
</section>