package cn.edu.sdbi;

import cn.edu.sdbi.utils.HdfsUtils;
import cn.edu.sdbi.utils.RegexExcludePathFilter;
import org.apache.hadoop.fs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author xuhongzu
 * @date 2020/10/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoTest {

    @Test
    public void test01() throws IOException {
        FileSystem fs = HdfsUtils.getFileSystem();
        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fs.listFiles(new Path("/"), true);

        while (locatedFileStatusRemoteIterator.hasNext()) {
            LocatedFileStatus next = locatedFileStatusRemoteIterator.next();
            System.out.println(next.getPath().toString());
        }
        fs.close();
    }

    @Test
    public void test02() throws IOException {
        FileSystem fs = HdfsUtils.getFileSystem();
        FileStatus[] fileStatuses = fs.listStatus(new Path("/xiaofendui/upload"), new RegexExcludePathFilter("/*1*/"));
        System.out.println(Arrays.asList(fileStatuses));
        fs.close();
    }
}
