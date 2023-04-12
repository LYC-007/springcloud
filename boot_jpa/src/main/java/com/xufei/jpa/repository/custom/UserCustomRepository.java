package com.xufei.jpa.repository.custom;




import com.xufei.jpa.model.User;


import java.util.List;

public interface UserCustomRepository {
    List<Object[]> findBynativeQuery(User user);

}
