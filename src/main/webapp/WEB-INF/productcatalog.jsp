<%-- 
    Document   : productcatalog
    Created on : 10. nov. 2019, 13.10.28
    Author     : Rikke, carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Product catalog</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>

    <body>
        <h1>Product Catalog</h1>
        <br>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Category</th>
                    <th>View Product</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${catalog}" var="product">
                    <tr>
                        <td>${product.getId()}</td>
                        <td>${product.getName()}</td>
                        <td>${product.getDescription()}</td>
                        <td>${product.getCategory().getCategoryname()}</td>
                        <td>
                            <form name="create" action="FrontController" method = "POST">
                                <input type="hidden" name="cmd" value="view_product">
                                <input type="hidden" value="${product.getId()}" name="product_id"/>
                                <input type="submit" value="View product">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br><br>
        <form name="create" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="view_create_page">
            <input type="submit" value="Create new product" />
        </form>
        <br>

        <form name="create_category" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="create_category">
            <input type="submit" value="Create new category" />
        </form>

    </body>
</html>