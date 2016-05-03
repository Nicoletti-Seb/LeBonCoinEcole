<form id="compte-form" method="POST" action="${pageContext.request.contextPath}/account">
    <h1>Cr�ation de compte</h1>

    <h2>Nom *</h2>
    <input type="text" name="lastname" placeholder="Nom" required=""/>

    <h2>Pr�nom *</h2>
    <input type="text" name="firstname" placeholder="Pr�nom" required=""/>

    <h2>Login *</h2>
    <input type="text" name="username" placeholder="Login" required=""/>

    <h2>Mot de passe *</h2>
    <input type="password" name="password" placeholder="Mot de passe" required=""/>

    <h2>Votre �cole *</h2>
    <select name="school" required="">
        <option value=""></option>
        <c:forEach var="s" items="${requestScope['allSchools']}">
            <option value="${s.id}">${s.toString()}</option>
        </c:forEach>
    </select>

    <h2>T�l�phone</h2>
    <input type="tel" name="phone" placeholder="t�l�phone"/>

    <h2>Adresse email *</h2>
    <input type="email" name="email" placeholder="Adresse email" required=""/>

    <h2>Image</h2>
    <input type="file"/>

    <input type="hidden" name="action" value="create"/>
    <button type="submit" class="button">Valider</button>
</form>