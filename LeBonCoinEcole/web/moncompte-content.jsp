<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<!-- Formulaire de creation de compte -->
<c:if test="${requestScope['action'] == 'formCreationComtpe'}"> 

    <section class="container-form" >
        <form id="compte-form" method="POST" action="MonCompte">
            <h1>Création de compte</h1>
            
            <h2>Nom</h2>
            <input type="text" name="lastname" placeholder="Nom"/>
            
            <h2>Prénom</h2>
            <input type="text" name="firstname" placeholder="Prénom"/>
            
            <h2>Login *</h2>
            <input type="text" name="username" placeholder="Login" required=""/>
            
            <h2>Mot de passe *</h2>
            <input type="password" name="password" placeholder="Mot de passe" required=""/>

            <h2>Votre adresse</h2>
            <div class="multibock">
                <input class="largeur-60" type="number" name="addr-number" min="0" placeholder="num"/>
                <input class="largeur-300" type="text" name="addr-name" placeholder="rue"/><br>
                <input class="largeur-100" type="number" name="addr-areaCode" min="1" placeholder="Code postal"/>
                <input class="largeur-100" type="text" name="addr-city" placeholder="Ville"/>
                <input class="largeur-100" type="text" name="addr-country" placeholder="Pays"/>
            </div>
            
            <h2>Votre école *</h2>
            <select name="school" required="">
                <option value=""></option>
                <c:forEach var="s" items="${requestScope['allSchools']}">
                    <option value="${s.id}">${s.toString()}</option>
                </c:forEach>
            </select>
            
            <h2>Téléphone</h2>
            <input type="tel" name="phone" placeholder="téléphone"/>
            
            <h2>Adresse email</h2>
            <input type="email" name="email" placeholder="Adresse email"/>
    
            <h2>Image</h2>
            <input type="file"/>
            
            <input type="hidden" name="action" value="create"/>
            <button type="submit" class="button">Valider</button>
        </form>
    </section>
</c:if>

<!-- Affichage du compte -->
<c:if test="${requestScope['action'] == 'displayInfoCompte'}"> 

    <section class="container-form" id="displayCompte">
        <form method="GET" action="MonCompte">
            <h1>Mes informations</h1>

            <c:set var="s" value="${requestScope['student']}"/> 
            <img src="//static.leboncoin.fr/img/no-picture.png"/>
            
            <h2>Nom</h2>
            ${s.lastname}
            
            <h2>Prénom</h2>
            ${s.firstname}
            
            <h2>Login</h2>
            ${s.username}

            <h2>Mes adresses</h2>
            <ul>
                <c:forEach var="addr" items="${s.address}">
                    <li>${addr}</li>
                </c:forEach>
            </ul>

            <h2>Mon école</h2>
            <a href="http://${s.school.link}" target="_blank">${s.school.name}</a><br>
            ${s.school.address.toString()}

            <h2>Téléphone</h2>
            <ul>
                <c:forEach var="phone" items="${s.phoneNumbers}">
                    <li>${phone}</li>
                </c:forEach>
            </ul>

            <h2>Email</h2>
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
        <form id="compte-form" method="POST" action="MonCompte">
            <h1>Modifier mes informations</h1>
            
            <c:set var="s" value="${requestScope['student']}"/>
            <img src="//static.leboncoin.fr/img/no-picture.png"/>
            
            <h2>Nom</h2>
            <input type="text" name="lastname" value="${s.lastname}" placeholder="Nom"/>
            
            <h2>Prénom</h2>
            <input type="text" name="firstname" value="${s.firstname}" placeholder="Prénom"/>
            
            <h2>Login</h2>
            ${s.username}
            
            <h2>Mot de passe *</h2>
            <input type="password" name="password" placeholder="Mot de passe"/>

            <h2>Mes adresse</h2>
            <c:forEach var="addr" items="${s.address}">            
                <div class="multibock">
                    <input class="largeur-60" type="number" name="addr-number" min="0" value="${addr.number}" placeholder="Num"/>
                    <input class="largeur-300" type="text" name="addr-name" value="${addr.name}" placeholder="Rue"/><br>
                    <input class="largeur-60" type="number" name="addr-areaCode" min="1" value="${addr.areaCode}"placeholder="Code postal"/>
                    <input class="largeur-100" type="text" name="addr-city" value="${addr.city}" placeholder="Ville"/>
                    <input class="largeur-100" type="text" name="addr-country" value="${addr.country}" placeholder="Pays"/>
                </div>
            </c:forEach>
            
            <div class="multibock">
                <input class="largeur-60" type="number" name="addr-number" min="0" placeholder="Num"/>
                <input class="largeur-300" type="text" name="addr-name" placeholder="Rue"/><br>
                <input class="largeur-100" type="number" name="addr-areaCode" min="1" placeholder="Code postal"/>
                <input class="largeur-100" type="text" name="addr-city" placeholder="Ville"/>
                <input class="largeur-100" type="text" name="addr-country" placeholder="Pays"/>
            </div>
            
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
                <div class="multibock">
                    <input type="tel" name="phone" value="${phone}" placeholder="Téléphone"/>
                </div>
            </c:forEach>
                <div class="multibock">
                    <input type="tel" name="phone" placeholder="Téléphone"/>
               </div>
            
            <h2>Adresse email</h2>
            <c:forEach var="email" items="${s.emails}">
                <div class="multibock">
                    <input type="email" name="email" value="${email}" placeholder="Adresse email"/>
                </div>
            </c:forEach>
                <div class="multibock">
                    <input type="email" name="email" placeholder="Adresse email"/>
                </div>
            
            <h2>Image</h2>
            <input type="file"/>
            
            <input type="hidden" name="action" value="update"/>
            <button type="submit" class="button">Valider</button>
        </form>
    </section>
</c:if>