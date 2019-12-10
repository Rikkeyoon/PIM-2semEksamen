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
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>
    <body>
        <!-- Navigation bar -->
        <%@include file="includes/navigationbar.jsp" %>

        <!-- Sidebar -->
        <%@include file="includes/sidebar.jsp" %>

        <!-- Main content of page -->
        <div id="main">
            <form name="create_category" action="FrontController" method="POST">
                <input type="hidden" name="cmd" value="create_category">
                <div class="container text-center" >
                    <h1 style="text-align: center">Create Category</h1>
                    <br>
                    <div class="row">
                        <div class="col col-lg-12">
                            <label for="category_name">Category Name</label>
                            <br>
                            <input type="text" name="category_name" id="category_name" required>
                            <br><br>
                        </div>
                    </div>

                    <div class="new_attributes">
                        <label for="attribute_name">Attribute Name (Mark if the field is required)</label>
                        <br>
                        <div class="row">
                            <div class="col-lg-12">
                            </div>
                        </div>
                    </div>
                    <br>
                    <button class="add_field_button"><i class="glyphicon glyphicon-plus-sign" style="margin-right: 10px;"></i>Add</button>
                    <br><br>
                    <button class="crtbtn" type="submit"><i class="glyphicon glyphicon-floppy-disk" style="margin-right: 10px;"></i>Create category</button>
                    </div>
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

            var sidebar = document.getElementById("sidebar");
            var main = document.getElementById("main");
            var navbar = document.getElementsByClassName("navbar-content")[0];
            var viewbar = document.getElementsByClassName("view-nav-content")[0];
            function openSidebar() {
                sidebar.style.width = "250px";
                main.style.marginLeft = "250px";
                navbar.style.marginLeft = "250px";
                viewbar.style.marginLeft = "250px";
            }

            function closeSidebar() {
                sidebar.style.width = "0";
                main.style.marginLeft = "0";
                navbar.style.marginLeft = "0";
                viewbar.style.marginLeft = "0";
            }
        </script>
    </body>
</html>
