package com.niit.nitit_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String fullName;
    @Column
    private String phoneNumber;
    @Column
    private int age;
    @Column
    private String address;
    @Column
    private Timestamp createdDate;
    @Column
    private Timestamp modifiedDate;
    @Column
    private Integer createdBy;
    @Column
    private Integer modifiedBy;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoong sử dụng trong toString()
    @JoinTable(name = "user_role", //Tạo ra một join Table
            joinColumns = @JoinColumn(name = "id_user"),  // TRong đó, khóa ngoại chính là address_id trỏ tới class hiện tại
            inverseJoinColumns = @JoinColumn(name = "id_role") //Khóa ngoại thứ 2 trỏ tới thuộc tính
    )
    @JsonIgnore
    private List<Role> roles;
}
