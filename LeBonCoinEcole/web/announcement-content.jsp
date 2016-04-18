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
            
            <c:set var="student" value="${announcement.student}"/>
            <c:set var="school" value="${student.school}"/>

            <article class="announcement-container">
                <img src="//static.leboncoin.fr/img/no-picture.png" /> 
                <header class="categories">
                    <c:forEach var="c" items="${announcement.categories}">
                        <div  class="category">
                            ${c.name}
                        </div>
                    </c:forEach>
                </header>
                
                <section class="item">
                    <div class="description">
                        <h2 class="title"> ${announcement.title} </h2>
                        <p class="desc">${announcement.description}</p>
                        <p class="price">${announcement.price} &euro;</p>
                        <p class="date">${announcement.startDate}</p>
                    </div>
                </section>
            </article>
            <aside class="contact-container">
                <h1>${student.username}</h1>
                <h2>École:</h2>${school.name}
                <h2>Tels:</h2>
                    <ul>
                        <c:forEach var="phone" items="${student.phoneNumbers}">
                            <li>${phone.number}</li>
                        </c:forEach>
                    </ul>
                <h2>Emails:</h2>
                    <ul>
                        <c:forEach var="email" items="${student.emails}">
                            <li>${email.email}</li>
                        </c:forEach>
                    </ul>
                
                <a href="${school.link}">Détails</a>
            </aside>

        </c:when>
    </c:choose>
</section>