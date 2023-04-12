package com.xufei.common.service;


import com.xufei.common.domain.UserAddress;

import java.util.List;

public interface ConsumerService {
    List<UserAddress> initOrder(String userId);

}
