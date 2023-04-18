package com.excellentbook.excellentbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@Table(name = "user_order")
@Entity
@EqualsAndHashCode(exclude = {"owner", "buyer", "book"}, callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private User buyer;
}
