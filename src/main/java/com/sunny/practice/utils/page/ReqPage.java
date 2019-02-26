/**
 * FileName: ReqPage
 * Author:   sunny
 * Date:     2018/8/24 15:58
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.utils.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqPage<T extends Serializable> implements  Serializable {


    private static final long serialVersionUID = 4958562099571256217L;

    /**
     * 当前页
     */
    private Integer pageIndex;

    /**
     * 每页条目数
     */
    private Integer pageSize;

    /**
     * 参数对象
     */
    private T obj;
}