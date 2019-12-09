<%-- 
    Document   : Bulkedit
    Created on : 01. dec. 2019, 13.10.28
    Author     : ALLAN!, carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>INFOMERGE | Bulk Edit</title>
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

        <!-- Sidebar -->
        <div id="sidebar" class="sidebar">
            <a href="javascript:void(0)" class="closebtn" onclick="closeSidebar()">&times;</a>
            <div class="padded">
                <form name="get_product_catalog" action="FrontController" method = "POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="productcatalog">
                    <button type="submit" class="viewcatalogbtn"><span>Products</span></button>
                </form>
                <form name="view_categories" action="FrontController" method="POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="viewcategories">
                    <button type="submit" class="viewcatalogbtn">Categories</button>
                </form>
                <form name="bulkEdit" action="FrontController" method="POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="choosebulkedit">
                    <button type="submit" class="viewcatalogbtn">Bulk edit</button>
                </form>
                <form name="bulkEdit" action="FrontController" method="POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="choosecreate">
                    <button type="submit" class="viewcatalogbtn">Create</button>
                </form>
            </div>
        </div>

        <!-- Main content of the page -->
        <div id="main">
            <br><br>
            <div class="container text-center">
                <div class="row" style="display: flex; justify-content: center;">
                    <div class="column-50">
                        <div class="full-color">
                            <form name="bulkedit" action="FrontController" method="POST">
                                <input type="hidden" name="cmd" value="get_view">
                                <input type="hidden" name="view" value="bulkedit">
                                <div class="full-color-content">
                                    <button class="full-color-btn" type="submit">
                                        Bulk edit
                                    </button>
                                </div>
                                <div class="input-group">
                                    <select class="custom-select" name="category" style="width: 260px;margin-left: 150px;">
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
        <script>
            var sidebar = document.getElementById("sidebar");
            var main = document.getElementById("main");
            var navbar = document.getElementsByClassName("navbar-content")[0];
            var viewbar = document.getElementsByClassName("view-nav-content")[0];
            function openSidebar() {
                sidebar.style.width = "250px";
                main.style.marginLeft = "250px";
                navbar.style.marginLeft = "250px";
                viewbar.style.marginLeft = "250px";
            }

            function closeSidebar() {
                sidebar.style.width = "0";
                main.style.marginLeft = "0";
                navbar.style.marginLeft = "0";
                viewbar.style.marginLeft = "0";
            }
        </script>
    </body>
</html>