<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<h1>Les étudiants</h1>

<section>
    <form method="POST" action="${pageContext.request.contextPath}/admin/students">
        <input type="hidden" name="action" value="delete"/> 
        <table border="1">
            <tr>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Login</th>
                <th>School</th>
                <th>Email</th>                
                <th>Supprimer</th>
            </tr>

            <c:forEach var="student" items="${allStudents}">
                <tr>
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
</section>