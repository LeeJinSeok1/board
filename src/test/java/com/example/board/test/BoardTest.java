package com.example.board.test;

import com.example.board.dto.BoardDTO;
import com.example.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class BoardTest {
    @Autowired
    BoardService boardService;

    @Test
    @Transactional
    @Rollback(value = true)
    public void boardSaveTest() {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardWriter("tester");
        boardDTO.setBoardTitle("testTitle");
        boardDTO.setBoardPass("testPass");
        boardDTO.setBoardContents("testContents");
        Long boardId = boardService.boardSave(boardDTO);
    }
}
