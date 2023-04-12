package com.xufei.jpa.repository;

import com.xufei.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * JpaSpecificationExecutor 完成多条件查询，并且支持分页与排序。
 */

public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {


    @Transactional(timeout = 10)//根据需要添加 @Transactional 对事物的支持，查询超时的设置等
    @Modifying//如涉及到删除和修改在需要加上@Modifying
    @Query("delete from User  where id=?1")
    void deleteByUserId(Integer id);

    Page<User> findAllByUsernameLike(String username, Pageable pageable);

//    @Query("select u from User u where u.username = ?1 and u.password = ?2")
//    User getByUsernameAndPassword(String username, String password);

    @Query("select u from User u where u.username = :username and u.password = :password")
    User getByUsernameAndPassword(@Param("username")String username, @Param("password") String password);


    /**
     * nativeQuery=true时，value参数写的是原生sql
     */
    @Query(value = "select u.* from sys_user u where u.username = :username and u.password = :password",nativeQuery = true)
    User getByUsernameAndPasswordSQl(@Param("username")String username, @Param("password") String password);

    @Query("select u from User u where 1=1 " +
            " and(:username is null or u.username=:username)" +
            " and(:mobile is null or u.mobile=:mobile)")
    User findByUserNameOrMobile(@Param("username")String username, @Param("mobile") String mobile);


}
