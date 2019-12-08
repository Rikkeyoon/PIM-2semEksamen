<%-- 
    Document   : navigationbar
    Created on : 5. dec. 2019, 14.27.55
    Author     : carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="nav">
    <div class="navbar-content">
        <button class="openbtn" onclick="openSidebar()">&#9776;</button>
        <img src="https://res.cloudinary.com/dmk5yii3m/image/upload/v1575766331/LogoMakr_19Hf5O_exndwi.png">
    </div>
</nav>
<nav class="view-nav" id="view-nav">
    <div class="view-nav-content">
        <button class="btn" onclick="window.history.back();">
            <i class="fas fa-long-arrow-alt-left" style="font-size: 20px;"></i><b>   Go back</b>
        </button>
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