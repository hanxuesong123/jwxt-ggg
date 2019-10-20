package com.jwxt.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwxt.entity.academic.Question;
import com.jwxt.vo.QuestionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("select count(0) from st_question where lession_id = #{lessionId} ")
    int getQuestionCountByLessionId(@Param("lessionId") String lessionId);

    @Select("SELECT q.*,s.* " +
            "FROM st_question q, st_single s " +
            "WHERE q.`lession_id` = #{lessionId} AND q.`type` = '1' " +
            "AND q.`id` = s.`id`")
    List<QuestionVo> getSingleListByLessionIdAndType(@Param("lessionId") String lessionId);

    @Select("SELECT q.*,m.*,m.mutiple_ask as handleMutipleAsk " +
            "FROM st_question q, st_mutiple m " +
            "WHERE q.`lession_id` = #{lessionId} AND q.`type` = '2' " +
            "AND q.`id` = m.`id`")
    List<QuestionVo> getMutipleListByLessionIdAndType(@Param("lessionId") String lessionId);

    @Select("SELECT q.*,a.* " +
            "FROM st_question q, st_ask a " +
            "WHERE q.`lession_id` = #{lessionId} AND q.`type` = '3' " +
            "AND q.`id` = a.`id`")
    List<QuestionVo> getAskListByLessionIdAndType(@Param("lessionId") String lessionId);


    @Select("SELECT q.*,u.* " +
            "FROM st_question q, st_upper u " +
            "WHERE q.`lession_id` = #{lessionId} AND q.`type` = '4' " +
            "AND q.`id` = u.`id`")
    List<QuestionVo> getUpperListByLessionIdAndType(@Param("lessionId") String lessionId);
}
