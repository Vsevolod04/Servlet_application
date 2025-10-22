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

        <script>
            function updRedirect(id) {
                window.location = "<%= getServletContext().getContextPath()%>/updatePage.jsp?id=" + id;
            }
        </script>


        <jsp:include page="header.jsp"/>

        <main class="m-2">
            <div class="row ">
                <p class="col-2">
                    Hello <c:out value="${login}"/> (<c:out value="${role}"/>)
                </p>
                <a href="LogoutServlet" class="col-2 link-primary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">
                    Выход
                </a>
            </div>

            <div class="row">
                <div class="col-8">
                    <table class="table table-hover table-info table-bordered border-dark">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Имя клиента</th>
                                <th scope="col">Вещь</th>
                                <th scope="col">Стоимость</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${items}">
                                <tr>
                                    <th scope="row">${item.getId()}</td>
                                    <td>${item.getClient()}</td>
                                    <td>${item.getItem()}</td>
                                    <td>${item.getCost()}</td>
                                    <td data-label='Действия'>
                                        <div>
                                            <button type='button' class='btn btn-light' onclick="updRedirect(${item.getId()})">
                                                <img src='icons/pencil-fill.svg' alt='edit'>
                                            </button>
                                            <form method="GET" action="LombardServlet" style="display: inline">
                                                <input type="text" name="method" value="DELETE" hidden>
                                                <input type="text" name="id" value="${item.getId()}" hidden>
                                                <button type='submit' class='btn btn-light'>
                                                    <img src='icons/trash-fill.svg' alt='delete'>
                                                </button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="col-4 bg-light"> 
                    <form class="g-3" method="POST" action="LombardServlet" id="form">
                        <div class="row-auto">
                            <label for="client" class="form-label">Клиент</label>
                            <input type="text" class="form-control" id="client" name="client" 
                                   placeholder="client" required>
                        </div>
                        <div class="row-auto">
                            <label for="item" class="form-label">Вещь</label>
                            <input type="text" class="form-control" id="item" 
                                   name="item" placeholder="item" required>
                        </div>
                        <div class="row-auto">
                            <label for="cost" class="form-label">Стоимость</label>
                            <input type="text" class="form-control" id="cost" 
                                   name="cost" placeholder="1.99" pattern="^\d+(\.\d{1,2})?$" required>
                        </div>
                        <div class="row-auto mt-2">
                            <button type="submit" class="btn btn-primary mb-3">Добавить</button>
                            <button type="reset" class="btn btn-warning mb-3">Сброс</button>
                        </div>
                    </form>
                </div>

            </div>

        </main>

        <jsp:include page="footer.jsp"/>
    </body>
</html>


