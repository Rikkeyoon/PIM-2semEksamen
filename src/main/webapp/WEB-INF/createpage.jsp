<%-- 
    Document   : createpage
    Created on : 5. dec. 2019, 14.36.40
    Author     : carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <form name="create_category" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="createcategory">
            <input type="submit" value="Create new category" />
        </form>
        <br>

        <form name="create" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="createproduct">
            <select name="category">
                <c:forEach items="${categories}" var="cat">
                    <option value="${cat.getCategoryname()}">
                        ${cat.getCategoryname()}
                    </option>
                </c:forEach>
            </select>

            <input type="submit" value="Create new product for category">
        </form>
        <br>
    </body>
</html>
