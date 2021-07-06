package com.niit.nitit_project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartWatchDTO {
    private Integer id;
    private Integer idCart;
    private Integer idWatch;
    private String nameWatch;
    private Long count;
    private Double price;
    private String image;
}
