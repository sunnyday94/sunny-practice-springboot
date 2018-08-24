/**
 * FileName: ReqPage
 * Author:   sunny
 * Date:     2018/8/24 15:58
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.vphoto.practice.utils.page;

import lombok.Data;

import java.io.Serializable;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@Data
public class ReqPage<T extends Serializable> implements  Serializable {


    private static final long serialVersionUID = 4958562099571256217L;

    private Integer pageIndex;

    private Integer pageSize;

    private T ojb;


    public ReqPage() {
    }

    public ReqPage(Integer pageIndex, Integer pageSize, T ojb) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.ojb = ojb;
    }
}