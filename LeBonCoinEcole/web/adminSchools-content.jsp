<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<section class="container-form" >
    <form id="compte-form" method="POST" action="${pageContext.request.contextPath}/admin/schools">
        <h1>Création d'école</h1>

        <h2>Nom *</h2>
        <input type="text" name="name" placeholder="Nom" required=""/>
        
        <h2>Lien internet</h2>
        <input type="text" name="link" placeholder="Lien internet"/>

        <h2>Adresse *</h2>
        <div class="multiblock">
            <input class="largeur-300" type="text" name="address" placeholder="Adresse" required=""/></br>
            <input class="largeur-100" type="text" name="areaCode" placeholder="Code Postal" required=""/>
            <input class="largeur-100" type="text" name="city" placeholder="Ville" required=""/>
            <input class="largeur-100" type="text" name="country" placeholder="Pays" required=""/>
        </div>
        
        <input type="hidden" name="action" value="create"/>
        <button type="submit" class="button">Valider</button>
    </form>
</section>

<section>
    <form method="POST" action="${pageContext.request.contextPath}/admin/schools">
        <input type="hidden" name="action" value="delete"/> 
        <table border="1">
            <tr>
                <th>Nom</th>
                <th>Adresse</th>
                <th>Lien</th>
                <th>Modifier</th>
                <th>Supprimer</th>
            </tr>

            <c:forEach var="school" items="${allSchools}">
                <tr>
                    <td>${school.name}</td>
                    <td>${school.address.name} ${school.address.areaCode} ${school.address.city} - ${school.address.country}</td>
                    <td><a href="http://${school.link}" target="_blank">Lien</a></td>
                    <td><a href="${pageContext.request.contextPath}/admin/schools?action=edit&id=${school.id}">Modifier</a></td>
                    <td>
                        <button type="submit" class="button" name="id" value="${school.id}">X</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</section>