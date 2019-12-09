<%-- 
    Document   : sidebar
    Created on : 9. dec. 2019, 11.11.31
    Author     : carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Sidebar -->
<div id="sidebar" class="sidebar">
    <a href="javascript:void(0)" class="closebtn" onclick="closeSidebar()">&times;</a>
    <div class="padded">
        <form name="bulkEdit" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="choosecreate">
            <button type="submit" class="viewcatalogbtn">Create</button>
        </form>
        
        <form name="get_product_catalog" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="productcatalog">
            <button type="submit" class="viewcatalogbtn"><span>Catalog</span></button>
        </form>
        
        <form name="view_categories" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="viewcategories">
            <button type="submit" class="viewcatalogbtn">Categories</button>
        </form>
        
        <form name="bulkEdit" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="choosebulkedit">
            <button type="submit" class="viewcatalogbtn">Bulk edit</button>
        </form>
    </div>
</div>
