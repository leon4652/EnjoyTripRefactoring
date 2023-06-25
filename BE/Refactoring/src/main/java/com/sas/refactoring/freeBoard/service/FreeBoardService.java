package com.sas.refactoring.freeBoard.service;

import com.sas.refactoring.freeBoard.dto.FreeBoardDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FreeBoardService {
    List<FreeBoardDto> showFreeBoardList() throws Exception;
}
