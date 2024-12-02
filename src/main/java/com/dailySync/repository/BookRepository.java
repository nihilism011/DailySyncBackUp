package com.dailySync.repository;

import com.dailySync.dto.BookResDto;
import com.dailySync.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface BookRepository extends JpaRepository<Book, Long> {

}
