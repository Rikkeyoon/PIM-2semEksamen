<%-- 
    Document   : productcatalog
    Created on : 10. nov. 2019, 13.10.28
    Author     : Rikke, carol
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
        <br>

        <form name="search" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="search_product">
            <label for="product_id"><b>Product ID</b></label>
            <br>
            <input type="text" name="product_id">
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
            
            <label for="product_tag"><b>Tag</b></label>
            <br>
            <input type="text" name="product_tag">
            <input class="searchbtn" id="searchbtn" type="submit" value="Search"/>
            <br>
        </form>

        <form name="get_product_catalog" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="productcatalog">
            <input type="submit" value="Reset"/>
            <br>
        </form>

        <table border="1">
            <thead>
                <tr>
                    <th>Picture</th>
                    <th>ID</th>
                    <th>Item Number</th>
                    <th>Name</th>
                    <th>Brand</th>
                    <th>Description</th>
                    <th>Category</th>
                    <th>Supplier</th>
                    <th>SEO text</th>
                    <th>Status</th>
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
        <form name="create" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="createproduct">
            <input type="submit" value="Create new product" />
        </form>
        <br>

        <form name="create_category" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="createcategory">
            <input type="submit" value="Create new category" />
        </form>
        <br>

        <form name="edit_category" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="editcategory">
            <input type="submit" value="Edit category" />
        </form>
        <br>
    </body>
</html>