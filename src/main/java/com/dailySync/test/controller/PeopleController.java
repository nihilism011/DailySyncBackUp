package com.dailySync.test.controller;

import com.dailySync.test.dto.PeopleReqDto;
import com.dailySync.test.dto.PeopleResDto;
import com.dailySync.test.service.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeopleController {
    final private PeopleService peopleService;

    @GetMapping("people")
    public ResponseEntity<?> getAllPeople(){
        List<PeopleResDto> list = peopleService.getAllPeople();
        return ResponseEntity.ok(list);
    }

    @GetMapping("people/{id}")
    public ResponseEntity<?> getPeopleByid(@PathVariable Long id){
        return ResponseEntity.ok(peopleService.getPeopleById(id));
    }

    @PostMapping("people")
    public ResponseEntity<?> postCreatePeople(@ModelAttribute PeopleReqDto reqDto ) {
        return ResponseEntity.ok(peopleService.createPeople(reqDto));
    }

    @DeleteMapping("people/delete/{id}")
    public ResponseEntity<?> deletePeople(@PathVariable Long id) {
        peopleService.deletePeople(id);
        return ResponseEntity.ok().build();
    }
}
