package com.niit.nitit_project.service.shop.impl;

import com.niit.nitit_project.entity.*;
import com.niit.nitit_project.model.dto.OrderDTO;
import com.niit.nitit_project.model.dto.OrderWatchDTO;
import com.niit.nitit_project.model.mapper.CartWatchToOrderWatch;
import com.niit.nitit_project.model.mapper.OrderMapper;
import com.niit.nitit_project.model.mapper.OrderWatchMapper;
import com.niit.nitit_project.repository.*;
import com.niit.nitit_project.sercurity.CustomUserDetails;
import com.niit.nitit_project.service.shop.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("${order.shipFee}")
    public Double shipFee;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderWatchRepository orderWatchRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartWatchRepository cartWatchRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Model loadPageCheckout(Model model) {
        //Lấy user hiện tại
        User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        //Tạo order mới
        Order order = new Order();
        order.setReceiver(user.getUsername());
        order.setFullName(user.getFullName());
        order.setPhoneNumber(user.getPhoneNumber());
        order.setAddress(user.getAddress());
        order.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));
        List<Cart> carts = cartRepository.findByIdUser(user.getId());
        if(carts.size() == 0){
            model.addAttribute("order", new OrderDTO());
            return model;
        }
        Cart cart= carts.get(0);
        order.setTotalPrice(cart.getTotalPrice());
        order.setShipFee(shipFee);
        Double totalPayment = cart.getTotalPrice() + shipFee;
        order.setTotalPayment(totalPayment);
        OrderDTO orderDTO = OrderMapper.toOrderDTO(order);
        DecimalFormat df = new DecimalFormat("#,###.##");
        orderDTO.setTotalPriceFormat(df.format(order.getTotalPrice()));
        orderDTO.setTotalPaymentFormat(df.format(order.getTotalPayment()));
        model.addAttribute("order", orderDTO);
        return model;
    }

    @Override
    public void processCheckout(OrderDTO orderDTO) {
        //Lấy user hiện tại
        User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        //Lưu order vào database
        orderDTO.setIdUser(user.getId());
        orderDTO.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));
        orderDTO.setStatus(0);
        OrderDTO result = OrderMapper.toOrderDTO(orderRepository.save(OrderMapper.toOrder(orderDTO)));
        //Lưu các order watch vào db
        Cart cart = cartRepository.findByIdUser(user.getId()).get(0);
        List<OrderWatch> orderWatchList = cartWatchRepository.findByIdCart(cart.getId())
                                            .stream()
                                            .map(o -> {
                                                OrderWatch ow = CartWatchToOrderWatch.toOrderWatch(o);
                                                ow.setIdOrder(result.getId());
                                                orderWatchRepository.save(ow);
                                                return ow;
                                            })
                                            .collect(Collectors.toList());
        //Thay đổi status cart thành 1
        cart.setStatus(1);
        cartRepository.save(cart);
    }

    @Override
    public Model loadPageDisplayStatusOrder(Model model) {
        //Lấy order theo id user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        List<Order> orders = orderRepository.findByIdUser(user.getId());
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order o : orders) {
            DecimalFormat df = new DecimalFormat("#,###.##");
            OrderDTO res = OrderMapper.toOrderDTO(o);
            res.setTotalPriceFormat(df.format(res.getTotalPrice()));
            res.setTotalPaymentFormat(df.format(res.getTotalPayment()));
            orderDTOS.add(res);
        }
        model.addAttribute("orderDTOS", orderDTOS);
        return model;
    }

    @Override
    public Model loadPageOrderDetail(Integer id, Model model) {
        //Lấy order theo id
        OrderDTO orderDTO = OrderMapper.toOrderDTO(orderRepository.findById(id).get());
        //Lấy danh sách item trong order
        Long totalWatch = 0L;
        List<OrderWatch> orderWatchList = orderWatchRepository.findByIdOrder(id);
        List<OrderWatchDTO> orderWatchDTOList = new ArrayList<>();
        for(OrderWatch x : orderWatchList){
            totalWatch += x.getCount();
            OrderWatchDTO orderWatchDTO = OrderWatchMapper.toOrderWatchDTO(x);
            orderWatchDTO.setImage(imageRepository.getListByIdWatch(x.getIdWatch()).get(0).getLink());
            orderWatchDTOList.add(orderWatchDTO);
        }
        orderDTO.setTotalWatch(totalWatch);
        DecimalFormat df = new DecimalFormat();
        orderDTO.setTotalPaymentFormat(df.format(orderDTO.getTotalPayment()));
        orderDTO.setTotalPriceFormat(df.format(orderDTO.getTotalPrice()));
        model.addAttribute("order", orderDTO);
        model.addAttribute("listOrderItem", orderWatchDTOList);
        return model;
    }
}
