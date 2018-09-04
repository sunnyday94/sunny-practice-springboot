/**
 * FileName: STBUser
 * Author:   sunny
 * Date:     2018/8/24 14:25
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.entity.po;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@Entity
@Table(name = "s_tb_user")
@Data
public class STBUser implements Serializable {
    private static final long serialVersionUID = -8929360818301496892L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;  //主键id

    @Column(name = "user_name")
    private String userName ; //用户名称

    @Column(name = "user_password")
    private String userPassword ; //用户密码

    @Column(name = "gender")
    private String gender ; //性别

    @Column(name = "tel_phone")
    private String telPhone; //电话号码

    @Column(name = "del_flag")
    private String delFlag;  //删除标志

    @Column(name = "create_time")
    private Date createTime;  //创建时间

    @Column(name = "update_time")
    private Date updateTime; //更新时间

    @Column(name = "del_time")
    private Date delTime; //删除时间

    @Column(name = "remark")
    private String remark;  //备注信息


}