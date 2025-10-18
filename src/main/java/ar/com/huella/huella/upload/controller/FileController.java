package ar.com.huella.huella.upload.controller;

import ar.com.huella.huella.upload.service.implementation.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@Tag(name = "Update", description = "Operaciones para subir, ver y descargar.")
public class FileController {
 @Autowired
    private FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    @PostMapping("/upload")
    @Operation(summary = "Metodo que sube un archivo  ", description = "Metodo que sube un archivo crea un id.")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileStorageService.storeFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Could not upload the file: " + e.getMessage());
        }
    }

    @GetMapping("/view/{filename}")
    @Operation(summary = "Metodo de vista ", description = "Metodo que da la vista  en base al id.")
    public ResponseEntity<Resource> viewFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(fileUploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = "application/octet-stream";
                try {
                    contentType = Files.probeContentType(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/files/{filename}")
    @Operation(summary = "Metodo que descarga", description = "Metodo que descarga en base al id.")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Path filePath = Paths.get(fileUploadDir).resolve(filename).normalize();
        System.out.println("Requested file path: " + filePath.toAbsolutePath());

        try {
            if (Files.exists(filePath) && Files.isReadable(filePath)) {
                Resource resource = new UrlResource(filePath.toUri());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                System.out.println("File not found or not readable: " + filePath.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    
     @PostMapping("/uploadUrl")
     @Operation(summary = "Metodo que uploadUrl ", description = "Metodo que sube y crea una  url para la vista")
    public ResponseEntity<String> uploadFileUrl(@RequestParam("file") MultipartFile file) {
        try {
            String fileId = fileStorageService.storeFile(file);
            String fileUrl =  "http://localhost:8080/view/" +fileId;
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Could not upload the file: " + e.getMessage());
        }
    }
    
}
