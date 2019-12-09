<%-- 
    Document   : viewcategories
    Created on : 3. dec. 2019, 09.29.41
    Author     : Nina, carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>INFOMERGE | Category Catalog</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
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
                <form name="bulkEdit" action="FrontController" method="POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="choosecreate">
                    <button type="submit" class="viewcatalogbtn">Create</button>
                </form>

                <form name="get_product_catalog" action="FrontController" method = "POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="productcatalog">
                    <button type="submit" class="viewcatalogbtn"><span>Catalog</span></button>
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
                <br> 

                <form name="download" action="FrontController" method="POST">
                    <input type="hidden" name="cmd" value="download_categories">
                    <div class="input-group" style="position: absolute;
                         bottom: 20px!important;margin-right: 10px;">
                        <div class="form-control">Download categories</div>
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit">
                                <i class="glyphicon glyphicon-download"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Main content -->
        <div id="main">
            <br>
            <h1>View categories</h1>

            <div class="container-table" style="display:flex;">
                <div class="wrap-table1">
                    <div class="table">
                        <table id="categoriesTable" border="1" >
                            <thead>
                                <tr class="table-head">
                                    <th onclick="sortAlphabeticalTable(0)">Categoryname
                                        <i class="glyphicon glyphicon-triangle-bottom" 
                                           style="cursor: pointer; font-size: 14px;"></i>
                                    </th>
                                    <th>Attributes</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${categories}" var="cat">
                                    <tr>
                                        <td>${cat.getCategoryname()}</td>
                                        <td>
                                            <c:forEach items="${cat.getAttributes()}" var="attr">
                                                ${attr}
                                                <br>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <form name="edit_category" action="FrontController" method = "POST">
                                                <input type="hidden" name="cmd" value="get_view">
                                                <input type="hidden" name="view" value="editcategory">
                                                <input type="hidden" value="${cat.getCategoryname()}" name="category"/>
                                                <button type="submit" class="imgbtn">
                                                    <i class="far fa-edit" style="cursor: pointer"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <br><br>
                    </div>
                </div>
            </div>
        </div>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript"  src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript"  src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <script>
                                        $(document).ready(function () {
                                            $('#categoriesTable').dataTable({"searching": false});
                                        });

                                        function sortAlphabeticalTable(n) {
                                            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
                                            table = document.getElementById("categoriesTable");
                                            switching = true;
                                            //Set the sorting direction to ascending:
                                            dir = "asc";
                                            /*Make a loop that will continue until
                                             no switching has been done:*/
                                            while (switching) {
                                                //start by saying: no switching is done:
                                                switching = false;
                                                rows = table.rows;
                                                /*Loop through all table rows (except the
                                                 first, which contains table headers):*/
                                                for (i = 1; i < (rows.length - 1); i++) {
                                                    //start by saying there should be no switching:
                                                    shouldSwitch = false;
                                                    /*Get the two elements you want to compare,
                                                     one from current row and one from the next:*/
                                                    x = rows[i].getElementsByTagName("TD")[n];
                                                    y = rows[i + 1].getElementsByTagName("TD")[n];
                                                    /*check if the two rows should switch place,
                                                     based on the direction, asc or desc:*/
                                                    if (dir == "asc") {
                                                        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                                                            //if so, mark as a switch and break the loop:
                                                            shouldSwitch = true;
                                                            break;
                                                        }
                                                    } else if (dir == "desc") {
                                                        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                                                            //if so, mark as a switch and break the loop:
                                                            shouldSwitch = true;
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (shouldSwitch) {
                                                    /*If a switch has been marked, make the switch
                                                     and mark that a switch has been done:*/
                                                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                                                    switching = true;
                                                    //Each time a switch is done, increase this count by 1:
                                                    switchcount++;
                                                } else {
                                                    /*If no switching has been done AND the direction is "asc",
                                                     set the direction to "desc" and run the while loop again.*/
                                                    if (switchcount == 0 && dir == "asc") {
                                                        dir = "desc";
                                                        switching = true;
                                                    }
                                                }
                                            }
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
