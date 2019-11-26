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
        <h1>Edit ${category1}</h1>
        
        <form name="update_attributes" action="FrontController" method="POST">
            <input type="hidden" name="category" value="${category1}">
            <input type="hidden" name="edit" value="editOld">
            <input type="hidden" name="cmd" value="edit_category">
            <div class="old_attributes">
                <label for="attributes"><b>Attributes</b></label>
                <br>
                <c:forEach items="${categories}" var="cat">
                    <c:if test="${cat.getCategoryname().equals(category1)}">
                        <c:forEach items="${cat.getAttributes()}" var="attr">
                            <input type="hidden" name="attr_old" value="${attr}"/>
                            <input type="text" name="attr_new" value="${attr}"/>
                            <label>Remove:</label>
                            <input type="checkbox" name="attr_remove" value="${attr}"/>
                            <div id="divValidateAttribute"></div>
                            <br>
                        </c:forEach>
                    </c:if>
                </c:forEach>
            </div>
            <input type="submit" value="Update attributenames"
                   <br><br><br>
        </form>

        <form name="edit_category" action="FrontController" method="POST">
            <input type="hidden" name="category" value="${category1}">
            <input type="hidden" name="edit" value="editNew">
            <input type="hidden" name="cmd" value="edit_category">
            <div class="new_attributes">
                <p>New Attribute Name (Mark if the field is required)</p>
                <div><input type="text" name="attribute">
                    <input type="radio" name="required">
                </div>
            </div>

            <button id="add_field_button">Add</button>
            <br><br>
            <input class="updatebtn" type="submit" value="Save Changes"/>
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
