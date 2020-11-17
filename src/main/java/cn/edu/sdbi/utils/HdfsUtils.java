package cn.edu.sdbi.utils;

import lombok.Data;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author suiyue
 * @date 2020/10/21
 */
@Component
@ConfigurationProperties(prefix = "hadoop.hdfs")
@Data
public class HdfsUtils {

    private String url;

    private static HdfsUtils hdfsUtils;

    @PostConstruct
    public void init() {
        hdfsUtils = this;
    }

    /**
     * 创建FileSystem
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public static FileSystem getFileSystem()  {
        Configuration conf = new Configuration();
//        conf.set("dfs.replication", "2");
        try {
            return FileSystem.get(new URI(hdfsUtils.url), conf, "root");
        } catch (IOException e) {
            throw new RuntimeException("获取文件系统发生IO异常", e);
        }  catch (URISyntaxException e) {
            throw new RuntimeException("获取文件系统发生URISyntaxException", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("获取文件系统发生InterruptedException", e);
        }
    }

    /**
     * copy流并关闭流
     * @param fs FileSystem
     * @param is InputStream
     * @param fos OutputStream
     * @throws IOException
     */
    public static void copyAndClose(FileSystem fs, InputStream is, OutputStream fos) {
        try {
            IOUtils.copyBytes(is, fos, fs.getConf());
        } catch (IOException e) {
            throw new RuntimeException("出现IO异常", e);
        }finally {
            IOUtils.closeStream(fos);
            IOUtils.closeStream(is);
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
