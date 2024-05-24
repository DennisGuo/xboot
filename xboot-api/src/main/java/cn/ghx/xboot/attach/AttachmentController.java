package cn.ghx.xboot.attach;

import cn.ghx.xboot.common.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

@RestController
@RequestMapping("/attach")
public class AttachmentController {
    public static final String POST_UPLOAD = "/upload";
    public static final String GET_FILE = "/file/{id}";
    public static final String DELETE = "/{id}";

    private final AttachmentService service;

    public AttachmentController(AttachmentService service) {
        this.service = service;
    }

    @GetMapping(GET_FILE)
    public StreamingResponseBody serveFile(@PathVariable String id,HttpServletResponse response) throws IOException {
        return service.serveFile(id,response);
    }

    @DeleteMapping(DELETE)
    public Result<Boolean> remove(@PathVariable String id){
        return Result.success(service.remove(id));
    }

    @PostMapping(POST_UPLOAD)
    public Result<Attachment> upload(@RequestParam(required = false) String path , MultipartFile file) throws IOException {
        return Result.success(service.upload(path,file));
    }


}
