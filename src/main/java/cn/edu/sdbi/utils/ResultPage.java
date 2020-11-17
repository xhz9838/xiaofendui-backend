package cn.edu.sdbi.utils;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 分页Result对象
 * @author suiyue
 * @date 2020/9/28
 */
@Data
@Accessors(chain = true)
@Builder
public class ResultPage<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int status;

    private String msg;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 每页的条数
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 当前页码数
     */
    private Integer pageNum;

    /**
     * 结果集
     */
    private List<T> items;


    public static ResultPage ok(Long total, Integer pageSize, Long pages, Integer pageNum, List items) {
        return ResultPage.builder()
                .status(ResultCode.OK)
                .msg("操作成功")
                .total(total)
                .pageSize(pageSize)
                .pageNum(pageNum)
                .pages(pages)
                .items(items)
                .build();
    }

    public static ResultPage ok(int code, String msg, Long total, Integer pageSize, Long pages, Integer pageNum, List items) {
        return ResultPage.builder()
                .status(code)
                .msg(msg)
                .total(total)
                .pageSize(pageSize)
                .pageNum(pageNum)
                .pages(pages)
                .items(items)
                .build();
    }

}
