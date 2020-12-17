/**
 * FileName: STBUserVo
 * Author:   sunny
 * Date:     2018/8/24 14:32
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.entity.vo;

import com.sunny.practice.entity.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@ApiModel(description = "用户vo")
@Getter
@Setter
public class STBUserVo extends BaseVO {

    private static final long serialVersionUID = 7478963597281334819L;

    @ApiModelProperty(value = "主键id")
    private Long id ;

    @ApiModelProperty(value = "用户名称")
    private String userName ;

    @ApiModelProperty(value="用户密码")
    private String userPassword ;

    @ApiModelProperty(value="性别")
    private String gender ;

    @ApiModelProperty(value="电话号码")
    private String telPhone;

    @ApiModelProperty(value="删除标志")
    private String delFlag;
}