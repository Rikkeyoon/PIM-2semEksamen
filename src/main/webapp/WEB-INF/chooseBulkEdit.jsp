<%-- 
    Document   : Bulkedit
    Created on : 01. dec. 2019, 13.10.28
    Author     : ALLAN!
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Bulk Edit</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
        <script src="https://kit.fontawesome.com/6341639fb9.js" crossorigin="anonymous"></script>
    </head>

    <body>
        <!-- Navigation bar -->
        <%@include file="includes/navigationbar.jsp" %>

        <!-- Main content -->
        <div id="main">
            <br><br>
            <h1 style="text-align: center;">Bulk Edit</h1>
            <h2 style="text-align: center;">Choose category:</h2>
            <c:if test="${error != null}">
                <div class="form-alert"><h4>${error}</h4></div>
                    </c:if>
            <div class="container text-center" >
                <div class="row">
                    <div class="col col-lg-12">    
                        <form name="bulkedit" action="FrontController" method="POST">
                            <input type="hidden" name="cmd" value="get_view">
                            <input type="hidden" name="view" value="bulkedit">
                            <select name="category">
                                <c:forEach items="${categories}" var="cat">
                                    <option value="${cat.getCategoryname()}">
                                        ${cat.getCategoryname()}
                                    </option>
                                </c:forEach>
                            </select>
                            <input type="submit" value="bulk edit">
                        </form>  
                    </div>
                </div>
            </div>
    </body>
</html>