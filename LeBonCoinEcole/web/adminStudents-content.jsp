<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<section id="toFade" class="container-form" >
    <%@include file="form-create-account.jsp" %>
</section>

<section id="display-info">
    <h1>Liste des étudiants</h1>
    <button id="btn-add" onclick="show(toFade)" class="button">+ Ajouter un étudiant</button>
    
    <c:choose>
        <c:when test="${nbPages > 1}">
            <nav class="paginations">
                <c:if test="${param.page > 1}">
                    <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/students?page=1"><<</a></div>
                    <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/students?page=${param.page - 1}"><</a></div>
                </c:if>

                <c:forEach var="i" begin="1" end="${nbPages}">
                    <c:choose>
                        <c:when test="${i == param.page}">
                            <div class="page selected">${i}</div>
                        </c:when>
                        <c:when test="${i != param.page}">
                            <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/students?page=${i}">${i}</a></div>
                        </c:when>
                    </c:choose>
                </c:forEach>
                            
                <c:if test="${param.page < nbPages}">
                    <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/students?page=${param.page + 1}">></a></div>
                    <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/students?page=${nbPages}">>></a></div>
                </c:if>
            </nav>
        </c:when>
    </c:choose>    
    
    <form method="POST" action="${pageContext.request.contextPath}/admin/students">
        <input type="hidden" name="action" value="delete"/> 
        <table class="listing">
            <tr>
                <th>Avatar</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Login</th>
                <th>School</th>
                <th>Email</th>                
                <th>Supprimer</th>
            </tr>

            <c:forEach var="student" items="${allStudents}">
                <tr>
                    <td><img class="image-avatar" src="${student.url}" /></td>
                    <td>${student.lastname}</td>
                    <td>${student.firstname}</td>
                    <td>${student.username}</td>
                    <td>${student.school.name}</td>
                    <td>${student.emails[0]}</td>
                    <td>
                        <button type="submit" class="button" name="username" value="${student.username}">X</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
        
    <c:choose>
        <c:when test="${nbPages > 1}">
            <nav class="paginations">
                <c:if test="${param.page > 1}">
                    <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/students?page=1"><<</a></div>
                    <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/students?page=${param.page - 1}"><</a></div>
                </c:if>

                <c:forEach var="i" begin="1" end="${nbPages}">
                    <c:choose>
                        <c:when test="${i == param.page}">
                            <div class="page selected">${i}</div>
                        </c:when>
                        <c:when test="${i != param.page}">
                            <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/students?page=${i}">${i}</a></div>
                            </c:when>

                    </c:choose>
                </c:forEach>
                <c:if test="${param.page < nbPages}">
                    <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/students?page=${param.page + 1}">></a></div>
                    <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/students?page=${nbPages}">>></a></div>
                </c:if>
            </nav>
        </c:when>
        
        <c:when test="${nbPages <= 0}">
            <section class="no-result">
                <p>
                    Aucun étudiants dans la base de données
                </p>
            </section>
        </c:when>
    </c:choose>
</section>

<script>
    function show(element) {
        element.style.display = 'block';
    }
    
    function hide (elements) {
        elements = elements.length ? elements : [elements];
        for (var index = 0; index < elements.length; index++) {
            elements[index].style.display = 'none';
        }
    }
    
    hide(document.querySelector('#toFade'));
</script>