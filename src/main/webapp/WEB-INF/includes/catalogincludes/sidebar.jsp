<%-- 
    Document   : sidebar
    Created on : 5. dec. 2019, 13.11.52
    Author     : carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <br><br>
        <h4 align="center">Search for: </h4>
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
        <form name="get_product_catalog" action="FrontController" method = "POST">
            <input type="hidden" name="cmd" value="get_view">
            <input type="hidden" name="view" value="productcatalog">
            <button class="btn btn-default" type="submit" style="float:right;">
                <i class="glyphicon glyphicon-repeat" style="margin-right: 10px;"></i>Reset   
            </button>
        </form>

        <br><br><br>
        <form name="upload" action="FrontController" method = "POST" enctype = "multipart/form-data">
            <input type="hidden" name="cmd" value="upload_json">
            <div class="input-group" style="cursor: pointer;">
                <div class="form-control">
                    <div class="upload-btn-wrapper">
                        <input  type="file" id="files" name = "file" accept=".json" multiple />
                        Upload a JSON file
                    </div>
                </div>
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit">
                        <i class="glyphicon glyphicon-upload"></i>
                    </button>
                </div>
            </div>
        </form>

        <br>
        <form name="download" action="FrontController" method="POST">
            <input type="hidden" name="cmd" value="download_catalog">
            <div class="input-group">
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