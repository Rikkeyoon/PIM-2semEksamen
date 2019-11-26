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

        <div>
            <form name="update" action="FrontController" method = "POST" enctype = "multipart/form-data">
                <input type="hidden" name="cmd" value="update_product">

                <h1>Edit product</h1>
                <br><br>

                <label for="product_id"><b>ID</b></label>
                <p>${product.getId()} </p>
                <br>
                <label for="product_name"><b>Product Name</b></label>
                <br>
                <input type="text" name="product_name" 
                       value="${product.getName()}" required>
                <br><br>
                <label for="product_desc"><b>Description</b></label>
                <br>
                <textarea name="product_desc" rows="4" cols="20" 
                          style="resize: none; width: 25%;" required="required">
                    ${product.getDescription()}
                </textarea>
                <br><br>
                <label for="product_category"><b>Category</b></label>
                <br>
                <input type="text" name="product_category" id="category" 
                       onkeyup="validateCategory();" 
                       value="${product.getCategory().getCategoryname()}" required>
                <div id="divValidateCategory"></div>
                <br>
                <c:forEach items="${product.getCategoryAttributes().keySet()}" 
                           var="key"> 
                    <div>
                        <label for="category_attribute"><b>${key}</b></label>
                        <br>
                        <textarea name="${key}" rows="4" cols="20" style="resize: none; 
                                  width: 25%;" required="required">
                            ${product.getCategoryAttributes().get(key)}  
                        </textarea>
                    </div>
                    <br>
                </c:forEach>

                <c:forEach items="${product.getImages()}" var="image"> 
                    <span>
                        <img width = "100" alt= "Picture not found" src = "${image.getKey()}">
                        <input type="radio" name="fileSelected" value="${image.getKey()}" required>
                    </span>
                </c:forEach>
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
                            <img width = "100" alt= "Picture not found" src = "${image.getKey()}">
                            <input type="checkbox" name="delete_chosen_pics" value="${image.getKey()}" />
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
