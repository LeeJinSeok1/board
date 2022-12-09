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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;
    @Transactional
    public Long commentSave(CommentDTO commentDTO) {
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
        return commentRepository.save(commentEntity).getId();

    }
    @Transactional
    public List<CommentDTO> commentList(Long boardId) {
        // 두가지 방법 모두 사용됨  조회할때 아이디가 아닌 entity로 조회를 해야하기 때문에 가져온다
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardId);
        BoardEntity boardEntity = optionalBoardEntity.get();
        // 1. comment_table에서 직접 해당 게시글의 댓글 목록을 가져오기
//        List<CommentEntity> commentEntityList =
//                commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);


        // 2. BoardEntity를 조회해서 댓글 목록 가져오기
        List<CommentEntity> result= boardEntity.getCommentEntityList();


        // DTO로 변환해주기
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: result){
            CommentDTO commentDTO1 = CommentDTO.toCommentList(commentEntity);
            commentDTOList.add(commentDTO1);
        }
        return commentDTOList;



    }
}
