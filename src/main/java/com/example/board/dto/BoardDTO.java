package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardPass;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
//멀티플로 데이터 넣고 끄집어 내는 역할
    private MultipartFile boardFile;
    private int fileAttached;
//    데이터의 파일이름
    private String originalFileName;
//    저장된 이름
    private String storedFileName;

    public static BoardDTO toDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getBoardCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getBoardUpdatedTime());

        //파일 관련된 내용 추가
        if(boardEntity.getFileAttached() ==1){
            //첨부파일 있음
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0 or 1
//            boardDTO.setFileAttached(1);
            //파일 가져오기
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());

        }else{
            //첨부파일 없음
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0 or 1
//            boardDTO.setFileAttached(0);
        }

        return boardDTO;
    }


}