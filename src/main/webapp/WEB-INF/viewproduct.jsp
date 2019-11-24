<%-- 
    Document   : view product
    Created on : 15 Nov 2019, 09:18:11
    Author     : zarpy
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View product</title>
    </head>
    <body>
        <form name="back" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="productcatalog">
            <input type="submit" value="Back" />
        </form>


        <form name="update" action="FrontController" method = "POST">
            <h1>View product</h1>
            <br><br>

            <label for="product_id"><b>ID</b></label>
            <p>${product.getId()} </p>

            <br>
            <label for="product_name"><b>Product Name</b></label>
            <p>${product.getName()} </p>
            <br>
            <label for="product_desc"><b>Description</b></label>
            <br>
            <p>${product.getDescription()}</p>
            <br>
            <label for="product_tags"><b>Tags</b></label>
            <br>
            <p>${product.getTagsAsString()}</p>
            <br>
            <label for="product_category"><b>Category</b></label>
            <p>${product.getCategory().getCategoryname()}</p>
            <br>
            <c:forEach items="${product.getCategoryAttributes().keySet()}" 
                       var="key"> 
                <div>
                    <b>${key}</b>
                    <p>${product.getCategoryAttributes().get(key)}</p>
                </div>
                <br>
            </c:forEach>
            <br>

            <c:forEach items="${product.getImages()}" var="image"> 
                <img width = "100" alt= "Picture not found" src = "${image.getKey()}">
            </c:forEach>

        </form>
        <form name="update" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="editproduct">
            <input type="submit" value="Edit">
        </form>
    </body>
</html>