<%-- 
    Document   : productcatalog
    Created on : 10. nov. 2019, 13.10.28
    Author     : Rikke, carol, Nina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Product catalog</title>
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
        <!-- View navigation bar (specific to this page) -->
        <nav class="view-nav">
            <div id="btnContainer" style="float: right;">
                <button class="btn" onclick="listView()"><i class="glyphicon glyphicon-th-list"></i> List</button> 
                <button class="btn active" onclick="gridView()"><i class="glyphicon glyphicon-th-large"></i> Grid</button>
                <button class="btn" onclick="tableView()"><i class="fas fa-table"></i> Table</button>
            </div>
        </nav>

        <!-- Sidebar with search (hidden by default) -->
        <%@include file="includes/catalogincludes/sidebar.jsp" %>

        <!-- Main content of the page -->
        <div id="main">
            <br>
            <h1>Product Catalog</h1>
            <br>

            <!-- Grid and list view -->
            <div class="row">
                <c:forEach items="${catalog}" var="product">
                    <div class="column">
                        <p>Product info</p>
                    </div>
                </c:forEach>
            </div>

            <!-- Table view (hidden by default) -->
            <%@include file="includes/catalogincludes/tableview.jsp" %>
            <br><br>
            <!--
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

    <form name="edit_category" action="FrontController" method="POST">
        <input type="hidden" name="cmd" value="get_view">
        <select name="category">
            <c:forEach items="${categories}" var="cat">
                <option value="${cat.getCategoryname()}">
                ${cat.getCategoryname()}
            </option>
            </c:forEach>
        </select>
        <input type="hidden" name="view" value="editcategory">
        <input type="submit" value="Edit category">
    </form> -->
        </div>
        <script>
            // Get the elements with class="column"
            var elements = document.getElementsByClassName("column");
            var table = document.getElementById("catalogTable");
            var i;

            // List View
            function listView() {
                for (i = 0; i < elements.length; i++) {
                    elements[i].style.display = "block";
                    elements[i].style.width = "100%";
                }
                table.style.display = "none";
            }

            // Grid View
            function gridView() {
                for (i = 0; i < elements.length; i++) {
                    elements[i].style.display = "block";
                    elements[i].style.width = "50%";
                }
                table.style.display = "none";
            }

            function tableView() {
                for (i = 0; i < elements.length; i++) {
                    elements[i].style.display = "none";
                }
                table.style.display = "block";
            }

            /* Set the width of the sidebar to 250px and the left margin of the page content to 250px */
            function openNav() {
                document.getElementById("sidebar").style.width = "250px";
                document.getElementById("main").style.marginLeft = "250px";
                document.getElementByClass("navbar-content").style.marginLeft = "250px";
            }

            /* Set the width of the sidebar to 0 and the left margin of the page content to 0 */
            function closeNav() {
                document.getElementById("sidebar").style.width = "0";
                document.getElementById("main").style.marginLeft = "0";
                document.getElementByClass("navbar-content").style.marginLeft = "0";
            }

            var container = document.getElementById("btnContainer");
            var btns = container.getElementsByClassName("btn");
            for (var i = 0; i < btns.length; i++) {
                btns[i].addEventListener("click", function () {
                    var current = document.getElementsByClassName("active");
                    current[0].className = current[0].className.replace(" active", "");
                    this.className += " active";
                });
            }
        </script>
    </body>
</html>