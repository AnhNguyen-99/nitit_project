package com.niit.nitit_project.model.mapper;

import com.niit.nitit_project.entity.OrderWatch;
import com.niit.nitit_project.model.dto.OrderWatchDTO;

public class OrderWatchMapper {
    public static OrderWatch toOrderWatch(OrderWatchDTO orderWatchDTO){
        OrderWatch orderWatch = new OrderWatch();
        orderWatch.setId(orderWatchDTO.getId());
        orderWatch.setIdOrder(orderWatchDTO.getIdOrder());
        orderWatch.setIdWatch(orderWatchDTO.getIdWatch());
        orderWatch.setNameWatch(orderWatchDTO.getNameWatch());
        orderWatch.setCount(orderWatchDTO.getCount());
        orderWatch.setPrice(orderWatchDTO.getPrice());
        return orderWatch;
    }

    public static OrderWatchDTO toOrderWatchDTO(OrderWatch orderWatch){
        OrderWatchDTO orderWatchDTO = new OrderWatchDTO();
        orderWatchDTO.setId(orderWatch.getId());
        orderWatchDTO.setIdOrder(orderWatch.getIdOrder());
        orderWatchDTO.setIdWatch(orderWatch.getIdWatch());
        orderWatchDTO.setNameWatch(orderWatch.getNameWatch());
        orderWatchDTO.setCount(orderWatch.getCount());
        orderWatchDTO.setPrice(orderWatch.getPrice());
        return orderWatchDTO;
    }
}
