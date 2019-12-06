<%-- 
    Document   : navigationbar
    Created on : 5. dec. 2019, 14.27.55
    Author     : carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="nav">
    <div class="navbar-content">
        <button class="openbtn" onclick="openNav()">&#9776;</button>
    </div>
    <h1>PIM System</h1>

    <form name="get_upload_page" action="FrontController" method = "POST">
        <input type="hidden" name="cmd" value="get_view">
        <input type="hidden" name="view" value="uploadpage">
        <input type="submit" value="Go to upload page" />
    </form>
    <br><br>
</nav>