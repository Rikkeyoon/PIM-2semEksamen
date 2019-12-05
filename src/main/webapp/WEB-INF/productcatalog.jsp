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
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>

    <body>
        <h1>Product Catalog</h1>
        <br>
        
        <h3>Search</h3>
        <form name="search" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="search_product">
            <label for="product_id"><b>Product ID</b></label>
            <br>
            <input type="text" name="product_id">
            <input class="searchbtn" id="searchbtn" type="submit" value="Search"/>
            <br>

            <label for="item_number"><b>Item number</b></label>
            <br>
            <input type="text" name="item_number">
            <input class="searchbtn" id="searchbtn" type="submit" value="Search"/>
            <br>

            <label for="product_name"><b>Product Name</b></label>
            <br>
            <input type="text" name="product_name">
            <input class="searchbtn" id="searchbtn" type="submit" value="Search"/>
            <br>

            <label for="product_category"><b>Category</b></label>
            <br>
            <input type="text" name="product_category">
            <input class="searchbtn" id="searchbtn" type="submit" value="Search"/>
            <br>

            <label for="brand"><b>Brand</b></label>
            <br>
            <input type="text" name="brand">
            <input class="searchbtn" id="searchbtn" type="submit" value="Search"/>
            <br>

            <label for="product_tag"><b>Tag</b></label>
            <br>
            <input type="text" name="product_tag">
            <input class="searchbtn" id="searchbtn" type="submit" value="Search"/>
            <br>

            <label for="supplier"><b>Supplier</b></label>
            <br>
            <input type="text" name="supplier">
            <input class="searchbtn" id="searchbtn" type="submit" value="Search"/>
            <br>

        </form>

        <form name="get_product_catalog" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="productcatalog">
            <input type="submit" value="Reset"/>
            <br>
        </form>

        <br><br>

        <table id="catalogTable" border="1">
            <thead>
                <tr>
                    <th>Picture</th>
                    <th onclick="sortNumberColumns(1)">ID&#8661;</th>
                    <th onclick="sortNumberColumns(2)">Item Number&#8661;</th>
                    <th onclick="sortAlphabeticalTable(3)">Name&#8661;</th>
                    <th onclick="sortAlphabeticalTable(4)">Brand&#8661;</th>
                    <th>Description</th>
                    <th onclick="sortAlphabeticalTable(6)">Category&#8661;</th>
                    <th onclick="sortAlphabeticalTable(7)">Supplier&#8661;</th>
                    <th>SEO text</th>
                    <th onclick="sortNumberColumns(9)">Status&#8661;</th>
                    <th>View Product</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${catalog}" var="product">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${not empty product.getImages()}">
                                    <c:choose>
                                        <c:when test="${!product.getPrimaryImage().equals('')}">
                                            <img width = "100" alt= "Picture not found" src = "${product.getPrimaryImage()}"> 
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
                        <td>
                            <form name="view_product" action="FrontController" method = "POST">
                                <input type="hidden" name="cmd" value="get_view">
                                <input type="hidden" name="view" value="viewproduct">
                                <input type="hidden" value="${product.getId()}" name="product_id"/>
                                <input type="submit" value="View product">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
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
        
        <form name="download" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="download_catalog">
            <input type="submit" value="Download catalog">
        </form>

        <form name="view_categories" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="viewcategories">
            <input type="submit" value="View categories" />
        </form>
        <br>

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
        </script>
    </body>
</html>