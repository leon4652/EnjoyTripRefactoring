package com.sas.refactoring.freeBoard.controller;

import com.sas.refactoring.freeBoard.dto.FreeBoardDto;
import com.sas.refactoring.freeBoard.service.FreeBoardService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/freeboard")
@CrossOrigin("*")
@RestController
public class FreeBoardController {
    private final FreeBoardService freeBoardService;

    public FreeBoardController(FreeBoardService freeBoardService) {
        this.freeBoardService = freeBoardService;
    }

    @GetMapping
    public List<FreeBoardDto> showFreeBoard() throws Exception {
        return freeBoardService.showFreeBoardList();
    }

}
