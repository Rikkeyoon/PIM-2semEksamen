/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.servlet.http.Part;

/**
 *
 * @author allan
 */
public class ImageMapper implements IImageMapper {

    private static final String UPLOAD_DIR = "img";
    private static final String WORKING_DIR = System.getProperty("user.dir");
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dmk5yii3m",
            "api_key", "228872137167968",
            "api_secret", "1IRxrcNuw4zVdlwJBiqAgktyyeU"));

    @Override
    public List<Pair<String, Boolean>> uploadImages(List<Part> parts, String primaryImage) {
        List<Pair<String, Boolean>> images = new ArrayList<Pair<String, Boolean>>();
        try {
            //Creates img folder if none exist(temporary storage for image before uploaded to cloudinary)
            File uploadFolder = new File(WORKING_DIR + File.separator + UPLOAD_DIR);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            for (Part part : parts) {
                if (part.getContentType() != null && part.getSize() > 0) {
                    String fileName = part.getSubmittedFileName();
                    String contentType = part.getContentType();

                    // allows only JPEG files to be uploaded
                    if (contentType != null && !contentType.equalsIgnoreCase("image/jpeg")) {
                        continue;
                    }

                    part.write(WORKING_DIR + File.separator + UPLOAD_DIR + File.separator + fileName);
                    File file = new File(WORKING_DIR + File.separator + UPLOAD_DIR + File.separator + fileName);

                    Map uploadResult = null;
                    String URL = null;
                    Boolean bool = false;

                    uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

                    URL = (String) uploadResult.get(new String("url"));

                    if (part.getSubmittedFileName().equals(primaryImage.replaceAll("\\s+", ""))) {
                        bool = true;
                    }
                    images.add(new Pair(URL, bool));
                    
                    file.delete();

                }
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return images;
    }
}
