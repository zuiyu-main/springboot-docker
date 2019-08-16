package com.tz.docker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author tz
 * @Classname UserController
 * @Description
 * @Date 2019-08-15 21:08
 */
@RestController
public class UserController {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @RequestMapping("/getUserInfo")
    public Object getUserInfo() {
        /*
            这里有一个大坑：使用getOne()的方法，可以查到数据。但是无法默认转化成JSON数据，返回接口直接报错
        */
        //UserInfo userInfo = userInfoRepository.getOne(1);
        for(int i = 0;i<10;i++){
            UserBean userBean = new UserBean();
            userBean.setAge(12);
            userBean.setBirthday(new Date());
            userBean.setCreatedTime(new Date());
            userBean.setJob("3333:"+i);
            userBean.setSex("1");
            userInfoRepository.save(userBean);
        }
        List<UserBean> all = userInfoRepository.findAll();
        return all;
    }
}
