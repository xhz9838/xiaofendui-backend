package cn.edu.sdbi.controller;

import cn.edu.sdbi.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xuhongzu
 * @date 2020/10/22
 */
@Controller
@Slf4j
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 下载文件
     * @param response HttpServletResponse
     * @param fileName 文件名
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response, String fileName, String path){
        log.debug("fileName={},path={}", fileName, path);
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        attachmentService.setResponseOutputStream(response, path , fileName);
    }
}
