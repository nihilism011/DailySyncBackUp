package com.dailySync.test.dto;

import com.dailySync.test.entities.People;
import lombok.*;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeopleResDto {
    private Long id;
    private String username;
    private String email;
    private List<String> books;

    public static PeopleResDto nullPeople(){
        return new PeopleResDto();
    }

    public static PeopleResDto of(People people) {
        if(people==null){
            return new PeopleResDto();
        }

        return PeopleResDto.builder()
                .id(people.getId())
                .username(people.getUsername())
                .email(people.getEmail())
                //.books(people.getBooks().stream().map(Book::getTitle).collect(Collectors.toList()))
                .build();
    }
}
