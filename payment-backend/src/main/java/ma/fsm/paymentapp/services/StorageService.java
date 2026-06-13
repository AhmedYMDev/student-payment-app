package ma.fsm.paymentapp.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    void init() throws IOException;
    String store(MultipartFile file) throws IOException;
    Resource loadAsResource(String filePath) throws IOException;
    Path getRootLocation();
}
