package async.file.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {

    private final Path rootLocation;

    @Autowired
    public FileServiceImpl(ApplicationProperties properties) {
        rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new ApplicationException("Could not initialize file upload directory!", e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void store(InputStream file, String fileName) {

        try {
            Path targetFile = rootLocation.resolve(fileName);
            long bytesWritten = Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Wrote " +  targetFile + " [ " + bytesWritten + " bytes ] to the filesystem!");
        } catch (IOException e) {
            throw new ApplicationException("Failed to store file: " + fileName, e);
        }
        
    }

    @Override
    public Stream<String> listAll() {

        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path).getFileName().toString());
        } catch (IOException e) {
            throw new ApplicationException("Failed to read stored files!", e);
        }
        
    }
}