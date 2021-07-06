package com.niit.nitit_project.model.mapper;

import com.niit.nitit_project.entity.CartWatch;
import com.niit.nitit_project.entity.Order;
import com.niit.nitit_project.entity.OrderWatch;

public class CartWatchToOrderWatch {
    public static OrderWatch toOrderWatch(CartWatch cartWatch){
        OrderWatch orderWatch = new OrderWatch();
        orderWatch.setIdWatch(cartWatch.getIdWatch());
        orderWatch.setNameWatch(cartWatch.getNameWatch());
        orderWatch.setCount(cartWatch.getCount());
        orderWatch.setPrice(cartWatch.getPrice());
        return orderWatch;
    }
}
