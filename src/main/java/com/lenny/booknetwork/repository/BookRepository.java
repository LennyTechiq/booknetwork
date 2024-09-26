package com.lenny.booknetwork.repository;

import com.lenny.booknetwork.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // You can define custom query methods here if needed, e.g.:
    // List<Book> findByTitleContaining(String title);
}
