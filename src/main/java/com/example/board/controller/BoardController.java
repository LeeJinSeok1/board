package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

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

    @GetMapping("/boardDetail/{id}")
    public String boardDetail(@PathVariable Long id,
                              Model model){
        //조회수
        boardService.boardHits(id);
        //
        BoardDTO boardDTO = boardService.boardDetail(id);
        model.addAttribute("board",boardDTO);
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


}
