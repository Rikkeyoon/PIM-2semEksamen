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
    </head>

    <body>
        <div>
            <form name="create" action="FrontController" method = "POST">
                <input type="hidden" name="cmd" value="create">

                <h1>Create product</h1>
                <br><br>

                <label for="product_id"><b>ID</b></label>
                <input type="text" name="product_id" id="id" onkeyup="validateId();" required>
                <div id="divValidateId"></div>
                <br>
                <label for="product_name"><b>Product Name</b></label>
                <input type="text" name="product_name" required>
                <br><br>
                <label for="product_desc"><b>Description</b></label>
                <textarea name="product_desc" rows="4" cols="20" required="required">
                </textarea>
                <br><br>
                <label for="product_category"><b>Category</b></label>
                <input type="text" name="product_category" id="category" 
                       onkeyup="validateCategory();" required>
                <div id="divValidateCategory"></div>
                <br>

                <!-- Exception handling -->
                <c:if test="${error != null}">
                    <div class="form-alert">${error}</div>
                </c:if>
                    
                <input class="createbtn" id="createbtn" type="submit" value="Create" 
                       onclick="confirmation()" disabled='disabled'/>

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
            
            function confirmation() {
                alert("Product created succesfully!");
            }

        </script>
            
    </body>
</html>
