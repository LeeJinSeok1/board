package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void boardSave(BoardDTO boardDTO) {
       BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    public List<BoardDTO> boardList() {
        // 데이터 베이스에 모든 정보를 가지고 온다.
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        // 정보를 넣어줄 DTO타입의 arrayList를 만들어 주고
        List<BoardDTO> boardDTOList = new ArrayList<>();
        //반복문을 사용해서 모두 까준다(리스트를하나씩 열어준다)
        for (BoardEntity boardEntity: boardEntityList) {
            // 그걸 dto 타입에 미리 DTO 클라스에서 정의 해 놓은 toDTO 객체에 entity를 넣어주고
            BoardDTO boardDTO = BoardDTO.toDTO(boardEntity);
            //그 정보를 만들어 둔 ArrayList에 담아서 리턴
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }
}
//    List<MemberEntity> memberEntityList = memberRepository.findAll();
//
//    List<MemberDTO> memberDTOList = new ArrayList<>();
//
//        for (MemberEntity memberEntity: memberEntityList){
//                MemberDTO memberDTO= MemberDTO.toDTO(memberEntity);
//                memberDTOList.add(memberDTO);
//                }
//                return memberDTOList;
//
//                }
