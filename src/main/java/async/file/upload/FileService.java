package async.file.upload;

import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

public interface FileService {

    void init();

    void deleteAll();

    void store(MultipartFile file);

    Stream<String> listAll();

}