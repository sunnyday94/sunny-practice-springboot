/**
 * FileName: STBUserVo
 * Author:   sunny
 * Date:     2018/8/24 14:32
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.vphoto.practice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@Data
public class STBUserVo implements Serializable {

    private static final long serialVersionUID = 7478963597281334819L;
    private Long id ;  //主键id

    private String userName ; //用户名称

    private String userPassword ; //用户密码

    private String gender ; //性别

    private String telPhone; //电话号码

    private String delFlag;  //删除标志

    private Date createTime;  //创建时间

    private Date updateTime; //更新时间

    private Date delTime; //删除时间

    private String remark;  //备注信息
}