package com.example.board.test;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;

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
    public void boardSaveTest() throws IOException {
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
    public void saveList() throws IOException {
        for (int i=1; i<=20; i++) {
            boardService.boardSave(newBoard(i));
        }

//        IntStream.rangeClosed(21, 40).forEach(i ->{
//            boardService.boardSave(newBoard(i));
//        });
    }


    @Test
    @Transactional
    @DisplayName("연관관계 조회 테스트")
    public void findTest() {
        // 파일이 첨부된 게시글 조회
        BoardEntity boardEntity = boardRepository.findById(2L).get();
        // 첨부 파일의 originalFileName 을 조회
        System.out.println(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
        // native query

    }






}
