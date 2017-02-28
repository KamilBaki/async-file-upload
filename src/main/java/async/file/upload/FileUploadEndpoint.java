package async.file.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;  
import javax.ws.rs.core.Response;  
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.ConnectionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;  
import org.glassfish.jersey.media.multipart.FormDataParam;

@Component
@Path("/file")
public class FileUploadEndpoint {

    // should use ManagedExecutorService or ManagedThreadFactory in JEE environment
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    private final FileService fileService;

    @Autowired
    public FileUploadEndpoint(FileService fileService) {
        this.fileService = fileService;
    }

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadFile(
		final @FormDataParam("asyncfile") InputStream uploadedInputStream,
		final @FormDataParam("asyncfile") FormDataContentDisposition fileDetail,
		final @Suspended AsyncResponse response) {

        TimeoutHandler timeout = (final AsyncResponse resp) -> {
            resp.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                            .entity("TIME OUT !").build());
        };
        response.setTimeoutHandler(timeout);
        response.setTimeout(180, TimeUnit.SECONDS);

        CompletionCallback completion = (final Throwable t) -> {
            if(t != null){
                response.resume(t);
            }
        };

        ConnectionCallback connection = (final AsyncResponse disconnected) -> {
            disconnected.cancel();
        };
        response.register(completion, connection);

        executor.submit(() -> {
            String filename = fileDetail.getFileName();
            String output = "File [ " + filename + " ] successfully uploaded! <a href=\"/\">Return</a>";
            fileService.store(uploadedInputStream,filename);
            response.resume(Response.ok(output).build());
        });
	}

}