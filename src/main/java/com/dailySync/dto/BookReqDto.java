package com.dailySync.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookReqDto {
    private String title;
    private String author;
    private Long peopleId;

}
