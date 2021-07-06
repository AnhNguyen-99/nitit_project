package com.niit.nitit_project.service.manage.Impl;

import com.niit.nitit_project.entity.Order;
import com.niit.nitit_project.entity.OrderWatch;
import com.niit.nitit_project.model.dto.OrderDTO;
import com.niit.nitit_project.model.dto.OrderWatchDTO;
import com.niit.nitit_project.model.mapper.OrderMapper;
import com.niit.nitit_project.model.mapper.OrderWatchMapper;
import com.niit.nitit_project.model.response.ResponseNormal;
import com.niit.nitit_project.repository.ImageRepository;
import com.niit.nitit_project.repository.OrderRepository;
import com.niit.nitit_project.repository.OrderWatchRepository;
import com.niit.nitit_project.service.manage.ManageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageOrderServiceImpl implements ManageOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderWatchRepository orderWatchRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Model loadPage(Model model, Integer page, Integer limit) {
        //Lấy danh sách order
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        Page<OrderDTO> orderDTOPage = orderRepository.findAll(pageable).map(o-> OrderMapper.toOrderDTO(o));
        model.addAttribute("orderPage", orderDTOPage);
        return model;
    }

    @Override
    public Model loadDetailProductPage(Model model, Integer idOrder) {
        //Lấy danh sách item trong order
        List<OrderWatchDTO> orderWatchDTOList = orderWatchRepository
                .findByIdOrder(idOrder)
                .stream()
                .map(o-> {
                    OrderWatchDTO ow = OrderWatchMapper.toOrderWatchDTO(o);
                    ow.setImage(imageRepository.findByIdWatch(ow.getIdWatch()).get(0).getLink());
                    return ow;
                })
                .collect(Collectors.toList());
        model.addAttribute("listOrderWatch", orderWatchDTOList);
        //Lấy thông tin order
        OrderDTO orderDTO = OrderMapper.toOrderDTO(orderRepository.findById(idOrder).get());
        model.addAttribute("order", orderDTO);
        return model;
    }

    @Override
    public ResponseNormal<?> confirmOrder(Integer idOrder) {
        Order order = orderRepository.findById(idOrder).get();
        if(order.getStatus() == 3){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Đơn đặt hàng đã được giao!", null);
        }
        int status = order.getStatus() +1;
        order.setStatus(status);
        orderRepository.save(order);
        String msg = new String();
        if(status == 1){
            msg = "Đơn hàng chuyển sang trạng thái đã xác nhận!";
        }else if(status == 2){
            msg = "Đơn hàng chuyển sang trạng thái đang giao!";
        } else{
            msg = "Đơn hàng chuyển sang trạng thái đã giao!";
        }
        return new ResponseNormal<>(HttpStatus.OK, msg, null);
    }

    @Override
    public ResponseNormal<?> changeStatusOrder(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId()).get();
        if(order.getStatus() == orderDTO.getStatus()){
            return new ResponseNormal<>(HttpStatus.BAD_REQUEST, "Trạng thái đơn hàng không thay đổi!", null);
        }
        order.setStatus(orderDTO.getStatus());
        orderRepository.save(order);
        return new ResponseNormal<>(HttpStatus.OK, "Thay đổi trạng thái thành công!", null);
    }
}
