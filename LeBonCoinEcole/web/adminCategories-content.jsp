<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<section class="container-form" >
    <form id="compte-form" method="POST" action="${pageContext.request.contextPath}/admin/categories">
        <h1>Création de catégorie</h1>

        <h2>Nom *</h2>
        <input type="text" name="name" placeholder="Nom" required=""/>

        <input type="hidden" name="action" value="create"/>
        <button type="submit" class="button">Valider</button>
    </form>
</section>

<section>
    <form method="POST" action="${pageContext.request.contextPath}/admin/categories">
        <input type="hidden" name="action" value="delete"/> 
        <table border="1">
            <tr>
                <th>Title</th>
                <th>Supprimer</th>
            </tr>

            <c:forEach var="category" items="${allCategories}" varStatus="status">
                <!--<tr class="${status.index%2==0 ? 'alt' : ''}">-->
                <tr>
                    <td>
                        ${category.name}
                    </td>
                    <td>
                        <button type="submit" class="button" name="name" value="${category.name}">X</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</section>
            