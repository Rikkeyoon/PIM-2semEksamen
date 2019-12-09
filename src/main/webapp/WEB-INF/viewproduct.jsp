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
        <title>View product</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://kit.fontawesome.com/6341639fb9.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <!-- Navigation bar -->
        <%@include file="includes/navigationbar.jsp" %>

        <!-- Main content -->
        <div id="main">
            <h1 style="text-align: center;">View product</h1>
            <br><br>
            <div class="container text-center">
                <c:forEach items="${product.getImages()}" var="image"> 
                    <img width = "100" alt= "Picture not found" src = "${image.getUrl()}">
                </c:forEach>
                <br><br>
                <div class="row">
                    <div class="col col-lg-2"></div>
                    <div class="col col-lg-4">
                        <label for="product_id"><b>ID</b></label>
                        <p>${product.getId()} </p>
                        <br>
                    </div>
                    <div class="col col-lg-4">
                        <label  for="status"><b>Status</b></label>
                        <p>${product.getStatus()} </p>
                        <br>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-2"></div>
                    <div class="col col-lg-4">
                        <label for="item_number"><b>Item Number</b></label>
                        <p>${product.getItemnumber()} </p>
                        <br>
                    </div>
                    <div class="col col-lg-4">
                        <label for="product_name"><b>Product Name</b></label>
                        <p>${product.getName()} </p>
                        <br>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-2"></div>
                    <div class="col col-lg-4">
                        <label for="brand"><b>Brand</b></label>
                        <p>${product.getBrand()} </p>
                        <br>
                    </div>
                    <div class="col col-lg-4">
                        <label for="supplier"><b>Supplier</b></label>
                        <p>${product.getSupplier()} </p>
                        <br>
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-2"></div>
                    <div class="col col-lg-4">
                        <label for="seo_text"><b>SEO text</b></label>
                        <p>${product.getSEOText()} </p>
                        <br>
                    </div>
                    <div class="col col-lg-4">
                        <label for="product_tags"><b>Tags</b></label>
                        <br>
                        <c:if test="${!product.getTags().isEmpty()}">
                            <c:forEach items="${product.getTags()}" var="key">
                                <form name="search" action="FrontController" method = "POST">
                                    <input type="hidden" name="cmd" value="search_product">
                                    <input type="hidden" name="product_tag" value = "${key}">
                                    <input style = "  background:none; border:none; font-size:1em; color:black;" class="searchbtn" id="searchbtn" type="submit" value="${key}"/>
                                </form>
                            </c:forEach>
                        </c:if>
                        <br><br> 
                    </div>
                </div>
                <div class="row">
                    <div class="col col-lg-12">
                        <label for="product_desc"><b>Description</b></label>
                        <br>
                        <p>${product.getDescription()}</p>
                        <br>
                    </div>
                </div>

                <label for="product_category"><h4><b>Category</b></h4></label>
                <p>${product.getCategory().getCategoryname()}</p>
                <br>
                <div class="row">
                    <div class="col col-lg-12">
                        <h3>Attributes</h3>
                    </div>
                </div>
                <c:set var = "firstOrSecond" scope = "session" value = "${1}"></c:set>
                <c:forEach items="${product.getCategoryAttributes().keySet()}" 
                           var="key"> 
                    <c:if test="${firstOrSecond == '1'}">
                        <div class="row">
                            <div class="col col-lg-2"></div>
                        </c:if>
                        <div class="col col-lg-4">
                            <label for="category_attribute"><b>${key}</b></label>
                            <p>${product.getCategoryAttributes().get(key)}</p>
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
            <form name="update" action="FrontController" method = "POST">
                <input type="hidden" name="cmd" value="get_view">
                <input type="hidden" name="view" value="updateproduct">
                <button class="btn btn-default" type="submit" class="btn">
                    <i class="glyphicon glyphicon-edit" style="margin-right: 10px;"></i>Edit product
                </button>
            </form>
            <br><br>
        </div>
    </body>
</html>