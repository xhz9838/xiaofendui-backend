package cn.edu.sdbi.service.impl;

import cn.edu.sdbi.entity.Attachment;
import cn.edu.sdbi.service.AttachmentService;
import cn.edu.sdbi.utils.FileType;
import cn.edu.sdbi.utils.HdfsUtils;
import cn.edu.sdbi.utils.RegexExcludePathFilter;
import cn.edu.sdbi.utils.Result;
import org.apache.hadoop.fs.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author suiyue
 * @date 2020/10/21
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Override
    public void upload(MultipartFile multipartFile, String uploadPath) {
        uploadHdfs(multipartFile, uploadPath);
    }

    @Override
    public void setResponseOutputStream(HttpServletResponse response, String path, String fileName) {

       String pathFileName = appendPath(path, fileName);

        // 1 获取文件系统
        FileSystem fs = HdfsUtils.getFileSystem();

        // 2 获取输入流
        OutputStream os;
        FSDataInputStream fis;
        try {
            fis = fs.open(new Path(pathFileName));
            os = response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("IOException", e);
        }
        HdfsUtils.copyAndClose(fs, fis, os);
    }


    private static String appendPath(String path, String fileName) {
        int l = path.length();
        String s = l > 0 && path.charAt(l -1) == '/' ? path.substring(0, l - 1) : path;
        return s + "/" + fileName;
    }

    @Override
    public Result findByPath(String keyword, String path) throws IOException {
        FileSystem fs = HdfsUtils.getFileSystem();

        FileStatus[] fileStatuses = fs.listStatus(new Path(path),
                new RegexExcludePathFilter(keyword));

        fs.close();
        List<Attachment> attachmentList = Arrays.stream(fileStatuses)
                .map(this::convertTo)
                .collect(Collectors.toList());
        return Result.ok("ok", attachmentList);
    }

    private Attachment convertTo(FileStatus fileStatus) {
         return  Attachment.builder()
                .accessTime(new Date(fileStatus.getAccessTime()))
                .blockSize(fileStatus.getBlockSize())
                .childrenNum(0L)
                .fileId("0")
                .group(fileStatus.getGroup())
                .length(fileStatus.getLen())
                .modificationTime(new Date(fileStatus.getModificationTime()))
                .owner(fileStatus.getOwner())
                .pathSuffix(fileStatus.getPath().getName())
                .permission(fileStatus.getPermission().toString())
                .replication(String.valueOf(fileStatus.getReplication()))
                .parentPath(fileStatus.getPath().getParent().getName())
                .type(FileType.isDir(fileStatus.isDirectory()))
                .build();
    }

    @Override
    public Result deleteByPath(String path) {

        deleteFromHdfs(path);
        return Result.ok("ok", null);

    }

    private void deleteFromHdfs(String path) {

        FileSystem fs = HdfsUtils.getFileSystem();
        try {
            fs.delete(new Path(path), true);
        } catch (IOException e) {
            throw new RuntimeException("删除文件出现IO异常");
        } finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传文件到HDFS
     * @param multipartFile
     * @param uploadPath
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    private void uploadHdfs(MultipartFile multipartFile, String uploadPath) {
        FileSystem fs = HdfsUtils.getFileSystem();
        //获取输出流
        InputStream is;
        FSDataOutputStream fos;
        try {
            is = multipartFile.getInputStream();
            fos = fs.create(new Path(appendPath(uploadPath, multipartFile.getOriginalFilename())));
        } catch (IOException e) {
            throw new RuntimeException("上传出现IO异常", e);
        }
        HdfsUtils.copyAndClose(fs, is, fos);
    }
}
