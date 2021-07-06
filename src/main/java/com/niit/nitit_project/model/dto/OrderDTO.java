package com.niit.nitit_project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    private String receiver;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Timestamp orderDate;
    private Long totalWatch;
    // 0: đang chờ xác nhận
    // 1: Đã xác nhận
    // 2: đang giao hàng
    // 3: Đã giao hàng
    private int status;
    private Double shipFee;
    private Double totalPrice;
    private Double totalPayment;
    private Integer idUser;
    private String totalPriceFormat;
    private String totalPaymentFormat;
}
