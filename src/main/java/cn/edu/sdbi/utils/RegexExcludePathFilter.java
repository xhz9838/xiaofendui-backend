package cn.edu.sdbi.utils;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/**
 * @author suiyue
 * @date 2020/10/27
 */
public class RegexExcludePathFilter implements PathFilter {


    private String regex;

    public RegexExcludePathFilter(String regex){
        this.regex = regex;
    }

    @Override
    public boolean accept(Path path) {
        String name = path.getName();
        return name.contains(regex);
    }
}
