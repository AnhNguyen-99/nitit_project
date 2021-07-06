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
public class CartDTO {
    private Integer id;
    private Integer idUser;
    private String username;
    private Long totalWatch;
    private Double totalPrice;
    private Timestamp createdDate;
    private String totalPriceFormat;
}
