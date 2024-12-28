package vn.edu.hust.soict.japango.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Autowired
    private CloudinaryProperties cloudinaryProperties;

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = null;
        Map<String, String> config = new HashMap();
        config.put("cloud_name", cloudinaryProperties.getCloudName());
        config.put("api_key", cloudinaryProperties.getApiKey());
        config.put("api_secret", cloudinaryProperties.getApiSecret());
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
