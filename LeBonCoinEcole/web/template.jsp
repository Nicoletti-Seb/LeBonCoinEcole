<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>${param.title}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css" />
</head>
<body>
    <!-- Header -->
    <jsp:include page="header.jsp"/>

    <!-- Body -->
    <h1>${param.title}</h1>
    <jsp:include page="${param.content}.jsp"/>

    <!-- Footer  -->
    <jsp:include page="footer.jsp"/>
        
</body>
</html>