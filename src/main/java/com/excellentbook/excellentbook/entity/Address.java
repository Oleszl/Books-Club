package com.excellentbook.excellentbook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@EqualsAndHashCode(exclude = "", callSuper = true)
public class Address extends BaseEntity {

    private String buildNumber;

    private String city;

    private String region;

    private String street;

    private String zipCode;
}
