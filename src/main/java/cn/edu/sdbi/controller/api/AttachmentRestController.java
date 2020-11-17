package cn.edu.sdbi.controller.api;

import cn.edu.sdbi.service.AttachmentService;
import cn.edu.sdbi.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author suiyue
 * @date 2020/10/21
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class AttachmentRestController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile multipartFile, @RequestParam String uploadPath) {
        log.debug("uploadPath={}", uploadPath);
        attachmentService.upload(multipartFile, uploadPath);
        return Result.ok("ok", null);
    }

    /**
     * 文件名模糊搜索
     * @param keyword 文件名关键字
     * @return
     */
    @GetMapping("/files")
    public Result filesForDir(String keyword, String path) throws IOException {
        return attachmentService.findByPath(keyword, path);
    }

    /**
     * 根据path删除文件
     *
     * @param path 文件id
     * @return
     */
    @DeleteMapping("/delete")
    public Result delete(String path) {
        return attachmentService.deleteByPath(path);
    }
}
