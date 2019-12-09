<%-- 
    Document   : producteditor
    Created on : 11 Nov 2019, 11:23:01
    Author     : zarpy, carol, allan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://kit.fontawesome.com/6341639fb9.js" crossorigin="anonymous"></script>
        <title>INFOMERGE | Edit Product</title>
    </head>

    <body>
        <!-- Navigation bar -->
        <%@include file="includes/navigationbar.jsp" %>

        <!-- Sidebar -->
        <%@include file="includes/sidebar.jsp" %>

        <!-- Main content -->
        <div id="main">
            <div class="container text-center" >   
                <form name="update" action="FrontController" method = "POST" enctype = "multipart/form-data">
                    <input type="hidden" name="cmd" value="update_product">
                    <input type="hidden" name="product_category" value="${product.getCategory().getCategoryname()}">
                    <div class="row">
                        <div class="col col-lg-12">
                            <h1 style="text-align: center;">Edit product</h1>
                        </div>

                        <div>
                            <br>
                            <div class="row">
                                <div class="col col-lg-12">
                                    <input type="hidden" value="${product.getId()}" name="product_id"/>
                                    <h5> <b>ID: </b>${product.getId()}</h5>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col col-lg-12">
                                    <c:if test="${error != null}">
                                        <div class="form-alert"><h4>${error}</h4>
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <h4>Fields marked with <font color="red">*</font> is required.</h4>
                            <div class="row">
                                <div class="col col-lg-2"></div>
                                <div class="col col-lg-4">
                                    <label for="item_number"><b>Item Number<font color="red">*</font></b></label>
                                    <br>
                                    <input class="form-control input-sm" type="text" name="item_number" value="${product.getItemnumber()}" required>
                                    <br>
                                </div>
                                <div class="col col-lg-4">
                                    <label for="product_name"><b>Product Name<font color="red">*</font></b></label>
                                    <br>
                                    <input class="form-control input-sm" type="text" name="product_name" value="${product.getName()}" required>
                                    <br>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col col-lg-2"></div>
                                <div class="col col-lg-4">
                                    <label for="brand"><b>Brand<font color="red">*</font></b></label>
                                    <br>
                                    <input class="form-control input-sm" type="text" name="brand" value="${product.getBrand()}" required>
                                    <br><br>
                                </div>
                                <div class="col col-lg-4">
                                    <label for="supplier"><b>Supplier</b></label>
                                    <br>
                                    <input class="form-control input-sm" type="text" name="supplier" value="${product.getSupplier()}" >
                                    <br>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col col-lg-2"></div>
                                <div class="col col-lg-4">
                                    <label for="seo_text"><b>SEO text</b></label>
                                    <br>
                                    <input class="form-control input-sm" type="text" name="seo_text" value="${product.getSEOText()}" >
                                    <br><br>
                                </div>
                                <div class="col col-lg-4">
                                    <label for="product_tags"><b>Tags</b></label>
                                    <br>
                                    <c:choose>
                                        <c:when test="${not empty product.getTags()}">
                                            <input class="form-control input-sm" type="text" name="product_tags" size="50" value = "${product.getTagsAsString()}">
                                        </c:when>
                                        <c:otherwise>
                                            <input class="form-control input-sm" type="text" name="product_tags" size="50">
                                        </c:otherwise>
                                    </c:choose>
                                    <br><br>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col col-lg-12">      
                                <label for="product_desc"><b>Description</b></label>
                                <br>
                                <textarea name="product_desc" rows="4" cols="20" 
                                          style="resize: none; width: 50%;">${product.getDescription()}</textarea>
                                <br><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col col-lg-12">
                                <h3>Category: ${product.getCategory().getCategoryname()}</h3>
                            </div>
                        </div>
                        <label for="attributes"><b>Attributes</b></label>
                        <br>
                        <c:set var = "firstOrSecond" scope = "session" value = "${1}"></c:set>
                        <c:forEach items="${product.getCategory().getAttributes()}" var="attr">
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
                    <c:forEach items="${product.getImages()}" var="image"> 
                        <span>
                            <c:choose>
                                <c:when test="${image.getUrl().equals(product.getPrimaryImage())}">
                                    <img width = "100" alt= "Picture not found" src = "${image.getUrl()}">
                                    <input type="radio" name="fileSelected" value="${image.getUrl()}"
                                           checked="checked" required />
                                </c:when>
                                <c:otherwise>
                                    <img width = "100" alt= "Picture not found" src = "${image.getUrl()}">
                                    <input type="radio" name="fileSelected" value="${image.getUrl()}"
                                           required />
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </c:forEach>
                    <br><br>
                    <br><br> 

                    <div class="row">
                        <div class="col col-lg-12">
                            <label for="file"><b>Add more pictures</b></label>
                            <br>
                            <div style="text-align:center; margin: auto; width: 200px;">
                                <input type="file" id="files" name = "file" multiple accept=".jpg, .png"/><br>
                                <output id="list"></output>
                            </div>
                            <br>
                        </div>
                    </div>


                    <label><b>Delete pictures</b></label>
                    <div id="div_delete_pics">
                        <c:forEach items="${product.getImages()}" var="image">
                            <span>
                                <img width = "100" alt= "Picture not found" src = "${image.getUrl()}">
                                <input type="checkbox" name="delete_chosen_pics" value="${image.getUrl()}"/>
                            </span>
                        </c:forEach>
                    </div>
                    <br><br>

                    <button class="btn btn-default" type="reset" onclick="removeThumbnails(  );"><i class="glyphicon glyphicon-refresh" style="margin-right: 10px;"></i>Reset</button>
                    <br><br>

                    <button class="btn btn-default" type="submit" name ="cmd" value ="update_product"><i class="glyphicon glyphicon-floppy-disk" style="margin-right: 10px;"></i>Save Changes</button>
                    <br><br>
                </form>
                <form name="update" id="delform" action="FrontController" method = "POST"> 
                    <input type="hidden" name="cmd" id="delcmd" value="">
                    <input type="hidden" name="delcmd" value ="deleteProduct">
                    <button class="btn btn-default" type="submit" name="cmd" value ="delete_product" onclick="dconfirmation()">
                        <i class="glyphicon glyphicon-trash" style="margin-right: 10px;"></i>Delete Product</button>
                </form>
                <br><br>
                <br>
            </div>
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
                                        '<span id="thumbnail" style="height: 75px; border: 1px solid #000; margin: 5px"><img style="height: 75px; border: 1px solid #000; margin: 5px" src="', e.target.result, '" title="', escape(theFile.name), '"/><input type="radio" name="fileSelected" value="', escape(theFile.name), '" required></span>'
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

            function validateID() {
                var id = $("#product_id").val();
                var idformat = /[0-9]/;

                if (!id.match(idformat)) {
                    $("#updatebtn").attr('disabled', 'disabled');
                    $("#divValidateId").html("Invalid Id").addClass('form-alert');
                } else {
                    $("#updatebtn").removeAttr('disabled');
                    $("#divValidateId").html("").removeClass('form-alert');
                }
            }

            function validateCategory() {
                var category = $("#product_category").val();
                var categoryformat = /[a-z]/;

                if (!category.match(categoryformat)) {
                    $("#updatebtn").attr('disabled', 'disabled');
                    $("#divValidateCategory").html("Invalid Category").addClass('form-alert');
                } else {
                    $("#updatebtn").removeAttr('disabled');
                    $("#divValidateCategory").html("").removeClass('form-alert');
                }
            }

            function dconfirmation() {
                if (confirm("You are about to delete a product!")) {
                    document.getElementById("delcmd").value = "delete_product";
                    document.getElementById("delform").submit();
                    alert("Product has been deleted!");
                } else {
                    alert("Return to Product page!");
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
