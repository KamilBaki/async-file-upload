package async.file.upload;

import java.io.InputStream;
import java.util.stream.Stream;

public interface FileService {

    void init();

    void deleteAll();

    void store(InputStream file, String fileName);

    Stream<String> listAll();

}