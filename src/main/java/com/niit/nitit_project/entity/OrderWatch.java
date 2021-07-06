package com.niit.nitit_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_watch")
public class OrderWatch implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer idOrder;
    @Column
    private Integer idWatch;
    @Column
    private String nameWatch;
    @Column
    private Long count;
    @Column
    private Double price;
}
