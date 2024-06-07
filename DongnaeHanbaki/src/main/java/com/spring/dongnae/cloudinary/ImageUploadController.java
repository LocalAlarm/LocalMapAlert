package com.spring.dongnae.cloudinary;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.spring.dongnae.utils.properties.PropertiesConfig;

@Component
public class ImageUploadController {

	private static PropertiesConfig propertiesConfig;
	
    public String uploadImage(MultipartFile file) {
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
            try {
                Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", propertiesConfig.getName(),
                        "api_key", propertiesConfig.getKey(),
                        "api_secret", propertiesConfig.getSecret()));

                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                System.out.println(uploadResult.toString());
                String imageUrl = (String) uploadResult.get("url");
                // 이미지 URL을 저장하거나 사용할 수 있습니다.
                System.out.println("Uploaded image URL: " + imageUrl);
                return imageUrl;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
