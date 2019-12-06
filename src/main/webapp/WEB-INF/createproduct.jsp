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
        <title>Create product</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>

    <body>
        <div>
            <form name="Back" action="FrontController" method = "POST">
                <input type="hidden" name="cmd" value="get_view">
                <input type="hidden" name="view" value="productcatalog">
                <input type="submit" value="Back">
            </form>

            <h1>Create product for category: ${category1}</h1>

            <form name="create" action="FrontController" method = "POST" enctype = "multipart/form-data">
                <input type="hidden" name="cmd" value="create_product">
                <input type="hidden" name="category" value="${category1}">
                <input type="reset" onclick="removeThumbnails();">
                <br><br>
                <c:if test="${error != null}">
                    <div class="form-alert"><h4>${error}</h4></div>
                        </c:if>
                <h4>Fields marked with <font color="red">*</font> is required.</h4>
                <label for="item_number"><b>Item Number <font color="red">*</font></b></label>
                <br>
                <input type="number" name="item_number" min = "0" max = "2,147,483,647" value = "<c:if test="${not empty product.getItemnumber()}">${product.getItemnumber()}</c:if>" required>
                    <br><br>
                    <label for="product_name"><b>Product Name <font color="red">*</font></b></label>
                    <br>
                    <input type="text" name="product_name" value = "<c:if test="${not empty product.getName()}">${product.getName()}</c:if>" required>
                    <br><br>
                    <label for="brand"><b>Brand <font color="red">*</font></b></label>
                    <br>
                    <input type="text" name="brand" value = "<c:if test="${not empty product.getBrand()}">${product.getBrand()}</c:if>"  required>
                    <br><br>
                    <label for="product_desc"><b>Description</b></label>
                    <br>
                    <textarea style="resize:none" name="product_desc" rows="4" cols="20"><c:if test="${not empty product.getDescription()}">${product.getDescription()}</c:if></textarea>
                    <br><br>
                    <label for="supplier"><b>Supplier</b></label>
                    <br>
                    <input type="text" name="supplier" value = "<c:if test="${not empty product.getSupplier()}">${product.getSupplier()}</c:if>"  >
                    <br><br>
                    <label for="seo_text"><b>SEO text</b></label>
                    <br>
                    <input type="text" name="seo_text" value = "<c:if test="${not empty product.getSEOText()}">${product.getSEOText()}</c:if>" >
                    <br><br>
                    <label for="product_tags"><b>Tags</b></label>
                    <br>
                    <input type="text" name="product_tags" placeholder="tag1, tag2, tag3" value = "<c:if test="${not empty product.getTags()}">${product.getTagsAsString()}</c:if>"  >
                    <br><br>
                    <h3>Category: ${category1}</h3>
                <label for="attributes"><b>Attributes</b></label>
                <br>
                <c:forEach items="${categories}" var="cat">
                    <c:if test="${cat.getCategoryname().equals(category1)}">
                        <c:forEach items="${cat.getAttributes()}" var="attr">
                            <br>
                            <label for="attribute_name"><b>${attr}</b></label>
                            <br>
                            <input type="hidden" name="attributename" value ="${attr}">
                            <input type="text" name="attributes" value = "<c:if test="${not empty product.getCategoryAttributes()}">${product.getCategoryAttributes().get(attr)}</c:if>">
                        </c:forEach>
                    </c:if>
                </c:forEach>
                <br><br>
                <label for="file"><b>Picture</b></label>
                <br>
                <input type="file" id="files" name = "file" multiple accept=".jpg, .png"/><br>
                <br>
                <output id="list"></output>
                <br><br>
                <label for="status"><b>Status</b></label>
                <br>
                <input type="text" name="status" value="0" readonly>
                <br><br>
                <input class="createbtn" id="createbtn" type="submit" value="Create">

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
