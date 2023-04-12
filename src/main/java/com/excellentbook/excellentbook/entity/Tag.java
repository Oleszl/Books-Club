package com.excellentbook.excellentbook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@EqualsAndHashCode(exclude = "books", callSuper = true)
public class Tag extends BaseEntity{
    private String name;
    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "tags")
    private Set<Book> books;
}
