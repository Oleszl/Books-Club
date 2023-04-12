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
public class Author extends BaseEntity{
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Book> books;
}
