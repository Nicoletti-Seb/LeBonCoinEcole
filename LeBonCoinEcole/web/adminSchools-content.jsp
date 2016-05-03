<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<c:if test="${param.action == 'edit'}">
    
    <c:set var="s" value="${requestScope['school']}"/>
    <section class="container-form" >
        <form id="compte-form" method="POST" action="${pageContext.request.contextPath}/admin/schools">
            <h1>Modification de l'�c�le "${s.name}"</h1>

            <h2>Nom *</h2>
            <input type="text" name="name" placeholder="Nom" required="" value="${s.name}" style="width: 500px"/>

            <h2>Lien internet</h2>
            <input type="text" name="link" placeholder="Lien internet" value="${s.link}" style="width: 500px"/>

            <h2>Adresse *</h2>
            <div class="multiblock">
                <input class="largeur-300" type="text" name="address" placeholder="Adresse" required="" value="${s.address.name}"/></br>
                <input class="largeur-100" type="text" name="areaCode" placeholder="Code Postal" required="" value="${s.address.areaCode}"/>
                <input class="largeur-100" type="text" name="city" placeholder="Ville" required="" value="${s.address.city}"/>
                <input class="largeur-100" type="text" name="country" placeholder="Pays" required="" value="${s.address.country}"/>
            </div>

            <input type="hidden" name="id" value="${s.id}"/>
            <input type="hidden" name="action" value="update"/>
            <button type="submit" class="button">Valider</button>
        </form>
    </section>
</c:if>

<c:if test="${param.action != 'edit'}">
    <section id="toFade" class="container-form" >
        <form id="compte-form" method="POST" action="${pageContext.request.contextPath}/admin/schools">
            <h1>Cr�ation d'�cole</h1>

            <h2>Nom *</h2>
            <input type="text" name="name" placeholder="Nom" required="" style="width: 500px"/>

            <h2>Lien internet</h2>
            <input type="text" name="link" placeholder="Lien internet" style="width: 500px"/>

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

    <section id="table-school">
        <h1>Liste des �coles</h1>
        <button id="btn-add" onclick="show(toFade)" class="button">+ Ajouter une �cole</button>

        <c:choose>
            <c:when test="${nbPages > 1}">
                <nav class="paginations">
                    <c:if test="${param.page > 1}">
                        <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools?page=1"><<</a></div>
                        <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools?page=${param.page - 1}"><</a></div>
                    </c:if>

                    <c:forEach var="i" begin="1" end="${nbPages}">
                        <c:choose>
                            <c:when test="${i == param.page}">
                                <div class="page selected">${i}</div>
                            </c:when>
                            <c:when test="${i != param.page}">
                                <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools?page=${i}">${i}</a></div>
                            </c:when>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${param.page < nbPages}">
                        <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools?page=${param.page + 1}">></a></div>
                        <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools?page=${nbPages}">>></a></div>
                    </c:if>
                </nav>
            </c:when>
        </c:choose>

        <form method="POST" action="${pageContext.request.contextPath}/admin/schools">
            <input type="hidden" name="action" value="delete"/> 
            <table>
                <tr>
                    <th>Nom</th>
                    <th>Adresse</th>
                    <th>Lien</th>
                    <th>NbEleve</th>
                    <th>Modifier</th>
                    <th>Supprimer</th>
                </tr>

                <c:forEach var="school" items="${allSchools}">
                    <tr>
                        <td>${school.name}</td>
                        <td>${school.address.name} ${school.address.areaCode} ${school.address.city} - ${school.address.country}</td>
                        <td><a href="http://${school.link}" target="_blank">Lien</a></td>
                        <td>${school.students}</td>
                        <td><a href="${pageContext.request.contextPath}/admin/schools?action=edit&id=${school.id}">Modifier</a></td>
                        <td>
                            <button type="submit" class="button" name="id" value="${school.id}">X</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>

        <c:choose>
            <c:when test="${nbPages > 1}">
                <nav class="paginations">
                    <c:if test="${param.page > 1}">
                        <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools?page=1"><<</a></div>
                        <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools?page=${param.page - 1}"><</a></div>
                    </c:if>

                    <c:forEach var="i" begin="1" end="${nbPages}">
                        <c:choose>
                            <c:when test="${i == param.page}">
                                <div class="page selected">${i}</div>
                            </c:when>
                            <c:when test="${i != param.page}">
                                <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools?page=${i}">${i}</a></div>
                                </c:when>

                        </c:choose>
                    </c:forEach>
                    <c:if test="${param.page < nbPages}">
                        <div class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools?page=${param.page + 1}">></a></div>
                        <div  class="page"><a class="clickable" href="${pageContext.request.contextPath}/admin/schools?page=${nbPages}">>></a></div>
                    </c:if>
                </nav>
            </c:when>

            <c:when test="${nbPages <= 0}">
                <section class="no-result">
                    <p>
                        Aucune �coles dans la base de donn�es
                    </p>
                </section>
            </c:when>
        </c:choose>
</section>
</c:if>
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