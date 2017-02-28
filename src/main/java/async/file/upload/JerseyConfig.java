package async.file.upload;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
         register(FileUploadEndpoint.class);
         register(MultiPartFeature.class);
         register(LoggingFeature.class);
    }
}