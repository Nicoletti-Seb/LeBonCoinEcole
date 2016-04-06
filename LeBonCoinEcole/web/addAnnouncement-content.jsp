<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<article>
    <form id="form-AddAnnouncement" action="" method="POST">
        
        <h1>Deposer une annonce</h1>
        
        <h2>Localisation</h2>
        <select>
            <option selected="selected" >Aucune école</option>
            <c:forEach var="s" items="${schools}">
                <option>${s.name}</option>
            </c:forEach>
        </select>
        
        <h2>Categories</h2>
         <aside class="categories">
            <c:forEach var="c" items="${categories}">
                <div class="category">
                    ${c.name}
                </div>
            </c:forEach>
        </aside>
        
        <h2>Titre</h2>
        <input type="text" placeholder="Titre"/>
        
        <h2>Description</h2>
        <textarea></textarea>
        
        <h2>Image</h2>
        <input type="file"/>
        <button type="submit" class="button">Ajouter</button>
    </form>
</article>
