package com.niit.nitit_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "watch")
public class Watch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String feature;
    @Column
    private Double price;
    @Column
    private Double reducedPrice;
    @Column
    private Double salePrice;
    @Column
    private int status;
    @Column
    private Timestamp createdDate;
    @Column
    private Timestamp modifiedDate;
    @Column
    private Integer createdBy;
    @Column
    private Integer modifiedBy;
    @Column
    private Integer idBrand;
}
