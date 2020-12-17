/**
 * FileName: BaseVO
 * Author:   sunny
 * Date:     2019/2/18 16:22
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @author sunny
 * @create 2019/2/18
 * @since 1.0.0
 */
@ApiModel(description = "公共字段")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 2979937197175167483L;

    @ApiModelProperty(value="删除标志")
    protected String delFlag;

    @ApiModelProperty(value="创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date createTime;

    @ApiModelProperty(value="更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;

    @ApiModelProperty(value="删除时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date delTime;

    @ApiModelProperty(value="备注信息")
    protected String remark;
}