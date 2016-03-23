<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

<h1> Index </h1>


<table>
    <caption>Liste des cat�gories</caption>
    <thead> 
        <th>Id</th>
        <th>Name</th>
    </thead>  

    <tbody>
        <c:forEach var="c" items="${requestScope['allCategories']}">  
            <tr>  
                <td>${c.id}</td>
                <td>${c.name}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<table>
    <caption>Liste des �tudiants</caption>
    <thead>  
        <th>Id</th>
        <th>Lastname</th>
        <th>Firstname</th>
        <th>Username</th>
    </thead>  

    <tbody>
        <c:forEach var="s" items="${requestScope['allStudents']}">  
            <tr>  
                <td>${s.id}</td>
                <td>${s.lastname}</td>
                <td>${s.firstname}</td>
                <td>${s.username}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<table>
    <caption>Liste des �coles</caption>
    <thead>  
        <th>Id</th>
        <th>Name</th>
        <th>Number</th>
        <th>Rue</th>
        <th>AreaCode</th>
        <th>City</th>
        <th>Country</th>
    </thead>  

    <tbody>
        <c:forEach var="s" items="${requestScope['allSchools']}">  
            <tr>  
                <td>${s.id}</td>
                <td>${s.name}</td>
                <td>${s.address.number}</td>
                <td>${s.address.name}</td>
                <td>${s.address.areaCode}</td>
                <td>${s.address.city}</td>
                <td>${s.address.country}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>