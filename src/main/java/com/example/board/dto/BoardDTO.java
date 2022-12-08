package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<MultipartFile> boardFile;
    private int fileAttached;
//    데이터의 파일이름
    private List<String> originalFileName;
//    저장된 이름
    private List<String> storedFileName;

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
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
//            boardDTO.setFileAttached(1);
            //파일 가져오기
//            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
//            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()){
                // originalFileName 이 List이기 때문에 add()를 이용하여 파일
                //boardFileEntity에  있는 originalFileName을 옮겨 담음.
//                boardDTO.getOriginalFileName().add(boardFileEntity.getOriginalFileName());
//                boardDTO.getStoredFileName().add(boardFileEntity.getStoredFileName());
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);

        }else{
            //첨부파일 없음
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0 or 1
//            boardDTO.setFileAttached(0);
        }

        return boardDTO;
    }


}