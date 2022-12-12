package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final CommentService commentService;

    private final BoardService boardService;
    @GetMapping("/boardSave")
    public String boardSaveForm() {
        return "boardSavePage";
    }

    @PostMapping("/boardSave")
    public String boardSave(@ModelAttribute BoardDTO boardDTO) throws IOException {
       Long boardId = boardService.boardSave(boardDTO);
        return "redirect:boardList";
    }

    @GetMapping("boardList")
    public String boardList(Model model){
       List<BoardDTO> boardList= boardService.boardList();
       model.addAttribute("boardList",boardList);
       return "boardListPage";
    }

    @GetMapping("/board") //페이징
    public String paging(@PageableDefault(page =1)Pageable pageable, Model model) {
        System.out.println("page" +pageable.getPageNumber()); //넘겨받은 페이지 값
        Page<BoardDTO> boardDTOList= boardService.paging(pageable); //리스트 가져오기ㄴ
        model.addAttribute("boardList",boardDTOList);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOList.getTotalPages();
        // 삼항연산자
//        int test = 10;
//        int num = (test > 5)? test: 100; // 테스트값이 5 이상이여야 테스트가 100이 된다.
//        if(test>5){
//            num = test;
//        }else{
//            num = 100;
//        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardPaging";
    }

    @GetMapping("/boardDetail/{id}")
    public String boardDetail(@PathVariable Long id,
                              Model model){
        //조회수
        boardService.boardHits(id);
        //
        BoardDTO boardDTO = boardService.boardDetail(id);
        model.addAttribute("board",boardDTO);

        List<CommentDTO> commentDTOList = commentService.commentList(id);
        if (commentDTOList.size() > 0) {
            model.addAttribute("commentList", commentDTOList);
        } else {
            model.addAttribute("commentList", "empty");
        }
        return "boardDetailPage";
    }

    @GetMapping("/boardUpdate/{id}")
    public String boardUpdatePage(@PathVariable Long id,
                              Model model){
        BoardDTO boardDTO= boardService.boardDetail(id);
        model.addAttribute("board",boardDTO);
        return "boardUpdatePage";
    }

    @PostMapping("/boardUpdate")
    public String boardUpdate(@ModelAttribute BoardDTO boardDTO,Model model){
        boardService.boardUpdate(boardDTO);
        BoardDTO board = boardService.boardDetail(boardDTO.getId());
        model.addAttribute("board",board);
        return "boardDetailPage";
    }

    @GetMapping("/boardDelete/{id}")
    public String boardDelete(@PathVariable Long id,
                              Model model){
        boardService.boardDelete(id);
        List<BoardDTO> boardList= boardService.boardList();
        model.addAttribute("boardList",boardList);
        return "boardListPage";
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody BoardDTO boardDTO){
        boardService.boardUpdate(boardDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/boardSearch")
    public String search(@RequestParam("type") String type,@RequestParam("q") String q,
                         Model model){
        List<BoardDTO> searchList = boardService.search(type,q);
        System.out.println(searchList);
        model.addAttribute("boardList",searchList);
        return "boardListPage";

    }


}
