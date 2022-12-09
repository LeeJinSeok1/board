package com.example.board.test;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.CommentEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    private Object assertThat;

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
    
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("댓글 작성 테스트")
    public void commentSave() throws IOException {
        //1. 게시글 작성 
        BoardDTO boardDTO= newBoard(100);
        Long savedId= boardService.boardSave(boardDTO);
        //2. 댓글 작성 
        CommentDTO commentDTO= newComment(savedId, 1);
        Long commentSavedId = commentService.commentSave(commentDTO);
        CommentEntity commentEntity= commentRepository.findById(commentSavedId).get();
   
//        assertThat(commentDTO.getCommentWriter().isEqualToS)
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("댓글 목록 테스트")
    public void commentListTest() throws IOException {
        //1. 게시글작성
        BoardDTO boardDTO= newBoard(100);
        Long savedId= boardService.boardSave(boardDTO);
        //2. 해당 게시글에 댓글 3개 자성
        IntStream.rangeClosed(1,3).forEach(i->{
            CommentDTO commentDTO =newComment(savedId,i);

            commentService.commentSave(commentDTO);
        });

        //3. 댓글 목록 조회했을 때 목록 갯수가 3이면 테스트 통괴
        List<CommentDTO> commentDTOList = commentService.commentList(savedId);
//        assertThat(commentDTOList.size().isEqualTo(3));
    }
    
    
    private CommentDTO newComment(Long boardId,int i ){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentWriter("writer"+i);
        commentDTO.setCommentContents("contents"+i);
        commentDTO.setBoardId(boardId);
        return commentDTO;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("페이징 객체 확인")
    public void pagingParams() {
        int page = 2;
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id")));
        // Page 객체가 제공해주는 메서드 확인
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막페이지인지 여부

        //Page<BoardEntity> -> Page<BoardDTO>
        Page<BoardDTO> boardList = boardEntities.map(
                board -> new BoardDTO(board.getId(),
                                         board.getBoardWriter(),
                                         board.getBoardTitle(),
                                         board.getBoardHits(),
                                         board.getBoardCreatedTime()
                )

        );
        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막페이지인지 여부
    }






}
