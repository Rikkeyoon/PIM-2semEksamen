<%-- 
    Document   : producteditor
    Created on : 11 Nov 2019, 11:23:01
    Author     : zarpy, carol
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
            <input type="hidden" name="cmd" value="view_product">
            <input type="hidden" value="${product.getId()}" name="product_id"/>
            <input type="submit" value="Back">
        </form>

        <div>
            <form name="update" action="FrontController" method = "POST">
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
                        <textarea name="attribute_value" rows="4" cols="20" 
                                  style="resize: none; width: 25%;" required="required">
                            ${product.getCategoryAttributes().get(key)}  
                        </textarea>
                    </div>
                    <br>
                </c:forEach>

                <!-- Exception handling -->
                <c:if test="${error != null}">
                    <div class="form-alert">${error}</div>
                </c:if>

                <input class="updatebtn" type="submit" value="Save Changes" onclick="confirmation()"/>
            </form>
            <form name="update" id="delform" action="FrontController" method = "POST"> 
                <input type="hidden" name="cmd" id="delcmd" value="">
                <input class="deletebtn" type="button" value="Delete Product" onclick="dconfirmation()"/>
            </form>

        </div>

        <!-- JavaScript functions -->
        <script>
            function validateID() {
                var id = $("#product_id").val();
                var idformat = /[0-9]/;

                if (!id.match(idformat)) {
                    $("#updatebtn").attr('disabled', 'diasabled');
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
                    $("#updatebtn").attr('disabled', 'diasbled');
                    $("#divValidateCategory").html("Invalid Category").addClass('form-alert');
                } else {
                    $("#updatebtn").removeAttr('disabled');
                    $("#divValidateCategory").html("").removeClass('form-alert');
                }
            }

            function confirmation() {
                alert("Product updated succesfully!");
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
