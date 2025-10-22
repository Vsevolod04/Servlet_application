<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <title>Update page</title>
    </head>
    <body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>

        <jsp:include page="header.jsp"/>

        <%@ page import="com.sorkin.ssp10.myclasses.LombardItem" %>
        <%
            LombardItem item = null;
            try {
                item = LombardItem.getItem(Integer.valueOf(request.getParameter("id")));

                if (item == null) {
                    response.setStatus(404);
                    request.setAttribute("error", "Неверное id запроса");
                    this.getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
                    return;
                }
            } catch (Exception ex) {
                response.setStatus(404);
                request.setAttribute("error", "Неверное id запроса");
                this.getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
                return;
            }
        %>

        <main class="m-2">

            <h1>Изменение данных</h1>

            <form class="g-3" method="POST" action="LombardServlet" id="form">
                <input type="text" name="method" value="PUT" hidden>
                <div class="row-auto">
                    <label for="id" class="form-label">id</label>
                    <input type="text" class="form-control" id="id" name="id" 
                           value="${param.id}" required readonly>
                </div>
                <div class="row-auto">
                    <label for="client" class="form-label">Клиент</label>
                    <input type="text" class="form-control" id="client" name="client" 
                           value="<%= item.getClient()%>" placeholder="client" required>
                </div>
                <div class="row-auto">
                    <label for="item" class="form-label">Вещь</label>
                    <input type="text" class="form-control" id="item" 
                           name="item" value="<%= item.getItem()%>" placeholder="item" required>
                </div>
                <div class="row-auto">
                    <label for="cost" class="form-label">Стоимость</label>
                    <input type="text" class="form-control" id="cost"  value="<%= item.getCost()%>"
                           name="cost" placeholder="1.99" pattern="^\d+(\.\d{1,2})?$" required>
                </div>
                <div class="row-auto mt-2">
                    <button type="submit" class="btn btn-primary mb-3">Обновить</button>
                    <button type="reset" class="btn btn-warning mb-3">Сброс</button>
                </div>
            </form>

            <a href="LombardServlet" class="col-2 link-primary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">
                Отмена
            </a>
        </main>

        <jsp:include page="footer.jsp"/>
    </body>
</html>
