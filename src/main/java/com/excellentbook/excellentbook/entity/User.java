package com.excellentbook.excellentbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@EqualsAndHashCode(exclude = {"roles", "desiredBooks", "orders", "ownBooks"}, callSuper = true)
@SuperBuilder
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    private String photoUrl;

    private Boolean active;

    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "buyers")
    private Set<Book> desiredBooks;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Book> ownBooks;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
    private Set<Order> orders;

}
