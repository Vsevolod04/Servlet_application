<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>КОД <%= response.getStatus()%></h1>
        <p><c:out value="${error}" default="Unknown Error" /></p>
        <a href="LombardServlet">Вернуться</a>
</body>
</html>
