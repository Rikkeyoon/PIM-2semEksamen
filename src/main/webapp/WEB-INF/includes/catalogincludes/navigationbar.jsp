<%-- 
    Document   : navigationbar
    Created on : 8. dec. 2019, 14.44.39
    Author     : carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Main navigation bar -->
<nav class="nav">
    <div class="navbar-content">
        <button class="openbtn" onclick="openSidebar()">&#9776;</button>
        <img src="https://res.cloudinary.com/dmk5yii3m/image/upload/v1575766331/LogoMakr_19Hf5O_exndwi.png">
    </div>
</nav>
<!-- View navigation bar -->
<nav class="view-nav" id="view-nav" >
    <div id="btnContainer" style="float: right;">
        <button class="btn" onclick="listView()"><i class="glyphicon glyphicon-th-list"></i> List</button> 
        <button class="btn active" onclick="gridView()"><i class="glyphicon glyphicon-th-large"></i> Grid</button>
        <button class="btn" onclick="tableView()"><i class="fas fa-table"></i> Table</button>
    </div>
</nav>
<script>
    window.onscroll = function () {
        myFunction();
    };

    var viewnav = document.getElementById("view-nav");
    var sticky = viewnav.offsetTop;

    function myFunction() {
        if (window.pageYOffset >= sticky) {
            viewnav.classList.add("sticky");
        } else {
            viewnav.classList.remove("sticky");
        }
    }
</script>
