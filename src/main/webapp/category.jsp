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
        <title>Category Page</title>
          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" charset="utf-8"></script>
    </head>
    <body>
        <h1>Create Category</h1>
        
            <%-- select category from list --%>
            <form name="f1" method="get" action="#">
                <select name="category">
                    <option>Cykler</option>
                    <option>Mobiler</option>   
                    <option>Alkohol</option>
                    <option>Seng</option>
                </select>
            <input type="submit" name="submit" value="Select Category"/>
            </form>
            <br>
            
              <%-- display selected category from dropdown list. --%>
     <% 
                String selected = request.getParameter("category");
                if (selected !=null)
                {
                    out.println("Selected Category is : "+ selected + "\n");
                }
                
      %>
      
      <h2>Standard template</h2>
            <form>
                <label for="product_id"><b>ID</b></label>
                <br>
                <input type="text" name="product_id" id="id" onkeyup="validateId();" required>
                <div id="divValidateId"></div>
                <br>
                <label for="product_name"><b>Product Name</b></label>
                <br>
                <input type="text" name="product_name" required>
                <br><br>
                <label for="product_desc"><b>Description</b></label>
                <br>
                <textarea name="product_desc" rows="4" cols="20" required="required">
                </textarea>
                <br><br>
                <label for="product_category"><b>Category</b></label>
                <br>
                <input type="text" name="product_category" id="category" 
                       onkeyup="validateCategory();" required>
                <div id="divValidateCategory"></div>
                <br>

                <!-- Exception handling -->
                <c:if test="${error != null}">
                    <div class="form-alert">${error}</div>
                </c:if>

                <input class="createbtn" id="createbtn" type="submit" value="Create" 
                       onclick="confirmation()"/>
            </form>
                
        <h3>Choose attributes for template</h3>
        <form>
            Text Field<input type="radio" name="textfield" value="textfield" />
        </form>
        
        <br>
        <form name="f1" method="get" action="#">
                <select name="attributes">
                    <option>Text Field</option>
                    <option>Button</option>   
                    <option>Checkbox</option>
                    <option>Radio Button</option>
                </select>
            <input type="submit" name="submit" value="Select Attributes"/>
            </form>
            <br>
            
      <% 
                String chosen = request.getParameter("attributes");
                if (chosen !=null)
                {
                    out.println("Selected attribute is : "+ chosen + "\n");
                }
      %>
      <br>     

        <form
        <label for="headline"><b>Headline</b></label>
            <input type="text" placeholder="Enter headline" name="headline" required>

            <button type="submit" class="btn">Add</button>
        </form>
      
      
    
      

      
      
      
      
      
      

        <!-- JavaScript functions -->
        <script>
            function validateID() {
                var id = $("#id").val();
                var idformat = /[0-9]/;

                if (!id.match(idformat)) {
                    $("#createbtn").attr('disabled', 'disabled');
                    $("#divValidateId").html("Invalid Id").addClass('form-alert');
                } else {
                    $("#createbtn").removeAttr('disabled');
                    $("#divValidateId").html("").removeClass('form-alert');
                }
            }

            function validateCategory() {
                var category = $("#category").val();
                var categoryformat = /[a-z]/;

                if (!category.match(categoryformat)) {
                    $("#createbtn").attr('disabled', 'disabled');
                    $("#divValidateCategory").html("Invalid Category").addClass('form-alert');
                } else {
                    $("#createbtn").removeAttr('disabled');
                    $("#divValidateCategory").html("").removeClass('form-alert');
                }
            }

            function confirmation() {
                alert("Product created succesfully!");
            }

        </script>
      
      
            


        
        
    </body>
</html>
