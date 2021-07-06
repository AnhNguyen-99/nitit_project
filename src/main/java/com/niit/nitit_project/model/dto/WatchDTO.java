package com.niit.nitit_project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WatchDTO {
    private Integer id;
    private String code;
    private String name;
    private String description;
    private String feature;
    private Double price;
    private Double reducedPrice;
    private Double salePrice;
    private int status;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private Integer createdBy;
    private Integer modifiedBy;
    private Integer idBrand;
    private String brandName;
    private List<ImageDTO> imageDTOList;
}
