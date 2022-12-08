package com.example.board.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="comment_table")
public class CommentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String commentWriter;
    @Column
    private String commentContents;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private BoardEntity boardEntity;

}
