package cn.ghx.xboot.attach;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ghx.xboot.mapper.AttachmentMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
        String filename= mfile.getOriginalFilename();
        if(!StringUtils.hasText(path)){
            path = DateUtil.format(new Date(),"yyyy/MM/dd");
        }
        path += "/" + filename;
        File file = new File(storagePath,path);
        File folder = file.getParentFile();
        if(!folder.exists()){
            boolean rs = folder.mkdirs();
            log.info("create folder rs ={}, folder={}" ,rs,folder);
            Assert.isTrue(rs,"创建保存文件目录失败！");
        }
        log.debug("upload file ,original file = {}, path={}" , filename, path);

        mfile.transferTo(file);

        Attachment rs = new Attachment();
        rs.setPath(path);
        rs.setName(filename);
        rs.setMime(FileUtil.getMimeType(path));
        rs.setFileSize(mfile.getSize());

        save(rs);

        return rs;
    }

    public Boolean remove(String id) {
        Attachment item = getById(id);
        if(item != null){
            File file = new File(storagePath,item.getPath());
            if(FileUtil.exist(file)){
                FileUtil.del(file);
            }
            return removeById(id);
        }
        return true;
    }

    public void serveFile(String id, HttpServletResponse response) throws IOException {
        Attachment item = getById(id);
        if(item != null){
            File file = new File(storagePath,item.getPath());
            if(file.exists()){
                response.setContentType(item.getMime());
                response.setContentLength(item.getFileSize().intValue());
                IoUtil.copy(new FileInputStream(file),response.getOutputStream());
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}




