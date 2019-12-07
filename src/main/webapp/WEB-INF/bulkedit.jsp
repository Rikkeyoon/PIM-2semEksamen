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
        <title>Bulk Edit</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>

    <body>
         <!-- Navigation bar -->
        <%@include file="includes/navigationbar.jsp" %>
        <!-- View navigation bar (specific to this page) -->
        <nav class="view-nav">
            <form name="back" action="FrontController" method = "POST">
                <input type="hidden" name="cmd" value="get_view">
                <input type="hidden" name="view" value="productcatalog">
                <input type="submit" value="Back" />
            </form>
        </nav>
        <br>
        <h1>Bulk edit: ${category1.getCategoryname()}</h1>
        <br><br>
        <c:if test="${error != null}">
            <div class="form-alert"><h4>${error}</h4>
            </div>
        </c:if>
        <form name="update" action="FrontController" method = "POST">
            <table id="catalogTable" border="1">
                <thead>
                    <tr>
                        <th onclick="sortNumberColumns(2)">Item Number&#8661;</th>
                        <th onclick="sortAlphabeticalTable(3)">Name&#8661;</th>
                        <th onclick="sortAlphabeticalTable(4)">Brand&#8661;</th>
                        <th>Description</th>
                        <th onclick="sortAlphabeticalTable(7)">Supplier&#8661;</th>
                        <th>SEO text</th>
                        <th onclick="sortNumberColumns(9)">Status&#8661;</th>
                        <th>tags</th>
                            <c:forEach items="${category1.getAttributes()}" var = "attribute">
                            <th>Attribute:${attribute}</th>
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
                                <input type="checkbox" name="bulkEditSelected" value ="${product.getId()}">
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br><br>
            <div class="block" style="width: 50%">
                <h3>New Values</h3>
                <label for="brand"><b>Brand</b></label>
                <br>
                <input type="text" name="brand">
                <br><br>
                <label for="supplier"><b>Supplier</b></label>
                <br>
                <input type="text" name="supplier">
                <br><br>
                <label for="seo_text"><b>SEO text</b></label>
                <br>
                <input type="text" name="seo_text">
                <br><br>
                <label for="product_tags"><b>Tags</b></label>
                <br>
                <input type="text" name="product_tags" size="50">
                <br><br>
                <label for="attributes"><b>Attributes</b></label>
                <br>
                <c:forEach items="${category1.getAttributes()}" var="attr">
                    <br>
                    <label for="attribute_name"><b>${attr}</b></label>
                    <br>
                    <input type="hidden" name="attributename" value ="${attr}">
                    <input type="text" name="attributes" value = "<c:if test="${not empty product.getCategoryAttributes()}">${product.getCategoryAttributes().get(attr)}</c:if>">
                </c:forEach>
                <br><br>
                <input type="hidden" name="delcmd" value="bulkdelete">
                <button type="submit" name ="cmd" value ="bulkedit">Bulk Edit</button>
                <button type="submit" name="cmd" value ="delete_product">Delete Products</button>
        </form>
    </div>

    <br><br>
    <script>
        function sortAlphabeticalTable(n) {
            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("catalogTable");
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
            table = document.getElementById("catalogTable");
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
            $('#catalogTable').DataTable({
                "order": [[3, "asc"]]
            });
        });
    </script>
</body>
</html>