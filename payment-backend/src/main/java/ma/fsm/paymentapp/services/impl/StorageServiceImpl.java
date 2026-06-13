package ma.fsm.paymentapp.services.impl;

import ma.fsm.paymentapp.exceptions.ResourceNotFoundException;
import ma.fsm.paymentapp.services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {
    private final Path rootLocation;

    public StorageServiceImpl() throws IOException {
        String storageDirectory = System.getProperty("user.home") + "/inset_data/payments/";
        this.rootLocation = Paths.get(storageDirectory);
        init();
    }

    @Override
    public void init() throws IOException {
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
    }

    @Override
    public String store(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("PDF file is required");
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename() == null ? "receipt.pdf" : file.getOriginalFilename());
        if (!originalFilename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Only PDF files are accepted");
        }

        String storedFilename = UUID.randomUUID() + "_" + originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path destination = rootLocation.resolve(storedFilename).normalize();
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return destination.toString();
    }

    @Override
    public Resource loadAsResource(String filePath) throws MalformedURLException {
        if (filePath == null || filePath.isBlank()) {
            throw new ResourceNotFoundException("Payment receipt file not found");
        }
        Path path = Paths.get(filePath).normalize();
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new ResourceNotFoundException("Payment receipt file not readable");
        }
        return resource;
    }

    @Override
    public Path getRootLocation() {
        return rootLocation;
    }
}
