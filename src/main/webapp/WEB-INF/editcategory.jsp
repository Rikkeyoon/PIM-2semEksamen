<%-- 
    Document   : editcategory
    Created on : 19. nov. 2019, 17.31.45
    Author     : carol
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

        <form name="edit_category" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="edit_category">
            <h1>Edit Category</h1>
            <select name="category">
                <c:forEach items="${categories}" var="cat">
                    <option value="${cat.getCategoryname()}">
                        ${cat.getCategoryname()}</option>
                    </c:forEach>
            </select>
            <br><br>

            <!--
            <label for="current_attributes">Current Attributes</label>
            <c:forEach items="${category.getAttributes()}" var="attr">
                <p>${attr}</p>
            </c:forEach>
            -->

            <div class="new_attributes">
                <label for="attribute_name">Attribute Name (Mark if the field is required)</label>
                <br>
                <div><input type="text" name="attribute">
                    <input type="radio" name="required">
                </div>
            </div>

            <button class="add_field_button">Add</button>
            <br><br>
            <input type="submit" value="Update Category"/>
        </form>
        <script>
            $(document).ready(function () {
                var wrapper = $(".new_attributes");
                var add_button = $(".add_field_button");

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
