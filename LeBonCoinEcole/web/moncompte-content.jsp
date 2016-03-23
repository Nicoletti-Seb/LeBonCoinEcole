<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

Mon Compte

<c:if test="${requestScope['action'] == 'formCreationComtpe'}"> 
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
                    ${s.name} (${s.address.toString()})
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
displayInfoCompte
</c:if>
