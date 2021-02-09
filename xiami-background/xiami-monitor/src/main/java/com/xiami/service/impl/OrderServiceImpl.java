package com.xiami.service.impl;

import com.xiami.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­19 12:19
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public void sayHi() {
        System.out.println("新的一天开始了");
    }
}
