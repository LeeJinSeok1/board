package com.example.board.repository;

import com.example.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    @Modifying
    @Query(value="update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    void boardHits(@Param("id") Long id);

//    @Modifying
//    @Query(value="update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
//    void boardHits(@Param("id") Long id);


    // 작성자 검색
//    select*from board_table where board_writer like '%q%' order by id desc
    List<BoardEntity> findByBoardWriterContainingOrderByIdDesc(String q);

    // 제목 검색
    //    select*from board_table where board_writer like '%q%' order by id desc
    List<BoardEntity> findByBoardTitleContainingOrderByIdDesc(String q);

    // 제목  또는 작성자 검색
    //    select*from board_table where board_writer like '%q%' or board_title list '%q%' order by id desc
    List<BoardEntity> findByBoardTitleContainingOrBoardWriterContainingOrderByIdDesc(String title, String writer);
}
