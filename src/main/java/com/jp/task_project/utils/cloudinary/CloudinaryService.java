package com.jp.task_project.utils.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CloudinaryService {
   //para dps alte
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, String> generateUploadSignature() {

        long timestamp = System.currentTimeMillis() / 1000L;

        Map<String, Object> paramsToSign = new HashMap<>();
        paramsToSign.put("timestamp", timestamp);

        String signature = cloudinary.apiSignRequest(paramsToSign, cloudinary.config.apiSecret);

        Map<String, String> response = new HashMap<>();
        response.put("timestamp", String.valueOf(timestamp));
        response.put("signature", signature);
        response.put("api_key", cloudinary.config.apiKey);

        return response;
    }


    public  String uploadImage (MultipartFile image){

        File convFile =null;
        try {
        String fileName = Paths.get(image.getOriginalFilename()).getFileName().toString();
        fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(image.getBytes());
        }

        // ☁️ Upload no Cloudinary
        Map<String, Object> result = cloudinary.uploader().upload(convFile, ObjectUtils.asMap(
                "folder", "bookCovers"
        ));
             return (String) result.get("secure_url");

        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Erro ao enviar arquivo para o Cloudinary.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado.");
        } finally {
            // 🧼 Remove o arquivo temporário
            if (convFile != null && convFile.exists()) {
                convFile.delete();
            }
        }
}


    public static String extractPublicIdFromUrl(String imageUrl) {
        // Expressão regular para extrair o public_id da URL
        String regex = "https://res\\.cloudinary\\.com/[^/]+/image/upload/[^/]+/([^/]+)\\.[a-z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(imageUrl);

        if (matcher.find()) {
            // O public_id será o primeiro grupo da regex
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("URL não é válida do Cloudinary");
        }
    }

    private void deleteImage(String publicId) {
        try {
            Map<String, String> response = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println("Delete response: " + response);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir imagem do Cloudinary", e);
        }
    }

    private void deleteImageByUrl(String imageUrl) {
        String publicId = extractPublicIdFromUrl(imageUrl);
        deleteImage(publicId);
    }




}
