package com.xufei.jpa.controller;

import com.xufei.jpa.model.User;
import com.xufei.jpa.repository.UserRepository;
import com.xufei.jpa.repository.custom.UserCustomRepository;
import com.xufei.jpa.repository.custom.UserCustomRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    /**
     * UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User>
     *     JpaRepository<User,Integer> 支持分页查询和排序
     *     JpaSpecificationExecutor 可以完成多条件查询+分页+排序。
     */
    @Autowired
    UserRepository userRepository;


    @Autowired
    UserCustomRepositoryImpl userCustomImplRepsotory;
    /**
     * 测试使用源生的sql查询
     */
    private void nativeQuery() {
        User user = new User();
        user.setUsername("dominick_li");
        user.setChannelId(1);
        List<Object[]> list = userCustomImplRepsotory.findBynativeQuery(user);
        for (Object[] objs : list) {
            System.out.println(objs[0] + "," + objs[1]);
        }
    }

    /**
     * 没有条件的分页查询
     */
    private void selectUserByPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        Page<User> userPage = userRepository.findAllByUsernameLike("user%", pageable);
        System.out.println("总记录数" + userPage.getTotalElements() + ",总页数" + userPage.getTotalPages());
        for (User user : userPage.getContent()) {
            System.out.println(user.getUsername());
        }
    }
    /**
     *  单条件查询
     * 需求：根据用户姓名查询数据
     */
    public void test1(){
        Specification<User> spec = new Specification<User>() {
            /**
             * @return Predicate:定义了查询条件
             * @param Root<User> root:根对象。封装了查询条件的对象
             * @param CriteriaQuery<?> query:定义了一个基本的查询.一般不使用
             * @param CriteriaBuilder cb:创建一个查询条件
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("username"), "王五");
            }
        };
        List<User> list = userRepository.findAll(spec);
        for (User user : list) {
            System.out.println(user);
        }
    }
    /**
     * 多条件查询 方式一
     * 需求：使用用户姓名以及年龄查询数据
     */
    public void test2() {
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("username"), "王五"));
                list.add(cb.equal(root.get("userage"), 24));
                //此时条件之间是没有任何关系的。
                Predicate[] arr = new Predicate[list.size()];
                return cb.and(list.toArray(arr));
            }
        };
        List<User> list = userRepository.findAll(spec);
        for (User users : list) {
            System.out.println(users);
        }
    }

    /**
     * 多条件查询 方式二
     * 需求：使用用户姓名或者年龄查询数据
     */
    public void test3(){
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.or(cb.equal(root.get("username"),"王五 "),cb.equal(root.get("userage"), 25));
            }
        };
        List<User> list = userRepository.findAll(spec);
        for (User users : list) {
            System.out.println(users);
        }
    }
    /**
     * 使用hql进行多条件查询
     */
    private void SpecificationQuery() {
        String startDate = "2020-05-22";
        String endDate = "2020-05-25";
        String username = "dominick";
        Specification<User> querySpecifi = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(startDate)) {
                    //大于或等于传入时间
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate").as(String.class), startDate));
                }
                if (!StringUtils.isEmpty(endDate)) {
                    //小于或等于传入时间
                    predicates.add(cb.lessThanOrEqualTo(root.get("createdDate").as(String.class), endDate));
                }
                if (!StringUtils.isEmpty(username)) {
                    //模糊查询,需要自己手动拼接%%字符
                    predicates.add(cb.like(root.get("username").as(String.class), "%" + username + "%"));
                }
                // and到一起的话所有条件就是且关系，or就是或关系
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<User> userList = userRepository.findAll(querySpecifi);
        for (User user : userList) {
            System.out.println(user.getUsername());
        }
    }

    /**
     * 需求：查询王姓用户，并且做分页处理
     */
    public void test4(){
        //条件
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("username").as(String.class), "王%");
            }
        };
        //分页
        Pageable pageable = PageRequest.of(2, 2);
        Page<User> page = userRepository.findAll(spec, pageable);
        System.out.println("总条数："+page.getTotalElements());
        System.out.println("总页数："+page.getTotalPages());
        List<User> list = page.getContent();
        for (User user : list) {
            System.out.println(user);
        }
    }

    /**
     * 需求：查询数据库中王姓的用户，并且根据用户 id 做倒序排序，并且进行分页
     */
    public void test5(){
        //条件
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("username").as(String.class),"王%");
            }
        };
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        //排序
        Page<User> page = userRepository.findAll(spec, pageable);
        for (User user : page.getContent()) {
            System.out.println(user);
        }
    }


}
