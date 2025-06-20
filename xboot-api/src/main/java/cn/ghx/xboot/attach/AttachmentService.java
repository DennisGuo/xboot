package cn.ghx.xboot.attach;

import cn.ghx.xboot.attach.vo.Base64Image;
import cn.ghx.xboot.mapper.AttachmentMapper;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author ghx
 * @createDate 2024-02-23 15:19:34
 */
@Slf4j
@Service
public class AttachmentService extends ServiceImpl<AttachmentMapper, Attachment> {

    @Value("${app.storage.path}")
    private String storagePath;

    public Attachment upload(String path, MultipartFile mfile) throws IOException {
        String filename = mfile.getOriginalFilename();
        if (!StringUtils.hasText(path)) {
            path = DateUtil.format(new Date(), "yyyy/MM/dd");
        }
        path += "/" + filename;
        File file = new File(storagePath, path);
        File folder = file.getParentFile();
        if (!folder.exists()) {
            boolean rs = folder.mkdirs();
            log.info("create folder rs ={}, folder={}", rs, folder);
            Assert.isTrue(rs, "创建保存文件目录失败！");
        }
        String mime = FileUtil.getMimeType(filename);
        log.debug("upload file ,original file = {},mime={}, path={}", filename, mime, path);

        mfile.transferTo(file);

        Attachment rs = new Attachment();
        rs.setPath(path);
        rs.setName(filename);
        rs.setMime(mime);
        rs.setFileSize(mfile.getSize());

        save(rs);
        //new FileInputStream(file).close(); // 这一步为了释放 file 文件

        return rs;
    }

    public Boolean remove(String id) {
        Attachment item = getById(id);
        if (item != null) {
            File file = new File(storagePath, item.getPath());
            if (FileUtil.exist(file)) {
                FileUtil.del(file);
            }
            return removeById(id);
        }
        return true;
    }

    public StreamingResponseBody serveFile(String id, HttpServletResponse response) throws IOException {
        Attachment item = getById(id);
        if (item != null) {
            File file = new File(storagePath, item.getPath());
            if (file.exists()) {
                response.setContentType(item.getMime());
                response.setContentLength((int) file.length());
                return (os) -> {
                    try (FileInputStream ins = new FileInputStream(file)) {
                        ins.transferTo(os);
                        os.flush();
                    }
                };
            } else {
                log.warn("文件{}，不存在", file.getAbsolutePath());
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            log.warn("附件{}，不存在", id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        //throw new FileNotFoundException(id);
        return null;
    }

    public Attachment uploadBase64(String path, Base64Image img) throws Exception {
        String filename = img.getName();
        if (!StringUtils.hasText(path)) {
            path = DateUtil.format(new Date(), "yyyy/MM/dd");
        }
        path += "/" + filename;
        File file = new File(storagePath, path);
        File folder = file.getParentFile();
        if (!folder.exists()) {
            boolean rs = folder.mkdirs();
            log.info("create folder rs ={}, folder={}", rs, folder);
            Assert.isTrue(rs, "创建保存文件目录失败！");
        }
        String mime = FileUtil.getMimeType(filename);
        log.debug("upload file ,original file = {}, mime={},path={}", filename, mime, path);

        String dataUrl = img.getDataUrl();
        // base64 url to bytes
        String base64 = dataUrl.contains(",") ? dataUrl.substring(dataUrl.indexOf(",") + 1) : dataUrl;
        byte[] bytes = DatatypeConverter.parseBase64Binary(base64);
        FileUtil.writeBytes(bytes, file);

        Attachment rs = new Attachment();
        rs.setPath(path);
        rs.setName(filename);
        rs.setMime(mime);
        rs.setFileSize(file.length());

        save(rs);
        //new FileInputStream(file).close(); // 这一步为了释放 file 文件

        return rs;
    }
}




