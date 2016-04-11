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
    <form id="form-AddAnnouncement" action="" method="POST">

        <h1>Deposer une annonce</h1>

        <h2>Categories</h2>
        <aside class="categories">
            <c:forEach var="c" items="${categories}">
                <input id="${c.name}" class="hidden" type="checkbox" name="categories"  value="${c.name}" />
                <label class="category" for="${c.name}">${c.name}</label>
            </c:forEach>
        </aside>

        <h2>Titre</h2>
        <input name="title" type="text" placeholder="Titre" required/>

        <h2>Description</h2>
        <textarea name="description" required></textarea>

        <h2>Prix</h2>
        <input type="number" name="price" min="1" step="0.01" required/>

        <h2>Image</h2>
        <input type="file" name="image" />
        <input type="hidden" name="action" value="create" />
        <button type="submit" class="button">Ajouter</button>

    </form>
</section>
