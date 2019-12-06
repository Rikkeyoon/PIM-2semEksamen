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
        <title>Product Editor</title>
    </head>

    <body>
        <form name="Back" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="viewproduct">
            <input type="hidden" value="${product.getId()}" name="product_id"/>
            <input type="submit" value="Back">
        </form>
        <h1>Edit product</h1>
        <div>
            <form name="update" action="FrontController" method = "POST" enctype = "multipart/form-data">
                <input type="hidden" name="cmd" value="update_product">
                <input type="hidden" name="product_category" value="${product.getCategory().getCategoryname()}">
                <br>
                <c:if test="${error != null}">
                    <div class="form-alert"><h4>${error}</h4>
                    </div>
                </c:if>
                <h4>Fields marked with <font color="red">*</font> is required.</h4>
                <label for="product_id"><b>ID</b></label>
                <input type="hidden" value="${product.getId()}" name="product_id"/>
                <p>${product.getId()}</p>
                <label for="item_number"><b>Item Number<font color="red">*</font></b></label>
                <br>
                <input type="text" name="item_number" value="${product.getItemnumber()}" required>
                <br>
                <label for="product_name"><b>Product Name<font color="red">*</font></b></label>
                <br>
                <input type="text" name="product_name" value="${product.getName()}" required>
                <br>
                <label for="brand"><b>Brand<font color="red">*</font></b></label>
                <br>
                <input type="text" name="brand" value="${product.getBrand()}" required>
                <br><br>
                <label for="product_desc"><b>Description</b></label>
                <br>
                <textarea name="product_desc" rows="4" cols="20" 
                          style="resize: none; width: 25%;">${product.getDescription()}</textarea>
                <br><br>
                <label for="supplier"><b>Supplier</b></label>
                <br>
                <input type="text" name="supplier" value="${product.getSupplier()}" >
                <br>
                <label for="seo_text"><b>SEO text</b></label>
                <br>
                <input type="text" name="seo_text" value="${product.getSEOText()}" >
                <br><br>
                <label for="product_tags"><b>Tags</b></label>
                <br>
                <c:choose>
                    <c:when test="${not empty product.getTags()}">
                        <input type="text" name="product_tags" size="50" value = "${product.getTagsAsString()}">
                    </c:when>
                    <c:otherwise>
                        <input type="text" name="product_tags" size="50">
                    </c:otherwise>
                </c:choose>
                <br><br>
                <h3>Category: ${product.getCategory().getCategoryname()}</h3>
                <label for="attributes"><b>Attributes</b></label>
                <br>
                <c:forEach items="${product.getCategory().getAttributes()}" var="attr">
                    <br>
                    <label for="attribute_name"><b>${attr}</b></label>
                    <br>
                    <input type="hidden" name="attributename" value ="${attr}">
                    <input type="text" name="attributes" value = "<c:if test="${not empty product.getCategoryAttributes()}">${product.getCategoryAttributes().get(attr)}</c:if>">
                </c:forEach>
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
                <label for="status"><b>Status</b></label>
                <input type="hidden" value="${product.getStatus()}" name="status"/>
                <p>${product.getStatus()}</p>
                <br><br> 

                <label for="file"><b>Add more pictures</b></label>
                <br>
                <input type="file" id="files" name = "file" multiple accept=".jpg, .png"/><br>
                <output id="list"></output>
                <br>

                <label><b>Delete pictures</b></label>
                <div id="div_delete_pics">
                    <c:forEach items="${product.getImages()}" var="image">
                        <span>
                            <img width = "100" alt= "Picture not found" src = "${image.getUrl()}">
                            <input type="checkbox" name="delete_chosen_pics" value="${image.getUrl()}" />
                        </span>
                    </c:forEach>
                </div>
                <br><br>

                <input type="reset" onclick="removeThumbnails();">
                <br><br>

                <input class="updatebtn" type="submit" value="Save Changes"/>
            </form>
            <br>
            <form name="update" id="delform" action="FrontController" method = "POST"> 
                <input type="hidden" name="cmd" id="delcmd" value="">
                <input type="hidden" name="delcmd" value ="deleteProduct">
                <input class="deletebtn" type="button" value="Delete Product" 
                       onclick="dconfirmation()"/>
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

        </script>
    </body>
</html>
