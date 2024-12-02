package com.dailySync.test.service;

import com.dailySync.test.dto.BookReqDto;
import com.dailySync.test.dto.BookResDto;
import com.dailySync.test.entities.Book;
import com.dailySync.test.entities.People;
import com.dailySync.test.repository.BookRepository;
import com.dailySync.test.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    public Book CreateBook(BookReqDto reqDto) {

        People people =peopleRepository.findById(reqDto.getPeopleId())
                .orElseThrow(()->new ResolutionException("??"));

        Book book = Book.builder()
                .title(reqDto.getTitle())
                .author(reqDto.getAuthor())
                .people(people)
                .build();
        return bookRepository.save(book);
    }

    public List<BookResDto> getAllBook(){
        return bookRepository.findAll()
                .stream().map(BookResDto::of)
                .collect(Collectors.toList());
    }

    public BookResDto getBookById(Long id){
        Book book = bookRepository.findById(id).orElse(null);
        return BookResDto.of(book);
    }

    public void postDeleteById(long id){
        bookRepository.deleteById(id);
    }

}
