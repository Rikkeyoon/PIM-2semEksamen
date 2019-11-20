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
        <title>Product catalog</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>

    <body>
        <div>
            <form name="back" action="FrontController" method = "POST">
                <input type="hidden" name="cmd" value="view_catalog">
                <input type="submit" value="Back" />
            </form>
            
            <form name="create" action="FrontController" method = "POST">
                <input type="hidden" name="cmd" value="create_product">

                <h1>Create product</h1>
                <br><br>

                <label for="product_id"><b>ID</b></label>
                <br>
                <input type="text" name="product_id" id="id" onkeyup="validateId();" required>
                <div id="divValidateId"></div>
                <br>
                <label for="product_name"><b>Product Name</b></label>
                <br>
                <input type="text" name="product_name" required>
                <br><br>
                <label for="product_desc"><b>Description</b></label>
                <br>
                <textarea name="product_desc" rows="4" cols="20" required="required">
                </textarea>
                <br><br>
                <label for="product_category"><b>Category</b></label>
                <br>
                <input type="text" name="product_category" id="category" 
                       onkeyup="validateCategory();" required>
                <div id="divValidateCategory"></div>
                <br>

                <input class="createbtn" id="createbtn" type="submit" value="Create" />
            </form>
        </div>

        <!-- JavaScript functions -->
        <script>
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

            function validateCategory() {
                var category = $("#category").val();
                var categoryformat = /[a-z]/;

                if (!category.match(categoryformat)) {
                    $("#createbtn").attr('disabled', 'disabled');
                    $("#divValidateCategory").html("Invalid Category").addClass('form-alert');
                } else {
                    $("#createbtn").removeAttr('disabled');
                    $("#divValidateCategory").html("").removeClass('form-alert');
                }
            }
        </script>
    </body>
</html>
