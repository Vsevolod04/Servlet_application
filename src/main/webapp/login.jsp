<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <title>login</title>
    </head>

    <body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>

        <jsp:include page="header.jsp"/>

        <main class="m-2">
            <h1>Вход на сайт</h1>
            <form class="row g-3" method="GET" action="AuthServlet">
                <div class="row-auto">
                    <label for="email" class="form-label">Логин</label>
                    <input type="text" class="form-control" id="login" 
                           name="login" placeholder="login" required>
                </div>
                <div class="row-auto">
                    <label for="password" class="form-label">Пароль</label>
                    <input type="password" class="form-control" id="password" 
                           name="password"  placeholder="password" required>
                </div>
                <div class="row-auto">
                    <button type="submit" class="btn btn-primary mb-3">Вход</button>
                    <button type="reset" class="btn btn-warning mb-3">Сброс</button>
                </div>
            </form>

            <p>
                <a href="register.jsp" class="link-primary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">
                    Регистрация
                </a>
            </p>
        </main>

        <jsp:include page="footer.jsp"/>
    </body>
</html>
