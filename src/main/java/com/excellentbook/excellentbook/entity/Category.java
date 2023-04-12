package com.excellentbook.excellentbook.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@EqualsAndHashCode(exclude = "books", callSuper = true)
public class Category extends BaseEntity{
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Book> books;
}
