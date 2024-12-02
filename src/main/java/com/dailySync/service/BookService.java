package com.dailySync.service;

import com.dailySync.dto.BookReqDto;
import com.dailySync.dto.BookResDto;
import com.dailySync.dto.PeopleResDto;
import com.dailySync.entities.Book;
import com.dailySync.entities.People;
import com.dailySync.repository.BookRepository;
import com.dailySync.repository.PeopleRepository;
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
