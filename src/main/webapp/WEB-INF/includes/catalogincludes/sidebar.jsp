<%-- 
    Document   : sidebar
    Created on : 5. dec. 2019, 13.11.52
    Author     : carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="sidebar" class="sidebar">
    <a href="javascript:void(0)" class="closebtn" onclick="closeSidebar()">&times;</a>
    <div class="padded">
        <form name="get_product_catalog" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="productcatalog">
            <button type="submit" class="viewcatalogbtn"><span>Products</span></button>
        </form>
        <form name="view_categories" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="viewcategories">
            <button type="submit" class="viewcatalogbtn">Categories</button>
        </form>
        <br><br>
        <h4>Search for: </h4>
        <form name="search" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="search_product">
            <div class="radiobtns" style="text-align: center;">
                <input type="radio" name="searchType" value="product_id" checked="checked"/> Id
                <input type="radio" name="searchType" value="product_itemnumber"/> Item number
                <input type="radio" name="searchType" value="product_name"/> Name <br>
                <input type="radio" name="searchType" value="product_brand"/> Brand
                <input type="radio" name="searchType" value="product_category"/> Category
                <input type="radio" name="searchType" value="product_tag"/> Tag <br>
                <input type="radio" name="searchType" value="product_supplier"/> Supplier
            </div>
            <br>
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Search" name="search">
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit">
                        <i class="glyphicon glyphicon-search"></i>
                    </button>
                </div>
            </div>
        </form>
        <br>
        <form name="get_product_catalog" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="productcatalog">
            <button class="btn btn-default" type="submit" style="float:right;">
                Reset</button>
        </form>

        <br><br>

        <form name="get_upload_page" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="uploadpage">
            <input type="submit" value="Go to upload page" />
        </form>
        <br><br>
        <!--
        <form name="upload" action="FrontController" method = "POST" enctype = "multipart/form-data">
            <input type="hidden" name="cmd" value="upload_json">
            <div class="input-group" style="position: absolute;
                 bottom: 100px!important;margin-right: 10px;">
                <div class="form-control">Upload JSON</div>
                <input class="btn btn-default" type="file" 
                       id="files" name = "file" accept=".json" multiple />
                <br><br>
                <div class="input-group-btn" style="position: absolute;
                 bottom: 100px!important;margin-right: 10px;">
                    <button class="btn btn-default" type="submit">
                        <i class="glyphicon glyphicon-upload"></i>
                    </button>
                </div>
                <br>
            </div>
        </form>
        -->

        <form name="download" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="download_catalog">
            <div class="input-group" style="position: absolute;
                 bottom: 20px!important;margin-right: 10px;">
                <div class="form-control">Download catalog</div>
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit">
                        <i class="glyphicon glyphicon-download"></i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>