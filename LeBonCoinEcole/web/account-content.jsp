<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<!-- Formulaire de creation de compte -->
<c:if test="${requestScope['action'] == 'formCreationComtpe'}"> 

    <section class="container-form" >
        <%@include file="form-create-account.jsp" %>
    </section>
</c:if>

<!-- Affichage du compte -->
<c:if test="${requestScope['action'] == 'displayInfoCompte'}"> 

    <section class="container-form" id="displayCompte">
        <form method="GET" action="account">
            <h1>Mes informations</h1>

            <c:set var="s" value="${requestScope['student']}"/> 
            <img src="${s.url}"/>
            
            <h2>Nom</h2>
            ${s.lastname}
            
            <h2>Prénom</h2>
            ${s.firstname}
            
            <h2>Login</h2>
            ${s.username}

            <h2>Votre école</h2>
            <a href="http://${s.school.link}" target="_blank">${s.school.name}</a><br>
            ${s.school.address.toString()}

            <h2>Téléphone</h2>
            <ul>
                <c:forEach var="phone" items="${s.phoneNumbers}">
                    <li>${phone}</li>
                </c:forEach>
            </ul>

            <h2>Adresse email</h2>
            <ul>
                <c:forEach var="email" items="${s.emails}">
                    <li>${email}</li>
                </c:forEach>
            </ul>
        
            <input type="hidden" name="action" value="modify"/>
            <button type="submit" class="button">Modifier</button>
        </form>
    </section>
</c:if>

<!-- Formulaire de modification de compte -->
<c:if test="${requestScope['action'] == 'formModifierComtpe'}"> 

    <section class="container-form" id="displayCompte">
        <form id="compte-form" method="POST" action="account" enctype="multipart/form-data" >
            <h1>Modifier mes informations</h1>
            
            <c:set var="s" value="${requestScope['student']}"/>
            <img src="${s.url}"/>
            
            <h2>Nom *</h2>
            <input type="text" name="lastname" value="${s.lastname}" placeholder="Nom" required=""/>
            
            <h2>Prénom *</h2>
            <input type="text" name="firstname" value="${s.firstname}" placeholder="Prénom" required=""/>
            
            <h2>Login</h2>
            ${s.username}
            
            <h2>Mot de passe *</h2>
            <input type="password" name="password" value="${s.password}"placeholder="Mot de passe" required=""/>
            
            <h2>Votre école *</h2>
            <select name="school" required="">
                <option value=""></option>
                <c:forEach var="school" items="${requestScope['allSchools']}">
                    <c:choose>
                        <c:when test="${school.id == s.school.id}">
                            <option selected="" value="${school.id}">
                                ${school.toString()}
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="${school.id}">
                                ${school.toString()}
                            </option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>

            <h2>Téléphone</h2>
            <c:forEach var="phone" items="${s.phoneNumbers}">
                <div class="multiblock">
                    <input type="tel" name="phone" value="${phone}" placeholder="Téléphone"/>
                </div>
            </c:forEach>
                <div class="multiblock">
                    <input type="tel" name="phone" placeholder="Téléphone"/>
               </div>
            
            <h2>Adresse email *</h2>
            <div class="multiblock">
                <input type="email" name="email" value="${s.emails[0]}" placeholder="Adresse email" required=""/>
            </div>
            <!-- Faire un truc pour recuperer tous les emails (avec le 1er en require -->
            <div class="multiblock">
                <input type="email" name="email" placeholder="Adresse email"/>
            </div>
            
            <h2>Image</h2>
            <input name="image" type="file"/>
            
            <input type="hidden" name="action" value="update"/>
            <button type="submit" class="button">Valider</button>
        </form>
    </section>
</c:if>