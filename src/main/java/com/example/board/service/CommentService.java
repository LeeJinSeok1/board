package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.CommentEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;
    public void commentSave(CommentDTO commentDTO) {
        // 현재 commentDTO 에는 boardId와 writer ,contents가 들어 있음.
        // DTO를 Entity로 바꿔줄 toCommentSave로 사용하기 위해서는 boardId로 그 Entity객체
        //전체를 가져와야 함 그래서 comment.getboardId를 활용해서 boardRepository.findById를 활용해
        //그 객체 전체 를 가져온다 .
        Optional <BoardEntity> optionalBoardEntity= boardRepository.findById(commentDTO.getBoardId());
        // 옵셔널 까주고
        BoardEntity boardEntity = optionalBoardEntity.get();
        // 가져오고
        // 만들어 놓은 toCommentSave에 작성자 내용이 들어있는 DTO와 그 객체 Entity를 넘겨준다.
        CommentEntity commentEntity= CommentEntity.toCommentSave(commentDTO,boardEntity);
        // 그리고CommentEntity타입으로 변환

        // 그값을 Save 저장처리
        commentRepository.save(commentEntity);

    }

}
