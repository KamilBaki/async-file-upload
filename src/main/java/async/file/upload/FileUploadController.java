package async.file.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.concurrent.Callable;
import java.time.LocalDateTime;

@Controller
public class FileUploadController {

    private final FileService fileService;

    @Autowired
    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", fileService
                .listAll()
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        final String filename = file.getOriginalFilename();

        try (final InputStream inputStream = file.getInputStream()) {
            fileService.store(inputStream, filename);
        } catch (IOException ex) {
            throw new ApplicationException("File handling error!", ex);
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

}