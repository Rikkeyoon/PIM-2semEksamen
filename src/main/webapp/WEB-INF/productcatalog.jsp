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
        <title>INFOMERGE | Product Catalog</title>
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
        <%@include file="includes/catalogincludes/navigationbar.jsp" %>

        <!-- Sidebar with search (hidden by default) -->
        <%@include file="includes/catalogincludes/sidebar.jsp" %>

        <!-- Main content of the page -->
        <div id="main">
            <br>
            <h1>Product Catalog</h1>
              <c:if test="${error != null}">
                <div class="form-alert"><h4>${error}</h4></div>
                    </c:if>
            <br><br>

            <!-- Table view (hidden by default) -->
            <%@include file="includes/catalogincludes/tableview.jsp" %> 

            
            <!-- Grid and list view -->
            <div class="row">
                <c:forEach items="${catalog}" var="product">
                    <div class="column-25">
                        <div class="imgcontainer">
                            <form name="view_product" action="FrontController" method = "POST">
                                <input type="hidden" name="cmd" value="get_view">
                                <input type="hidden" name="view" value="viewproduct">
                                <input type="hidden" value="${product.getId()}" name="product_id"/>
                                <button type="submit" class="imgbtn">
                                    <c:choose>
                                        <c:when test="${not empty product.getImages()}">
                                            <c:choose>
                                                <c:when test="${!product.getPrimaryImage().equals('')}">
                                                    <img class="cropped" alt= "Picture not found" src = "${product.getPrimaryImage()}"> 
                                                </c:when>
                                                <c:otherwise>
                                                    <img class="cropped" alt= "Picture not found" src = "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574764086/defaut_vignette_carre_xavv98.jpg">
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when> 
                                        <c:otherwise>
                                            <img class="cropped" alt= "Picture not found" src = "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574764086/defaut_vignette_carre_xavv98.jpg">
                                        </c:otherwise>
                                    </c:choose>
                                </button>
                            </form>
                            <div class="bottom-center">
                                <div class="product-name">${product.getName()}</div>
                                <p style="background-color: white">${product.getBrand()}</p>
                            </div>
                            <div class="top-left">${product.getItemnumber()}
                                <br>
                                ${product.getCategory().getCategoryname()}
                            </div>
                            <div class="top-right">
                                <form name="update" action="FrontController" method = "POST">
                                    <input type="hidden" name="cmd" value="get_view">
                                    <input type="hidden" name="view" value="updateproduct">
                                    <input type="hidden" value="${product.getId()}" name="product_id"/>
                                    <button type="submit" class="btn">
                                        <i class="far fa-edit" style="cursor: pointer"></i>
                                    </button>
                                </form>
                            </div>
                        </div>
                        <br>
                    </div>
                </c:forEach>
            </div>

            <br><br>
        </div>
        <script>
            // Get the elements with class="column"
            var elements = document.getElementsByClassName("column-25");
            var table = document.getElementById("catalogTable");
            var tcontainer = document.getElementsByClassName("container-table")[0];
            var i;
            // List View
            function listView() {
                for (i = 0; i < elements.length; i++) {
                    elements[i].style.display = "block";
                    elements[i].style.width = "100%";
                }
                table.style.display = "none";
                tcontainer.style.display = "none";
            }


            // Grid View
            function gridView() {
                for (i = 0; i < elements.length; i++) {
                    elements[i].style.display = "block";
                    elements[i].style.width = "25%";
                }
                table.style.display = "none";
                tcontainer.style.display = "none";
            }

            // Table View
            function tableView() {
                for (i = 0; i < elements.length; i++) {
                    elements[i].style.display = "none";
                }
                table.style.display = "block";
                tcontainer.style.display = "flex";
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