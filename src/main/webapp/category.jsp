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
      
      <
                
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
        
    </body>
</html>
