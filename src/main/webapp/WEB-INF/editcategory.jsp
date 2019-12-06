<%-- 
    Document   : editcategory
    Created on : 19. nov. 2019, 17.31.45
    Author     : carol, Allan, Nina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Category</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>
    <body>
        <form name="back" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="productcatalog">
            <input type="submit" value="Back" />
        </form>

        <h1>Edit ${category1.getCategoryname()}</h1>

        <form name="update_attributes" action="FrontController" method="POST">
            <input type="hidden" name="category" value="${category1.getCategoryname()}">
            <input type="hidden" name="cmd" value="edit_category">
            <label for="attributes"><b>Attributes</b></label>
            <br>

            <c:forEach items="${category1.getAttributes()}" var="attr">
                <input type="hidden" name="attr_old" value="${attr}"/>
                <input type="text" name="attr_new" value="${attr}"/>
                <label>Remove:</label>
                <input type="checkbox" name="attr_remove" value="${attr}"/>
                <br>
            </c:forEach>
            <br><br>

            <input type="hidden" name="cmd" value="edit_category">
            <div class="new_attributes">
                <p>New Attribute Name</p>
                <div><input type="text" name="attribute">
                </div>
            </div>

            <button id="add_field_button">Add</button>
            <br><br>
            <input class="updatebtn" type="submit" value="Save Changes"/>
        </form>
            <br>
        <form>
            <input type="hidden" name="delcmd" value="deleteCategory">
            <input type="hidden" name="cmd" value ="delete_product">
            <input type="hidden" name="categoryID" value="${category1.getId()}">
            <input type="submit" value="Delete Category">
        </form>
        <script>
            $(document).ready(function () {
            var wrapper = $(".new_attributes");
            var add_button = $("#add_field_button");

            $(add_button).click(function (e) {
            e.preventDefault();
            $(wrapper).append('<div><input type="text" name="attribute"/><input type="radio" name"required"/><a href="#" class="remove_field"> Remove</a></div>');
            });

            $(wrapper).on("click", ".remove_field", function (e) {
            e.preventDefault();
            $(this).parent('div').remove();
            });
            });
        </script>
    </body>
</html>
