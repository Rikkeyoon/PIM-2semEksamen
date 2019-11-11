<%-- 
    Document   : index.jsp
    Created on : 10. nov. 2019, 11.57.38
    Author     : Rikke
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Homepage</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>

    <body>
        <h1>Pim system</h1>
        <input type="submit" value="Product Catalog" />


        <!-- Exception handling -->
        <c:if test="${error != null}">
            <div class="form-alert">${error}</div>
        </c:if>
    </body>
</html>
