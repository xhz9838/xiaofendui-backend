package cn.edu.sdbi.entity;

import cn.edu.sdbi.utils.FileType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author suiyue
 * @date 2020/10/21
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Attachment implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date accessTime;
    private Long blockSize;
    private Long childrenNum;
    private String fileId;
    private String group;
    private Long length;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modificationTime;
    private String owner;
    private String pathSuffix;
    private String permission;
    private String replication;
    private String parentPath;
    private FileType type;
}
