package com.xufei.demo.mongodb;

import com.xufei.demo.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: XuFei
 * @Date: 2022/8/16 11:15
 */

@Repository
public interface MyUserRepository extends MongoRepository<User,String> {

}
