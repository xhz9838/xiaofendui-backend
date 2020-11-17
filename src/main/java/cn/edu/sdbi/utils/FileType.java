package cn.edu.sdbi.utils;

/**
 * @author xuhongzu
 * @date 2020/10/28
 */
public enum FileType {

    /**
     *
     */
    DIRECTORY,
    FILE;


    public static FileType isDir(Boolean isDir){
        if(isDir){
            return FileType.DIRECTORY;
        }else {
            return FileType.FILE;
        }
    }
}
