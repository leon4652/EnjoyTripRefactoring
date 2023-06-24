package com.sas.refactoring.freeBoard.controller;

import com.sas.refactoring.freeBoard.service.FreeBoardService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/freeboard")
@CrossOrigin("*")
@RestController
public class FreeBoardController {
    private final FreeBoardService freeBoardService;

    public FreeBoardController(FreeBoardService freeBoardService) {
        this.freeBoardService = freeBoardService;
    }

}
