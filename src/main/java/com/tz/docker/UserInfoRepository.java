package com.tz.docker;

import org.aspectj.weaver.ast.Var;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author tz
 * @Classname UserInfoRepository
 * @Description
 * @Date 2019-08-15 21:06
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserBean, Integer> {
}
