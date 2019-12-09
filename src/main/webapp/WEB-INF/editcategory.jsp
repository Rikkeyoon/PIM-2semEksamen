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
        <title>INFOMERGE | Edit Category</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
        <script src="https://kit.fontawesome.com/6341639fb9.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <!-- Navigation bar -->
        <%@include file="includes/navigationbar.jsp" %>

        <!-- Sidebar -->
        <%@include file="includes/sidebar.jsp" %>

        <!-- Main content of page -->
        <div id="main">
            <h1>Edit Category:</h1>
            <div class="container text-center" style="display: flex; justify-content: center;">   
                <div style="padding-left: 4%; padding-top: 5%;width: 500px;">
                    <h1>${category1.getCategoryname()}</h1>

                    <form name="update_attributes" action="FrontController" method="POST">
                        <input type="hidden" name="category" value="${category1.getCategoryname()}">
                        <input type="hidden" name="cmd" value="edit_category">
                        <h3><b>Attributes</b></h3>
                        <br>
                        <c:forEach items="${category1.getAttributes()}" var="attr"  varStatus="loop">
                            <input type="hidden" name="attr_old" value="${attr}"/>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon1">${loop.index + 1}</span>
                                </div>
                                <input type="text" class="form-control" name="attr_new" value="${attr}">
                            </div>
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" name="attr_remove" value="${attr}" class="custom-control-input" id="customCheck${loop.index + 1}">
                                <label class="custom-control-label" for="customCheck${loop.index + 1}"><b>Remove "${attr}" from this category</b></label>
                            </div>
                            <br><br>
                        </c:forEach>
                        <br>
                        <div class="new_attributes">
                            <button type="button" class="btn btn-default" id="add_field_button" style="border: 1px solid #818181;">
                                <i class="fas fa-plus-circle"></i>     Add Attribute</button>
                            <br><br>
                        </div>
                        <br>
                        <button class="btn btn-default" type="submit" style="border: 1px solid #818181;">
                            <i class="fas fa-save"></i>     Save Changes</button>
                    </form>
                    <br>
                    <form>
                        <input type="hidden" name="delcmd" value="deleteCategory">
                        <input type="hidden" name="cmd" value ="delete_product">
                        <input type="hidden" name="categoryID" value="${category1.getId()}">
                        <button class="btn btn-default" type="submit" style="border: 1px solid #818181;">
                            <i class="fas fa-trash-alt"></i>     Delete Category</button>
                    </form>
                </div>
            </div>
        </div>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript"  src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript"  src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

        <script>
            $(document).ready(function () {
                var wrapper = $(".new_attributes");
                var add_button = $("#add_field_button");

                $(add_button).click(function (e) {
                    e.preventDefault();
                    $(wrapper).append('<div class="input-group mb-3"><input type="text" name="attribute" class="form-control" placeholder="Attributename" /><div class="input-group-append"> <span class="input-group-text"><a style="color: inherit;" href="#" class="remove_field"> Remove Attribute</a></span></div></div>');
                });

                $(wrapper).on("click", ".remove_field", function (e) {
                    e.preventDefault();
                    $(this).parent('span').parent('div').parent('div').remove();
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
