package com.dailySync.todo.dto;

import com.dailySync.meal.dto.MealDayCntResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoCountResponseDto {

    private LocalDate date;
    private Long CNT;

    public static TodoCountResponseDto toDayCnt(TodoCountResponseDto todoCountResponseDto){

        return todoCountResponseDto.builder()
                .date(todoCountResponseDto.getDate())
                .CNT(todoCountResponseDto.getCNT())
                .build();
    }

}


