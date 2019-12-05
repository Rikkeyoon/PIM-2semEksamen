<%-- 
    Document   : viewcategories
    Created on : 3. dec. 2019, 09.29.41
    Author     : Nina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View categories</title>
    </head>
    <body>
        <form name="back" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="productcatalog">
            <input type="submit" value="Back" />
        </form>

        <h1>View categories</h1>


        <table id="categoriesTable" border="1">
            <thead>
                <tr>
                    <th onclick="sortAlphabeticalTable(0)">Categoryname&#8661;</th>
                    <th>Attributes</th>
                    <th>Edit category</th>
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
                            <input type="submit" value="Edit category">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br><br>
    <script>
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
    </script>
</body>
</html>
