<%-- 
    Document   : tableview
    Created on : 5. dec. 2019, 12.55.34
    Author     : carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<div class="container-table">
    <div class="wrap-table2">
        <div class="table">
            <table id="catalogTable" border="1">
                <thead>
                    <tr class="table-head">
                        <th>Picture</th>
                        <th onclick="sortNumberColumns(1)">ID</th>
                        <th onclick="sortNumberColumns(2)">Item Number</th>
                        <th onclick="sortAlphabeticalTable(3)">Name</th>
                        <th onclick="sortAlphabeticalTable(4)">Brand</th>
                        <th>Description</th>
                        <th onclick="sortAlphabeticalTable(6)">Category</th>
                        <th onclick="sortAlphabeticalTable(7)">Supplier</th>
                        <th>SEO text</th>
                        <th onclick="sortNumberColumns(9)">Status</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${catalog}" var="product">
                        <tr>
                            <td style="text-align: center;">
                                <form name="view_product" action="FrontController" method = "POST">
                                    <input type="hidden" name="cmd" value="get_view">
                                    <input type="hidden" name="view" value="viewproduct">
                                    <input type="hidden" value="${product.getId()}" name="product_id"/>
                                    <button type="submit" class="imgbtn">
                                        <c:choose>
                                            <c:when test="${not empty product.getImages()}">
                                                <c:choose>
                                                    <c:when test="${!product.getPrimaryImage().equals('')}">
                                                        <img style="image-resolution: 300dpi; max-height: 100px; max-width: 100px;" 
                                                             alt= "Picture not found" src = "${product.getPrimaryImage()}?resize=480:*"> 
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img width = "100" alt= "Picture not found" src = "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574764086/defaut_vignette_carre_xavv98.jpg">
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when> 
                                            <c:otherwise>
                                                <img width = "100" alt= "Picture not found" src = "https://res.cloudinary.com/dmk5yii3m/image/upload/v1574764086/defaut_vignette_carre_xavv98.jpg">
                                            </c:otherwise>
                                        </c:choose>
                                    </button>
                                </form>
                            </td>
                            <td>${product.getId()}</td>
                            <td>${product.getItemnumber()}</td>
                            <td>${product.getName()}</td>
                            <td>${product.getBrand()}</td>
                            <td>${product.getDescription()}</td>
                            <td>${product.getCategory().getCategoryname()}</td>
                            <td>${product.getSupplier()}</td>
                            <td>${product.getSEOText()}</td>
                            <td>${product.getStatus()}</td>
                            <td><form name="update" action="FrontController" method = "POST">
                                    <input type="hidden" name="cmd" value="get_view">
                                    <input type="hidden" name="view" value="updateproduct">
                                    <input type="hidden" value="${product.getId()}" name="product_id"/>
                                    <button type="submit" class="btn">
                                        <i class="far fa-edit" style="cursor: pointer"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript"  src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"  src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<script>
    if(document.getElementById("#catalogTable") != null){
    $(document).ready(function(){
    $('#catalogTable').dataTable({"searching": false, "ordering": false});
    });
    }

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
