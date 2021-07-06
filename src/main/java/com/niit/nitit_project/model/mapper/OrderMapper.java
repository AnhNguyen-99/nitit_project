package com.niit.nitit_project.model.mapper;

import com.niit.nitit_project.entity.Order;
import com.niit.nitit_project.model.dto.OrderDTO;

public class OrderMapper {
    public static Order toOrder(OrderDTO orderDTO){
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setReceiver(orderDTO.getReceiver());
        order.setFullName(orderDTO.getFullName());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setAddress(orderDTO.getAddress());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(orderDTO.getStatus());
        order.setShipFee(orderDTO.getShipFee());
        order.setTotalPayment(orderDTO.getTotalPayment());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setIdUser(orderDTO.getIdUser());
        return order;
    }

    public static OrderDTO toOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setReceiver(order.getReceiver());
        orderDTO.setFullName(order.getFullName());
        orderDTO.setPhoneNumber(order.getPhoneNumber());
        orderDTO.setAddress(order.getAddress());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setShipFee(order.getShipFee());
        orderDTO.setTotalPayment(order.getTotalPayment());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setIdUser(order.getIdUser());
        return orderDTO;
    }
}
