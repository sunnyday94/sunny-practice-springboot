/**
 * FileName: STBUser
 * Author:   sunny
 * Date:     2018/8/24 14:25
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.entity.po;

import com.sunny.practice.entity.BaseBean;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@Entity
@Table(name = "s_tb_user")
@Getter
@Setter
public class STBUser extends BaseBean {
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
}