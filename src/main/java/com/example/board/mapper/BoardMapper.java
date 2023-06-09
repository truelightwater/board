package com.example.board.mapper;

import com.example.board.model.BoardRequest;
import com.example.board.model.BoardResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

/**
* 게시글 저장
*  @param param - 게시글 정보
 *  */
    void save(BoardRequest param);

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    BoardResponse findById(Long id);

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     */
/*    @Update("UPDATE boardTable " +
            "SET boardUpdatedTime = NOW(), " +
            "boardTitle = #{boardTitle}, " +
            "boardContents = #{boardContents}, " +
            "boardWriter = #{boardWriter} WHERE id = #{id}")*/
    void update(BoardRequest params);

    /**
     * 게시글 삭제
     * @param id - PK
     */
    /*@Delete("DELETE FROM boardTable WHERE id = #{id}")*/
    void deleteById(Long id);


    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    /*@Select("SELECT * FROM boardTable")*/
    List<BoardResponse> findAll();

    void updateHits(Long id);

}
