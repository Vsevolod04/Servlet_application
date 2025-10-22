<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <title>register</title>
    </head>

    <body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>

        <jsp:include page="header.jsp"/>

        <main class="m-2">
            <h1>Регистрация на сайте</h1>

            <c:if test="${result != null}">
                ${requestScope.result}
            </c:if>

            <form class="row g-3" method="POST" action="AuthServlet" id="form">
                <div class="row-auto">
                    <label for="login" class="form-label">Логин</label>
                    <input type="text" class="form-control" id="login" name="login" 
                           placeholder="login" required>
                </div>
                <div class="row-auto">
                    <label for="password" class="form-label">Пароль</label>
                    <input type="password" class="form-control" id="password" 
                           name="password" placeholder="password" required>
                </div>
                <div class="row-auto">
                    <label for="confirmPassword" class="form-label">Подтверждение пароля</label>
                    <input type="password" class="form-control" id="confirmPassword" 
                           name="confirmPassword" placeholder="confirm password" required>
                </div>
                <div class="row-auto">
                    <label for="role" class="form-label">Выбор роли </label>
                    <select class="form-select" id="role" name="role"
                            aria-label="Floating label disabled select example" required>
                        <option value="admin">администратор</option>
                        <option value="user" selected>пользователь</option>
                    </select>
                </div>
                <div class="row-auto">
                    <button type="submit" class="btn btn-primary mb-3">Регистрация</button>
                    <button type="reset" class="btn btn-warning mb-3">Сброс</button>
                </div>
            </form>

            <p>
                <a href="login.jsp" class="link-primary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">
                    Вход
                </a>
            </p>
        </main>

        <script>
            let form = document.getElementById("form");
            form.addEventListener("submit", function (event) {
                const password = document.getElementById('password').value;
                const confirmPassword = document.getElementById('confirmPassword').value;
                if (password !== confirmPassword) {
                    event.preventDefault();
                    alert('Пароли не совпадают! Проверьте введенные данные.');
                }
            });

            let login = document.getElementById("login");
            login.addEventListener("input", function (event) {
                this.value = String(this.value).trim();
            });

            let password = document.getElementById("password");
            password.addEventListener("input", function (event) {
                this.value = String(this.value).trim();
            });

        </script>

        <jsp:include page="footer.jsp"/>
    </body>
</html>
