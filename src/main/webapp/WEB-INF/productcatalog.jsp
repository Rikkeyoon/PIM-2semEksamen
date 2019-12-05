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
        <!-- Sidebar with search (hidden by default) -->
        <%@include file="includes/sidebar.jsp" %>
        <!--
        <div id="sidebar" class="sidebar">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <div class="padded">
                <form name="get_product_catalog" action="FrontController" method = "POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="productcatalog">
                    <button type="submit">Products</button>
                </form>
                <br><br>
                <form name="get_product_catalog" action="FrontController" method = "POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!">
                    <button type="submit">Categories</button>
                </form>
                <br>
                <h4>Search for: </h4>
                <form name="search" action="FrontController" method = "POST">
                    <input type="hidden" name="cmd" value="search_product">
                    <input type="radio" name="searchType" value="product_id" checked="checked"/> Id
                    <input type="radio" name="searchType" value="product_name"/> Name
                    <input type="radio" name="searchType" value="product_category"/> Category
                    <input type="radio" name="searchType" value="product_tag"/> Tag
                    <br><br>
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search" name="search">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit">
                                <i class="glyphicon glyphicon-search"></i>
                                glyphicon glyphicon-upload
                            </button>
                        </div>
                    </div>
                </form>
                <br>
                <form name="get_product_catalog" action="FrontController" method = "POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="productcatalog">
                    <button class="btn btn-default" type="submit" style="float:right;">
                        Reset</button>
                </form>

                <br><br>
                <form name="download" action="FrontController" method="POST">
                    <input type="hidden" name="cmd" value="download_catalog">
                    <div class="input-group" style="position: absolute;
                         bottom: 20px!important;margin-right: 10px;">
                        <div class="form-control">Download catalog</div>
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit">
                                <i class="glyphicon glyphicon-download"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div> -->

        <!-- Navigation bar -->
        <nav class="nav">
            <div class="navbar-content">
                <button class="openbtn" onclick="openNav()">&#9776;</button>
            </div>
            <h1>PIM System</h1>

            <form name="get_upload_page" action="FrontController" method = "POST">
                <input type="hidden" name="cmd" value="get_view">
                <input type="hidden" name="view" value="uploadpage">
                <input type="submit" value="Go to upload page" />
            </form>
            <br><br>
        </nav>
        <nav class="view-nav">
            <div id="btnContainer" style="float: right;">
                <button class="btn" onclick="listView()"><i class="glyphicon glyphicon-th-list"></i> List</button> 
                <button class="btn active" onclick="gridView()"><i class="glyphicon glyphicon-th-large"></i> Grid</button>
                <button class="btn" onclick="tableView()"><i class="fas fa-table"></i> Table</button>
            </div>
        </nav>

        <!-- Main content of the page -->
        <div id="main">
            <h1>Product Catalog</h1>
            <br>

            <div class="row">
                <div class="column">
                    <p>Product info</p>
                </div>
                <div class="column">
                    <p>Product info</p>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <p>Product info</p>
                </div>
                <div class="column">
                    <p>Product info&#8661;</p>
                </div>
            </div>
            <!-- Table view (hidden by default) -->
            <%@include file="includes/tableview.jsp" %>
            <br><br>

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
            </form>
            <br><br>
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