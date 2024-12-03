package com.dailySync.test.dto;

import com.dailySync.test.entities.Book;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookResDto  {
    private String title;
    private String author;
    private String peopleId;

    public static BookResDto nullBook(){
        return new BookResDto();
    }

    public static BookResDto of(Book book){
        if(book == null){
            return new BookResDto();
        }
        return BookResDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .peopleId(String.valueOf(book.getPeople()))
                .build();
    }

}
