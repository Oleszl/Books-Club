package com.excellentbook.excellentbook.repository;

import com.excellentbook.excellentbook.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
