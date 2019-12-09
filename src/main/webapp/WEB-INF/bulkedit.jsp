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
        <title>INFOMERGE | Bulk Edit</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
        <script src="https://kit.fontawesome.com/6341639fb9.js" crossorigin="anonymous"></script>
    </head>

    <body>
        <!-- Navigation bar -->
        <%@include file="includes/navigationbar.jsp" %>

        <!-- Sidebar -->
        <%@include file="includes/sidebar.jsp" %>

        <!-- Main content -->
        <div id="main">
            <br><br>
            <h1 style="text-align: center;">Bulk Edit</h1>
            <h2 style="text-align: center;">Category: ${category1.getCategoryname()}</h2>
            <c:if test="${error != null}">
                <div class="form-alert"><h4>${error}</h4></div>
                    </c:if>
            <form name="update" action="FrontController" method = "POST">
                <div class="container-table" style="display:block; display: flex;">
                    <div class="wrap-table2">
                        <div class="table">
                            <table id="bulkTable" border="1" align="center">
                                <thead>
                                    <tr class="table-head">
                                        <!-- FIXME! Sort, virker ikke?? -->
                                        <th onclick="sortNumberColumns(1)">Item Number
                                            <i class="glyphicon glyphicon-triangle-bottom" 
                                               style="cursor: pointer; font-size: 14px;"></i>
                                        </th>
                                        <!-- Name virker! -->
                                        <th onclick="sortAlphabeticalTable(3)">Name
                                            <i class="glyphicon glyphicon-triangle-bottom" 
                                               style="cursor: pointer; font-size: 14px;"></i>
                                        </th>
                                        <th onclick="sortAlphabeticalTable(4)">Brand
                                            <i class="glyphicon glyphicon-triangle-bottom" 
                                               style="cursor: pointer; font-size: 14px;"></i>
                                        </th>
                                        <th>Description</th>
                                        <th onclick="sortAlphabeticalTable(5)">Category
                                            <i class="glyphicon glyphicon-triangle-bottom" 
                                               style="cursor: pointer; font-size: 14px;"></i>
                                        </th>
                                        <th onclick="sortAlphabeticalTable(6)">Supplier
                                            <i class="glyphicon glyphicon-triangle-bottom" 
                                               style="cursor: pointer; font-size: 14px;"></i>
                                        </th>
                                        <th onclick="sortNumberColumns()">Status
                                            <i class="glyphicon glyphicon-triangle-bottom" 
                                               style="cursor: pointer; font-size: 14px;"></i>
                                        </th>
                                        <th>tags</th>
                                            <c:forEach items="${category1.getAttributes()}" var = "attribute">
                                            <th>Attribute:<br>${attribute}</th>
                                            </c:forEach>
                                        <th>Select</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${catalog}" var="product">
                                        <tr>
                                            <td>${product.getItemnumber()}</td>
                                            <td>${product.getName()}</td>
                                            <td>${product.getBrand()}</td>
                                            <td>${product.getDescription()}</td>
                                            <td>${product.getSupplier()}</td>
                                            <td>${product.getSEOText()}</td>
                                            <td>${product.getStatus()}</td>
                                            <td>${product.getTagsAsString()}</td>
                                            <c:forEach  items="${category1.getAttributes()}" var = "attributeName">
                                                <td>${product.getCategoryAttributes().get(attributeName)}</td>
                                            </c:forEach>
                                            <td>
                                                <input type="checkbox" name="bulkEditSelected"
                                                       value ="${product.getId()}">
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="container text-center" >
                    <h3 style="text-align: center;"><b>New Values</b></h3>
                    <div class="row">
                        <div class="col col-lg-2"></div>
                        <div class="col col-lg-4">
                            <label for="brand"><b>Brand</b></label>
                            <br>
                            <input class="form-control input-sm" type="text" name="brand" size="50">
                            <br><br>
                        </div>
                        <div class="col col-lg-4">
                            <label for="supplier"><b>Supplier</b></label>
                            <br>
                            <input class="form-control input-sm" type="text" name="supplier" size="50">
                            <br><br>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-lg-2"></div>
                        <div class="col col-lg-4">
                            <label for="seo_text"><b>SEO text</b></label>
                            <br>
                            <input class="form-control input-sm" type="text" name="seo_text">
                            <br><br>
                        </div>
                        <div class="col col-lg-4">
                            <label for="product_tags"><b>Tags</b></label>
                            <br>
                            <input class="form-control input-sm" type="text" name="product_tags">
                            <br><br>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col col-lg-12">
                            <h3><b>Attributes</b></h3>
                        </div>
                    </div>
                    <c:set var = "firstOrSecond" scope = "session" value = "${1}"></c:set>
                    <c:forEach items="${category1.getAttributes()}" var="attr">
                        <c:if test="${firstOrSecond == '1'}">
                            <div class="row">
                                <div class="col col-lg-2"></div>
                            </c:if>
                            <div class="col col-lg-4">
                                <br>
                                <label for="attribute_name"><b>${attr}</b></label>
                                <br>
                                <input type="hidden" name="attributename" value ="${attr}">
                                <input class="form-control input-sm" type="text" name="attributes" value = "" size="50">
                            </div>
                            <c:choose>
                                <c:when test="${firstOrSecond == '0'}">
                                </div>
                                <c:set var = "firstOrSecond" scope = "session" value = "${1}"></c:set>
                            </c:when>
                            <c:otherwise>
                                <c:set var = "firstOrSecond" scope = "session" value = "${0}"></c:set>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${firstOrSecond == '0'}">
                    </div>
                </c:if>
                <br><br>
                <input type="hidden" name="delcmd" value="bulkdelete"/>
                <button class="btn btn-default" type="submit" name="cmd" value ="delete_product"><i class="glyphicon glyphicon-trash" style="margin-right: 10px;"></i>Delete Products</button>
                <button class="btn btn-default" type="submit" name ="cmd" value ="bulkedit"><i class="glyphicon glyphicon-floppy-disk" style="margin-right: 10px;"></i>Save Changes</button>
                <br><br>
            </form>
        </div>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript"  src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript"  src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

        <script>
                                            $(document).ready(function () {
                                                $('#bulkTable').dataTable({"searching": false, "ordering": false});
                                            });

                                            function sortAlphabeticalTable(n) {
                                                var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
                                                table = document.getElementById("bulkTable");
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

                                            function sortNumberColumns(n) {
                                                var table, rows, switching, i, x, y, shouldSwitch, switchcount = 0;
                                                table = document.getElementById("bulkTable");
                                                switching = true;
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
                                                        //check if the two rows should switch place:
                                                        if (dir == "asc") {
                                                            if (Number(x.innerHTML) > Number(y.innerHTML)) {
                                                                //if so, mark as a switch and break the loop:
                                                                shouldSwitch = true;
                                                                break;
                                                            }
                                                        } else if (dir == "desc") {
                                                            if (Number(x.innerHTML) < Number(y.innerHTML)) {
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
                                            $(document).ready(function () {
                                                $('#bulkTable').DataTable({
                                                    "order": [[3, "asc"]]
                                                });
                                            });

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