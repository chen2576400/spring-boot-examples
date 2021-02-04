package com.chenning.springbootlearn.demonBuild.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;


/**
 * <p>
 *
 * </p>
 *
 * @author chen
 * @since 2021-02-02
 */
@TableName("user")// 数据库表名
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)// 数据库主键字段
    private Integer userId;

    private String password;

    private String userName;

    private String address;


/*    @TableField(exist = false) // 非数据库字段
    private String OtherMation;*/


}
