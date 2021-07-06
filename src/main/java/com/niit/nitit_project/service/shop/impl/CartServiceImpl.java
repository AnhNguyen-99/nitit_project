package com.niit.nitit_project.service.shop.impl;

import com.niit.nitit_project.entity.Cart;
import com.niit.nitit_project.entity.CartWatch;
import com.niit.nitit_project.entity.User;
import com.niit.nitit_project.entity.Watch;
import com.niit.nitit_project.model.dto.CartDTO;
import com.niit.nitit_project.model.dto.CartWatchDTO;
import com.niit.nitit_project.model.mapper.CartMapper;
import com.niit.nitit_project.model.mapper.CartWatchMapper;
import com.niit.nitit_project.model.response.ResponseNormal;
import com.niit.nitit_project.repository.CartRepository;
import com.niit.nitit_project.repository.CartWatchRepository;
import com.niit.nitit_project.repository.ImageRepository;
import com.niit.nitit_project.repository.WatchRepository;
import com.niit.nitit_project.sercurity.CustomUserDetails;
import com.niit.nitit_project.service.shop.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartWatchRepository cartWatchRepository;
    @Autowired
    private WatchRepository watchRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ResponseNormal<CartWatchDTO> addCart(CartWatchDTO cartWatchDTO) {
        try{
            //Lấy user hiện tại
            User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal())
                    .getUser();
            //Lấy sản phẩm cần được thêm vào cart
            Watch watch = watchRepository.getById(cartWatchDTO.getIdWatch());
            //Lấy cart hiện tại của user có status = 0 (đang đặt hàng)
            List<Cart> carts = cartRepository.findByIdUser(user.getId());
            if(cartWatchDTO.getCount() == null){
                cartWatchDTO.setCount(1L);
            }
            if(carts.size() == 0){
                //Tạo cart mới
                Cart cart = new Cart();
                cart.setIdUser(user.getId());
                cart.setUsername(user.getUsername());
                cart.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
                cart.setStatus(0);
                Cart cartCurrent = cartRepository.save(cart);
                //Tạo cart watch mới
                CartWatch cartWatch = new CartWatch();
                cartWatch.setIdCart(cartCurrent.getId());
                cartWatch.setIdWatch(watch.getId());
                cartWatch.setNameWatch(watch.getName());
                cartWatch.setCount(cartWatchDTO.getCount());
                cartWatch.setPrice(watch.getSalePrice());
                cartWatchRepository.save(cartWatch);
                //Set lại tổng giá tiền rồi lưu lại
                cartCurrent.setTotalPrice(cartWatch.getPrice() * cartWatch.getCount());
                cartRepository.save(cart);
            }
            else {
                //Lấy cart hiện tại để thêm watch vào
                Cart cart = carts.get(0);
                Optional<CartWatch> itemFind = cartWatchRepository
                                        .findByIdCartAndIdWatch(cart.getId(), watch.getId());
                if(!itemFind.isPresent()){
                    //Giỏ hàng chưa có sản phẩm thì thêm mới vào
                    CartWatch item = new CartWatch();
                    item.setIdCart(cart.getId());
                    item.setIdWatch(watch.getId());
                    item.setCount(cartWatchDTO.getCount());
                    item.setNameWatch(watch.getName());
                    item.setPrice(watch.getSalePrice());
                    cartWatchRepository.save(item);
                }else {
                    //Nếu tồn tại sản phẩm thì + số lượng hàng cần thêm vào Cart watch đó
                    CartWatch item = itemFind.get();
                    Long count = item.getCount();
                    item.setCount(count + cartWatchDTO.getCount());
                    cartWatchRepository.save(item);
                }
                //Cập nhật lại tổng giá tiền giỏ hàng
                Double price = cart.getTotalPrice();
                price += watch.getSalePrice() * cartWatchDTO.getCount();
                cart.setTotalPrice(price);
                cartRepository.save(cart);
            }
            return new ResponseNormal<>(HttpStatus.OK, "Thêm vào giỏ hàng thành công", null);
        }catch (Exception e){
            //Không lấy được user hiện tại
            System.out.println("ERROR "+ e.getMessage());
            return new ResponseNormal<>(HttpStatus.FORBIDDEN, "Bạn cần phải đăng nhập", null);
        }
    }

    @Override
    public Model loadCart(Model model) {
        try{
            //Lấy user hiện tại
            User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal())
                    .getUser();
            //Lấy cart của user hiện tại
            List<Cart> carts = cartRepository.findByIdUser(user.getId());
            if(carts.size() == 0){
                model.addAttribute("cart", new CartDTO());
                model.addAttribute("listItem", new ArrayList<CartWatchDTO>());
                return model;
            }else {
                CartDTO cartDTO = CartMapper.toCartDTO(carts.get(0));
                Long totalWatch = 0L;
                List<CartWatch> cartWatchList = cartWatchRepository.findByIdCart(cartDTO.getId());
                List<CartWatchDTO> listItem = new ArrayList<>();
                for(CartWatch x : cartWatchList){
                    totalWatch += x.getCount();
                    CartWatchDTO cartWatchDTO = CartWatchMapper.toCartWatchDTO(x);
                    cartWatchDTO.setImage(imageRepository.getListByIdWatch(x.getIdWatch()).get(0).getLink());
                    listItem.add(cartWatchDTO);
                }
                //Lấy tổng số lượng sản phẩm
                cartDTO.setTotalWatch(totalWatch);
                DecimalFormat df = new DecimalFormat("#,###.##");
                cartDTO.setTotalPriceFormat(df.format(cartDTO.getTotalPrice()));
                model.addAttribute("cart", cartDTO);
                model.addAttribute("listItem", listItem);
                return model;
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("cart", new CartDTO());
            model.addAttribute("listItem", new ArrayList<CartWatchDTO>());
            return model;
        }
    }

    @Override
    public ResponseNormal<?> deleteCartWatch(Integer id) {
        //Lấy item trong cart để tính toán lại số lương và số tiền cart
        CartWatch cartWatch = cartWatchRepository.findById(id).get();
        //Lấy user hiện tại
        User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        //Lấy cart của user hiện tại
        Cart cart = cartRepository.findByIdUser(user.getId()).get(0);
        //Tính toán lại số tiền
        Double totalPrice = cart.getTotalPrice() - (cartWatch.getPrice() * cartWatch.getCount());
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
        cartWatchRepository.deleteById(id);
        return new ResponseNormal<>(HttpStatus.OK, "Xóa thành công", null);
    }

    @Override
    public ResponseNormal<?> changeAmountCartWatch(CartWatchDTO cartWatchDTO) {
        //Lấy item trong cart để tính toán lại số lương và số tiền cart
        CartWatch cartWatch = cartWatchRepository.findById(cartWatchDTO.getId()).get();
        //Lấy cart của user hiện tại
        User user = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
        Cart cart = cartRepository.findByIdUser(user.getId()).get(0);
        //Tính toán lại số lương sản phẩm, tổng tiền trong cart
        if(cartWatchDTO.getCount() > cartWatch.getCount()){
            //Nếu số lượng thêm vào lớn hơn số lượng hiện có
            Double totalPrice = cart.getTotalPrice() +
                    ((cartWatchDTO.getCount() - cartWatch.getCount()) * cartWatch.getPrice());
            cartWatch.setCount(cartWatchDTO.getCount());
            cart.setTotalPrice(totalPrice);
        }else {
            //Nếu số lượng thêm vào nhỏ hơn số lượng hiện có
            Double totalPrice = cart.getTotalPrice() -
                    ((cartWatch.getCount() - cartWatchDTO.getCount()) * cartWatch.getPrice());
            cartWatch.setCount(cartWatchDTO.getCount());
            cart.setTotalPrice(totalPrice);
        }
        cartWatchRepository.save(cartWatch);
        cartRepository.save(cart);
        return new ResponseNormal<>(HttpStatus.OK, "Thay đổi số lượng thành công!", null);
    }
}
