<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<div class="search-container">
    <aside class="categories">
        <c:forEach var="c" items="${categories}">
            <div class="category">
                ${c.name}
            </div>
        </c:forEach>
    </aside>

    <section class="items">
        
        <form action="" method="GET">
            <select>
                <option selected="selected" >Aucune école</option>
                <c:forEach var="s" items="${schools}">
                    <option>${s.name}</option>
                </c:forEach>
            </select>
            <input type="text" maxlength="5" placeholder="Departement - Ex 06600" />
            <input type="text" placeholder="Mots clés" /> 
            <button class="button" type="submit">Envoyer</button>
        </form>
        
        
        <c:forEach var="a" items="${announcements}">
            <article class="item">
                <img src="//static.leboncoin.fr/img/no-picture.png" /> 
                <div class="description">
                    <h2 class="title"> ${a.title} </h2>
                    <p class="desc">${a.description}</p>
                    <p class="price">${a.price} &euro;</p>
                    <p class="date">${a.startDate} hier</p>
                </div>
            </article>
        </c:forEach>
    </section>
</div>