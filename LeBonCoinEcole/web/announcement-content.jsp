<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>


<section>
    <c:choose>
        <c:when test="${not empty error}">
            <p>
                Aucune annonce n'a été trouvé...
            </p>
        </c:when>
        <c:when test="${not empty announcement}">
                ${announcement.title}
        </c:when>
    </c:choose>
</section>