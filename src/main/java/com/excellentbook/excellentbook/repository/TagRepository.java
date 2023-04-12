package com.excellentbook.excellentbook.repository;

import com.excellentbook.excellentbook.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
