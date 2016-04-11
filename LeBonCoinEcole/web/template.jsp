<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>leboncoin - universitaire</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css" />
</head>
<body>
    <!-- Header -->
    <jsp:include page="header.jsp"/>

    <!-- Body -->
    <div class="container">
        <jsp:include page="${param.content}.jsp"/>
    </div>
    
    <!-- Footer  -->
    <jsp:include page="footer.jsp"/>
        
</body>
</html>