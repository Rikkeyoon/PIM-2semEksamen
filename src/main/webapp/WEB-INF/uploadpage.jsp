<%-- 
    Document   : uploadpage
    Created on : 1. dec. 2019, 19.14.12
    Author     : carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form name="upload" action="FrontController" method = "POST" enctype = "multipart/form-data">
            <input type="hidden" name="cmd" value="upload_json">
            <input type="file" id="files" name = "file" accept=".json" multiple /><br>
            <br>
            <output id="list"></output>
            <input type="submit" value="Submit">
        </form>
        
        <script type="text/javascript">
            function handleFileSelect(evt) {
                document.getElementById('list').innerHTML = "";
                var files = evt.target.files;

                // Loop through the FileList and render image files as thumbnails.
                for (var i = 0, f; f = files[i]; i++) {

                    // Only process image files.
                    if (!f.type.match('image.*')) {
                        continue;
                    }

                    var reader = new FileReader();

                    // Closure to capture the file information.
                    reader.onload = (function (theFile) {
                        return function (e) {
                            // Render thumbnail.
                            var span = document.createElement('span');
                            span.innerHTML =
                                    [
                                        '<span style="height: 75px; border: 1px solid #000; margin: 5px"><img style="height: 75px; border: 1px solid #000; margin: 5px" src="', e.target.result, '" title="', escape(theFile.name), '"/><input type="radio" name="fileSelected" value="', escape(theFile.name), '" required></span>'
                                    ].join('');

                            document.getElementById('list').insertBefore(span, null);
                        };
                    })(f);

                    // Read in the image file as a data URL.
                    reader.readAsDataURL(f);
                }
            }

            document.getElementById('files').addEventListener('change', handleFileSelect, false);
        </script>
    </body>
</html>
