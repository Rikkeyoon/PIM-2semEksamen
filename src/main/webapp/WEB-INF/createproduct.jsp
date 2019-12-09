<%-- 
    Document   : createproduct.jsp
    Created on : 11. nov. 2019, 11.34.41
    Author     : Rikke
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>INFOMERGE | Create Product</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>

    <body>
        <!-- Navigation bar -->
        <%@include file="includes/navigationbar.jsp" %>
        
        <!-- Sidebar -->
        <div id="sidebar" class="sidebar">
            <a href="javascript:void(0)" class="closebtn" onclick="closeSidebar()">&times;</a>
            <div class="padded">
                <form name="get_product_catalog" action="FrontController" method = "POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="productcatalog">
                    <button type="submit" class="viewcatalogbtn"><span>Products</span></button>
                </form>
                <form name="view_categories" action="FrontController" method="POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="viewcategories">
                    <button type="submit" class="viewcatalogbtn">Categories</button>
                </form>
                <form name="bulkEdit" action="FrontController" method="POST">
                    <input type="hidden" name="cmd" value="get_view">
                    <input type="hidden" name="view" value="choosebulkedit">
                    <button type="submit" class="viewcatalogbtn">Bulk Edit</button>
                </form>
                <br>
            </div>
        </div>

        <!-- Main content -->
        <div id="main">
            <br><br>
            <form name="create" action="FrontController" method = "POST" enctype = "multipart/form-data">
                <input type="hidden" name="cmd" value="create_product">
                <input type="hidden" name="category" value="${category1}">
                <input type="reset" onclick="removeThumbnails();">
                <h1 style="text-align: center">Create product for category: ${category1}</h1>
                <div class="container text-center">
                    <div class="row">
                        <div class="col col-lg-12">
                            <c:if test="${error != null}">
                                <div class="form-alert"><h4>${error}</h4></div>
                                    </c:if>
                        </div>
                    </div>
                    <h4>Fields marked with <font color="red">*</font> is required.</h4>
                    <div class="row">
                        <div class="col col-lg-2"></div>
                        <div class="col col-lg-4">
                            <label for="item_number"><b>Item Number <font color="red">*</font></b></label>
                            <br>
                            <input class="form-control input-sm" type="number" name="item_number" min = "0" max = "2,147,483,647" value = "<c:if test="${not empty product.getItemnumber()}">${product.getItemnumber()}</c:if>" required>
                                <br><br>
                            </div>
                            <div class="col col-lg-4">
                                <label for="product_name"><b>Product Name <font color="red">*</font></b></label>
                                <br>
                                <input class="form-control input-sm" type="text" name="product_name" value = "<c:if test="${not empty product.getName()}">${product.getName()}</c:if>" required>
                                <br><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col col-lg-2"></div>
                            <div class="col col-lg-4">
                                <label for="brand"><b>Brand <font color="red">*</font></b></label>
                                <br>
                                <input class="form-control input-sm" type="text" name="brand" value = "<c:if test="${not empty product.getBrand()}">${product.getBrand()}</c:if>"  required>
                                <br><br>
                            </div>
                            <div class="col col-lg-4">
                                <label for="supplier"><b>Supplier</b></label>
                                <br>
                                <input class="form-control input-sm" type="text" name="supplier" value = "<c:if test="${not empty product.getSupplier()}">${product.getSupplier()}</c:if>"  >
                                <br><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col col-lg-2"></div>
                            <div class="col col-lg-4">
                                <label for="seo_text"><b>SEO text</b></label>
                                <br>
                                <input class="form-control input-sm" type="text" name="seo_text" value = "<c:if test="${not empty product.getSEOText()}">${product.getSEOText()}</c:if>" >
                                <br><br>
                            </div>
                            <div class="col col-lg-4">
                                <label for="product_tags"><b>Tags</b></label>
                                <br>
                                <input class="form-control input-sm" type="text" name="product_tags" placeholder="tag1, tag2, tag3" value = "<c:if test="${not empty product.getTags()}">${product.getTagsAsString()}</c:if>"  >
                                <br><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col col-lg-12">
                                <label for="product_desc"><b>Description</b></label>
                                <br>
                                <textarea style="resize:none; width: 50%;" name="product_desc" rows="4" cols="20"><c:if test="${not empty product.getDescription()}">${product.getDescription()}</c:if></textarea>
                                <br><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col col-lg-12">
                                <h3>Category: ${category1}</h3>
                        </div>
                    </div>
                    <label for="attributes"><b>Attributes</b></label>
                    <br>
                    <c:set var = "firstOrSecond" scope = "session" value = "${1}"></c:set>
                    <c:forEach items="${categories}" var="cat">
                        <c:if test="${cat.getCategoryname().equals(category1)}">
                            <c:forEach items="${cat.getAttributes()}" var="attr">
                                <c:if test="${firstOrSecond == '1'}">
                                    <div class="row">
                                        <div class="col col-lg-2"></div>
                                    </c:if>
                                    <div class="col col-lg-4">
                                        <br>
                                        <label for="attribute_name"><b>${attr}</b></label>
                                        <br>
                                        <input type="hidden" name="attributename" value ="${attr}">
                                        <input class="form-control input-sm" type="text" name="attributes" value = "<c:if test="${not empty product.getCategoryAttributes()}">${product.getCategoryAttributes().get(attr)}</c:if>">
                                        </div>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${firstOrSecond == '0'}">
                                    </div>
                                    <c:set var = "firstOrSecond" scope = "session" value = "${1}"></c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var = "firstOrSecond" scope = "session" value = "${0}"></c:set>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                    <br><br>
                    <label for="file"><b>Picture</b></label>
                    <br>
                    <div style="text-align:center; margin: auto; width: 200px;">
                        <input type="file" id="files" name = "file" multiple accept=".jpg, .png"/><br>
                    </div>
                    <br>

                    <output id="list"></output>
                    <br><br>
                    <button class="btn btn-default" type="reset" onclick="removeThumbnails(  );"><i class="glyphicon glyphicon-refresh" style="margin-right: 10px;"></i>Reset</button>
                    <br><br>
                    <button class="btn btn-default" type="submit" name ="cmd" value ="createproduct"><i class="glyphicon glyphicon-floppy-disk" style="margin-right: 10px;"></i>Create</button>
                    <br><br>
                </div>
            </form>
        </div>

        <!-- JavaScript functions -->
        <script type="text/javascript">
            function handleFileSelect(evt) {
                document.getElementById('list').innerHTML = "";
                var files = evt.target.files;

                // Loop through the FileList and render image files as thumbnails.
                for (var i = 0, f; f = files[i]; i++) {

                    // Only process image files.
                    if (!f.type.match('image.*')) {
                        continue;
                    }

                    var reader = new FileReader();

                    // Closure to capture the file information.
                    reader.onload = (function (theFile) {
                        return function (e) {
                            // Render thumbnail.
                            var span = document.createElement('span');
                            span.innerHTML =
                                    [
                                        '<span style="height: 75px; border: 1px solid #000; margin: 5px"><img style="height: 75px; border: 1px solid #000; margin: 5px" src="', e.target.result, '" title="', escape(theFile.name), '"/><input type="radio" name="fileSelected" value="', escape(theFile.name), '" required></span>'
                                    ].join('');

                            document.getElementById('list').insertBefore(span, null);
                        };
                    })(f);

                    // Read in the image file as a data URL.
                    reader.readAsDataURL(f);
                }
            }

            document.getElementById('files').addEventListener('change', handleFileSelect, false);

            function removeThumbnails() {
                var empty = document.getElementById('list');
                empty.innerHTML = [' '].join('');
                document.getElementById('list').insertBefore(empty, null);
            }
            ;

            function validateID() {
                var id = $("#id").val();
                var idformat = /[0-9]/;

                if (!id.match(idformat)) {
                    $("#createbtn").attr('disabled', 'disabled');
                    $("#divValidateId").html("Invalid Id").addClass('form-alert');
                } else {
                    $("#createbtn").removeAttr('disabled');
                    $("#divValidateId").html("").removeClass('form-alert');
                }
            }
        </script>
    </body>
</html>
