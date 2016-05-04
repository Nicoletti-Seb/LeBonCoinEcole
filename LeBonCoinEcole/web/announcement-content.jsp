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
                <img src="${announcement.url}" /> 
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
                        <p class="allDescription">${announcement.description}</p>
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
                
                <a href="http://${school.link}"  target="_blank">Détails</a>
                
                <div id="googleMap">
                    
                </div>
                
                <script src="http://maps.googleapis.com/maps/api/js"></script>
                <script>
                    var myCenter=new google.maps.LatLng(${school.address.latitude}, ${school.address.longitude});
                    
                    function initialize() {
                        var mapProp = {
                            center:myCenter,
                            zoom:15,
                            mapTypeId:google.maps.MapTypeId.ROADMAP
                        };
                        var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);

                        var marker=new google.maps.Marker({
                            position:myCenter,
                        });

                        marker.setMap(map);
                    }
                    google.maps.event.addDomListener(window, 'load', initialize);
                </script>
            </aside>

        </c:when>
    </c:choose>
</section>