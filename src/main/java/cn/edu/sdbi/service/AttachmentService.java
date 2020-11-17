package cn.edu.sdbi.service;

import cn.edu.sdbi.utils.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author suiyue
 * @date 2020/10/21
 */
public interface AttachmentService {
    /**
     * 上传文件
     * @param multipartFile 文件对象
     * @param uploadPath 上传路径
     */
    void upload(MultipartFile multipartFile, String uploadPath);

    /**
     * 下载文件  将指定文件的流copy到response OutputStream
     * @param response HttpServletResponse
     * @param path
     * @param fileName 文件名
     */
    void setResponseOutputStream(HttpServletResponse response, String path, String fileName);

    /**
     * 条件搜索 文件
     * @param keyword 文件名关键字
     * @param path 路径
     * @return 分页Result
     */
    Result findByPath(String keyword, String path) throws IOException;

    /**
     * 根据path删除文件
     * @param path 文件path
     * @return Result
     */
    Result deleteByPath(String path);
}
