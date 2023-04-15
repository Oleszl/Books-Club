package com.excellentbook.excellentbook.repository;

import com.excellentbook.excellentbook.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findBooksByStatusAndNameLikeIgnoreCase(String status, Pageable pageable, String name);

}
