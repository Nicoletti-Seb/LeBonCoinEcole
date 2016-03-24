<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<c:if test="${requestScope['action'] == 'formCreationComtpe'}"> 
    
    <h1>Création de compte</h1>
    
<div id="formCreationComtpe">
    <!-- Formulaire de création de compte
    - lastname, firstname, username, password
    - school
    - address (number, name, areaCode, city, country)
    - telephone
    - email
    - image
    -->
    <form method="POST" action="/MonCompte">
        lastname : <input type="text" name="lastname"/><br>
        firstname : <input type="text" name="firstname"/><br>
        username : <input type="text" name="username"/><br>
        password : <input type="password" name="password"/><br>
        
        address : <br>
        number : <input type="text" name="password"/><br>
        rue : <input type="text" name="name"/><br>
        areaCode : <input type="text" name="areaCode"/><br>
        city : <input type="text" name="city"/><br>
        country : <input type="text" name="country"/><br>
        
        school : 
        <select>
            <c:forEach var="s" items="${requestScope['allSchools']}">
                <option>
                    ${s.toString()}
                </option>
            </c:forEach>
        </select>
        <br>
        
        telephone : <input type="text" name="telephone"/><br>
        email : <input type="email" name="email"/><br>
        image :
    </form>
</div>
</c:if>

<c:if test="${requestScope['action'] == 'displayInfoCompte'}"> 

    <h1>Mes informations</h1>
    
    <c:set var="s" value="${requestScope['student']}"/> 
    lastname : ${s.lastname}<br>
    firstname : ${s.firstname}<br>
    username : ${s.username}<br>
    <!--password : ${s.password}<br>-->

    address : <br>
    <c:forEach var="addr" items="${s.address}">
        ${addr}<br>
    </c:forEach>

    school : ${s.school}<br>

    telephone : <br>
    <c:forEach var="phone" items="${s.phoneNumbers}">
        ${phone}<br>
    </c:forEach>
        
    email : <br>
    <c:forEach var="email" items="${s.emails}">
        ${email}<br>
    </c:forEach>
    image :
</c:if>
