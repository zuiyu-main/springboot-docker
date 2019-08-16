package com.tz.docker;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author tz
 * @Classname UserBean
 * @Description
 * @Date 2019-08-15 21:02
 */
@Data
@Entity   //注意包名
@Table(name="user_info")
public class UserBean {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;
    private Integer age;
    private String sex;
    private String job;
    private Date birthday;
    private Date createdTime;
    private Date updateTime;
}
