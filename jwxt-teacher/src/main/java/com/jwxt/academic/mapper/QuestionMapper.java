package com.jwxt.academic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.Question;
import com.jwxt.vo.QuestionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {


    @Select("<script> " +
            "SELECT sq.*,ss.* " +
            "FROM st_question sq,st_single ss " +
            "WHERE sq.`id` = ss.`id` " +
            "AND sq.id = #{singleId} " +
            "AND sq.type = #{type} " +
            "ORDER BY ss.`modify_time` " +
            "DESC " +
            " </script>")
    QuestionVo getSingleVOByIdAndType(@Param("singleId")String singleId,@Param("type") String type);

    @Select("<script> " +
            "SELECT sq.*,sm.*,sm.mutiple_ask as handleMutipleAsk " +
            "FROM st_question sq,st_mutiple sm " +
            "WHERE sq.`id` = sm.`id` " +
            "AND sq.id = #{mutipleId} " +
            "AND sq.type = #{type} " +
            "ORDER BY sm.`modify_time` " +
            "DESC" +
            " </script>")
    QuestionVo getMutipleVOByIdAndType(@Param("mutipleId")String mutipleId,@Param("type") String type);

    @Select("<script> " +
            "SELECT sq.*,sa.* " +
            "FROM st_question sq,st_ask sa " +
            "WHERE sq.`id` = sa.`id` " +
            "AND sq.id = #{askId} " +
            "AND sq.type = #{type} " +
            "ORDER BY sa.`modify_time` " +
            "DESC " +
            " </script>")
    QuestionVo getAskVOByIdAndType(@Param("askId")String askId,@Param("type") String type);

    @Select("select count(0) from st_question where lession_id = #{chapterId} and type = #{type}")
    Integer count(@Param("chapterId")String chapterId,@Param("type") String type);


    @Select("<script> " +
            "SELECT q.* " +
            "FROM st_question q " +
            "WHERE q.`lession_id` = #{chapterId} and q.`type` = #{type} " +
            " </script>")
    List<Question> getQuestionListByLessionIdAndType(@Param("chapterId")String chapterId,@Param("type") String type);

    @Select("<script> " +
            " SELECT sq.*,ss.* " +
            " FROM st_question sq,st_single ss " +
            " WHERE sq.`id`=ss.`id`  " +
            " <if test='map.content  != null'> and ss.single_content like CONCAT('%',#{map.content},'%') </if> " +
            " <if test='map.lessionId != null'> and sq.lession_id = #{map.lessionId} </if> " +
            " <if test='map.discipline != null'> and sq.discipline = #{map.discipline} </if> " +
            " <if test='map.sourced != null'> and sq.sourced = #{map.sourced}</if> " +
            " <if test='map.companyId != null'> and sq.company_id = #{map.companyId} </if> " +
            " ORDER BY ss.`modify_time`  " +
            " DESC " +
            " LIMIT #{page},#{size} " +
            " </script>")
    List<QuestionVo> getSingleList(@Param("page")Integer page,@Param("size") Integer size,@Param("map") Map<String,Object> map);



    @Select("<script> " +
            "select count(0) " +
            "FROM st_question sq,st_single ss " +
            "WHERE sq.`id`=ss.`id` " +
            " <if test='map.content  != null'> and ss.single_content like CONCAT('%',#{map.content},'%') </if> " +
            " <if test='map.lessionId != null'> and sq.lession_id = #{map.lessionId} </if> " +
            " <if test='map.discipline != null'> and sq.discipline = #{map.discipline} </if> " +
            " <if test='map.sourced != null'> and sq.sourced = #{map.sourced}</if> " +
            " <if test='map.companyId != null'> and sq.company_id = #{map.companyId} </if> " +
            " </script>")
    long getSingleTotal(@Param("map") Map<String,Object> map);

    @Select("<script> " +
            "SELECT sq.*,sm.*, sm.mutiple_ask as handleMutipleAsk " +
            "FROM st_question sq,st_mutiple sm " +
            "WHERE sq.`id` = sm.`id` " +
            " <if test='map.content  != null'> and sm.mutiple_content like CONCAT('%',#{map.content},'%') </if> " +
            " <if test='map.lessionId != null'> and sq.lession_id = #{map.lessionId} </if> " +
            " <if test='map.discipline != null'> and sq.discipline = #{map.discipline} </if> " +
            " <if test='map.sourced != null'> and sq.sourced = #{map.sourced}</if> " +
            " <if test='map.companyId != null'> and sq.company_id = #{map.companyId} </if> " +
            "ORDER BY sm.`modify_time` " +
            "DESC " +
            "LIMIT #{page},#{size}" +
            " </script>")
    List<QuestionVo> getMutipleList(@Param("page")Integer page,@Param("size") Integer size,@Param("map") Map<String,Object> map);

    @Select("<script> " +
            "SELECT COUNT(0) " +
            "FROM st_question sq,st_mutiple sm " +
            "WHERE sq.`id` = sm.`id`" +
            " <if test='map.content  != null'> and sm.mutiple_content like CONCAT('%',#{map.content},'%') </if> " +
            " <if test='map.lessionId != null'> and sq.lession_id = #{map.lessionId} </if> " +
            " <if test='map.discipline != null'> and sq.discipline = #{map.discipline} </if> " +
            " <if test='map.sourced != null'> and sq.sourced = #{map.sourced}</if> " +
            " <if test='map.companyId != null'> and sq.company_id = #{map.companyId} </if> " +
            " </script>")
    long getMutipleTotal(@Param("map") Map<String,Object> map);


    @Select("<script> " +
            "SELECT sq.*,sa.* " +
            "FROM st_question sq,st_ask sa " +
            "WHERE sq.`id` = sa.`id` " +
            " <if test='map.content  != null'> and sa.ask_content like CONCAT('%',#{map.content},'%') </if> " +
            " <if test='map.lessionId != null'> and sq.lession_id = #{map.lessionId} </if> " +
            " <if test='map.discipline != null'> and sq.discipline = #{map.discipline} </if> " +
            " <if test='map.sourced != null'> and sq.sourced = #{map.sourced}</if> " +
            " <if test='map.companyId != null'> and sq.company_id = #{map.companyId} </if> " +
            "ORDER BY sa.`modify_time` " +
            "DESC " +
            "LIMIT #{page},#{size}" +
            " </script>")
    List<QuestionVo> getAskList(@Param("page")Integer page, @Param("size")Integer size,@Param("map") Map<String,Object> map);

    @Select("<script> " +
            "SELECT COUNT(0) " +
            "FROM st_question sq,st_ask sa " +
            "WHERE sq.`id` = sa.`id`" +
            " <if test='map.content  != null'> and sa.ask_content like CONCAT('%',#{map.content},'%') </if> " +
            " <if test='map.lessionId != null'> and sq.lession_id = #{map.lessionId} </if> " +
            " <if test='map.discipline != null'> and sq.discipline = #{map.discipline} </if> " +
            " <if test='map.sourced != null'> and sq.sourced = #{map.sourced}</if> " +
            " <if test='map.companyId != null'> and sq.company_id = #{map.companyId} </if> " +
            " </script>")
    long getAskTotal(@Param("map") Map<String,Object> map);




    @Select("<script> " +
            "SELECT sq.*,su.* " +
            "FROM st_question sq,st_upper su " +
            "WHERE sq.`id` = su.`id` " +
            " <if test='map.content  != null'> and su.upper_content like CONCAT('%',#{map.content},'%') </if> " +
            " <if test='map.lessionId != null'> and sq.lession_id = #{map.lessionId} </if> " +
            " <if test='map.discipline != null'> and sq.discipline = #{map.discipline} </if> " +
            " <if test='map.sourced != null'> and sq.sourced = #{map.sourced}</if> " +
            " <if test='map.companyId != null'> and sq.company_id = #{map.companyId} </if> " +
            "ORDER BY su.`modify_time` " +
            "DESC " +
            "LIMIT #{page},#{size}" +
            " </script>")
    List<QuestionVo> getUpperList(@Param("page")Integer page, @Param("size")Integer size,@Param("map") Map<String,Object> map);

    @Select("<script> " +
            "SELECT COUNT(0) " +
            "FROM st_question sq,st_upper su " +
            "WHERE sq.`id` = su.`id`" +
            " <if test='map.content  != null'> and su.upper_content like CONCAT('%',#{map.content},'%') </if> " +
            " <if test='map.lessionId != null'> and sq.lession_id = #{map.lessionId} </if> " +
            " <if test='map.discipline != null'> and sq.discipline = #{map.discipline} </if> " +
            " <if test='map.sourced != null'> and sq.sourced = #{map.sourced}</if> " +
            " <if test='map.companyId != null'> and sq.company_id = #{map.companyId} </if> " +
            " </script>")
    long getUpperTotal(@Param("map") Map<String,Object> map);

    @Select("<script> " +
            "SELECT sq.*,su.* " +
            "FROM st_question sq,st_upper su " +
            "WHERE sq.`id` = su.`id` " +
            "<if test='lessionIdArray != null'>" +
            " and sq.lession_id in (#{lessionIdArray})" +
            "</if>" +
            "ORDER BY su.`modify_time` " +
            "DESC " +
            "LIMIT #{page},#{size}" +
            " </script>")
    List<QuestionVo> getQuestionsByLessionIdsOfUpperList(@Param("page")Integer page,@Param("size") Integer size,@Param("lessionIdArray") String[] lessionIdArray);
}

