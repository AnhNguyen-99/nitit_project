package com.niit.nitit_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String receiver;
    @Column
    private String fullName;
    @Column
    private String phoneNumber;
    @Column
    private String address;
    @Column
    private Timestamp orderDate;
    @Column
    private int status;
    @Column
    private Double shipFee;
    @Column
    private Double totalPrice;
    @Column
    private Double totalPayment;
    @Column
    private Integer idUser;
}
