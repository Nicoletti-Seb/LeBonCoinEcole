<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<section class="container-form">
    <header>
        <c:if test="${param.success}">
            <div class="success">
                L'annonce ${param.title} à bien été ajouté !
            </div>
        </c:if>
        
        <c:if test="${param.error}">
            <div class="info">
                L'annonce ${param.title} n'a pas pu être ajouté...
            </div>
        </c:if>
    </header>
    <form id="form-AddAnnouncement" method="POST"  enctype="multipart/form-data">
        <c:choose>
            <c:when test="${param.type}">
                <input type="hidden" name="type" value="true" />
                <h1>Deposer une recherche</h1>
            </c:when>
            <c:otherwise>
                <h1>Deposer une annonce</h1>
            </c:otherwise>
        </c:choose>

        <h2>Categories</h2>
        <aside class="categories">
            <c:forEach var="c" items="${categories}">
                
                <c:set var="contains" value="false"/>
                <c:forEach var="e" items="${vf.categories}">
                    <c:if test="${c.name == e.name}">
                        <c:set var="contains" value="true"/>
                    </c:if>
                </c:forEach>
                
                <c:choose>
                    <c:when test="${contains}">
                        <input id="${c.name}" class="hidden" type="checkbox" name="categories"  value="${c.name}" checked/>
                    </c:when>

                    <c:otherwise>
                        <input id="${c.name}" class="hidden" type="checkbox" name="categories"  value="${c.name}" />    
                    </c:otherwise>
                </c:choose>
                <label class="category" for="${c.name}">${c.name}</label>
                
            </c:forEach>
        </aside>

        <h2>Titre</h2>
        <input name="title" type="text" placeholder="Titre" value="${vf.title}" required/>

        <h2>Description</h2>
        <textarea name="description" required>${vf.description}</textarea>

        <h2>Prix</h2>
        <input type="number" name="price" min="0" step="0.01"  value="${vf.price}" required/>

        <h2>Image</h2>
        <input type="file" name="image" value="${vf.image}" />
        
        <c:if test="${param.type}">
            <input type="hidden" name="type" value="true" />
        </c:if>
        
        <c:choose>
            <c:when test="${param.action == 'update'}" >
                <input type="hidden" name="action" value="update" />
                <input type="hidden" name="id" value="${param.id}" />
                <button type="submit" class="button">Modifier</button>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="action" value="create" />
                <button type="submit" class="button">Ajouter</button>
            </c:otherwise>
        </c:choose>


    </form>
</section>
