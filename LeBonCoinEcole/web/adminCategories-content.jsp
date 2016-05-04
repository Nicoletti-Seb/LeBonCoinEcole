<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<header>
    <c:if test="${param['action'] == 'create'}">
        <c:if test="${param.success}">
            <div class="success">
                La catégorie ${param.name} à bien été ajoutée !
            </div>
        </c:if>

        <c:if test="${param.error}">
            <div class="info">
                La catégorie ${param.name} n'a pas pu être ajoutée...<br>
                Elle existe surement déjà...
            </div>
        </c:if>
    </c:if>
    
    <c:if test="${param['action'] == 'delete'}">
        <c:if test="${param.success}">
            <div class="success">
                La catégorie ${param.name} à bien été supprimée !
            </div>
        </c:if>

        <c:if test="${param.error}">
            <div class="info">
                La catégorie ${param.name} n'a pas pu être supprimée...<br>
                Il y a encore des annonces de cette catégorie...                
            </div>
        </c:if>
    </c:if>
</header>

<section id="list-cat-left">
    <h1>Liste des catégories</h1>
    <form method="POST" action="${pageContext.request.contextPath}/admin/categories">
        <input type="hidden" name="action" value="delete"/> 
        <table>
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
                        <button type="submit" class="button btn-danger" name="name" value="${category.name}">X</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</section>

<section id="form-cat-right">
    <form method="POST" action="${pageContext.request.contextPath}/admin/categories">
        <h1>Création de catégorie</h1>

        <h2>Nom *</h2>
        <input type="text" name="name" placeholder="Nom" required="" maxlength="25"/>

        <input type="hidden" name="action" value="create"/>
        <button type="submit" class="button">Valider</button>
    </form>
</section>