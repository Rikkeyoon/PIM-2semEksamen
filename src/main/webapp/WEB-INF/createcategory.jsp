<%-- 
    Document   : category
    Created on : 17. nov. 2019, 10.10.32
    Author     : Rikke
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>INFOMERGE | Create Category</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>
    <body>
        <!-- Sidebar -->
        <%@include file="includes/sidebar.jsp" %>
        
        <!-- Main content of page -->
        <div id="main">
            <form name="create_category" action="FrontController" method="POST">
                <input type="hidden" name="cmd" value="create_category">

                <h1>Create Category</h1>
                <br>

                <label for="category_name">Category Name</label>
                <br>
                <input type="text" name="category_name" id="category_name" required>
                <br><br>

                <div class="new_attributes">
                    <label for="attribute_name">Attribute Name (Mark if the field is required)</label>
                    <br>
                    <div><input type="text" name="attribute">
                        <input type="radio" name="required">
                    </div>
                </div>

                <button class="add_field_button">Add</button>
                <br><br>

                <input class="crtbtn" id="crtbtn" type="submit" value="Create Category"/>
            </form>
        </div>
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
