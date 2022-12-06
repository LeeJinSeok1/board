package com.example.board.test;

import com.example.board.dto.BoardDTO;
import com.example.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {
    @Autowired
    BoardService boardService;

    private BoardDTO newBoard(int i) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardTitle("test"+i);
        boardDTO.setBoardWriter("test" +i);
        boardDTO.setBoardPass("test"+i);
        boardDTO.setBoardContents("test"+i);
        return boardDTO;
    }


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
        BoardDTO findBoard = boardService.boardDetail(boardId);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("글작성 여러개")
    public void saveList() {
        for (int i=1; i<=20; i++) {
            boardService.boardSave(newBoard(i));
        }

        IntStream.rangeClosed(21, 40).forEach(i ->{
            boardService.boardSave(newBoard(i));
        });
    }
}
