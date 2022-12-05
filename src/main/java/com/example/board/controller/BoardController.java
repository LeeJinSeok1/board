package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String boardSave(@ModelAttribute BoardDTO boardDTO){
        boardService.boardSave(boardDTO);
        return "boardListPage";
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
        BoardDTO boardDTO = boardService.boardDetail(id);
        model.addAttribute("board",boardDTO);
        return "boardDetailPage";
    }
}
