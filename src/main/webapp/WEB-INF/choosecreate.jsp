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
        <title>INFOMERGE | Create</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
        <script src="https://kit.fontawesome.com/6341639fb9.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <!-- Navigation bar -->
        <%@include file="includes/navigationbar.jsp" %>

        <!-- Sidebar -->
        <%@include file="includes/sidebar.jsp" %>

        <!-- Main content of the page -->
        <div id="main">
            <br><br>
            <div class="container text-center" >
                <div class="row" style="display: flex; justify-content: center;">
                    <div class="column-50">
                        <div class="full-color">
                            <div class="full-color-content">
                                <form name="create_category" action="FrontController" method="POST">
                                    <input type="hidden" name="cmd" value="get_view">
                                    <input type="hidden" name="view" value="createcategory">
                                    <input class="full-color-btn" type="submit" value="Create new category" />
                                </form>
                                <br>
                            </div>
                        </div>
                    </div>
                    <div class="column-50">
                        <div class="full-color">
                            <form name="create" action="FrontController" method="POST">
                                <input type="hidden" name="cmd" value="get_view">
                                <input type="hidden" name="view" value="createproduct">
                                <div class="full-color-content">
                                    <input class="full-color-btn" type="submit" value="Create new product" />
                                </div>
                                <div class="input-group">
                                    <select class="custom-select" name="category" style="width: 260px;">
                                        <option selected disabled>Choose...</option>
                                        <c:forEach items="${categories}" var="cat">
                                            <option value="${cat.getCategoryname()}">
                                                ${cat.getCategoryname()}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </form> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
