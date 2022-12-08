package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import com.example.board.repository.BoardFileRepository;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    public Long boardSave(BoardDTO boardDTO) throws IOException {
//        if(boardDTO.getBoardFile().isEmpty()){
        if(boardDTO.getBoardFile().size() == 0){
            System.out.println("파일없음");
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            return boardRepository.save(boardEntity).getId();

        }else{
            System.out.println("파일있음");
            // 게시글 정보를 먼저 저장하고 해당 게시글의 entity를 가져옴
            BoardEntity boardEntity= BoardEntity.toSaveFileEntity(boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity entity= boardRepository.findById(savedId).get();
            // 파일이 담긴 list를 반복문으로 접근하여 하나씩 이름 가져오고, 저장용 이름 만들고
            // 로컬 경로에 저장하고 board_file_table에 저장
            for (MultipartFile boardFile: boardDTO.getBoardFile()){
//                MultipartFile boardFile = boardDTO.getBoardFile()
                String originalFileName= boardFile.getOriginalFilename();
                String storedFileName= System.currentTimeMillis()+"_"+ originalFileName;
                String savePath = "D:\\springboot_img\\" + storedFileName;
                boardFile.transferTo(new File(savePath));
                BoardFileEntity boardFileEntity =
                        BoardFileEntity.toSaveBoardFileEntity(entity, originalFileName, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }

             return savedId;

        }


    }
    @Transactional
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
    @Transactional
    public BoardDTO boardDetail(Long id) {
        // Optional로 감싸진 boardEntity 타입으로 findById 를ㅎ 활용해서 받아오고
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        // 그 정보를 BoardEntity타입의  boardEntity에 get을 활용해서 넣어주고
        BoardEntity boardEntity = optionalBoardEntity.get();
        // 미리 정의 해둔 DTO에 set 메서드인 toDTO를 활용해서 그 안에 entity를 넣어준다
        BoardDTO boardDTO = BoardDTO.toDTO(boardEntity);
        // 그리고 리턴
        return boardDTO;
    }
    @Transactional
    public void boardHits(Long id) {
        boardRepository.boardHits(id);
    }

    public void boardUpdate(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }
}

