/**
 * FileName: BaseBean
 * Author:   sunny
 * Date:     2019/2/18 16:19
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @author sunny
 * @create 2019/2/18
 * @since 1.0.0
 */
@Getter
@Setter
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL) //json序列化时null属性不显示
public class BaseBean implements Serializable {

    private static final long serialVersionUID = -232942928335938669L;

    @Column(name = "del_flag")
    protected String delFlag;  //删除标志

    @Column(name = "create_time")
    protected Date createTime;  //创建时间

    @Column(name = "update_time")
    protected Date updateTime; //更新时间

    @Column(name = "del_time")
    protected Date delTime; //删除时间

    @Column(name = "remark")
    protected String remark;  //备注信息

}