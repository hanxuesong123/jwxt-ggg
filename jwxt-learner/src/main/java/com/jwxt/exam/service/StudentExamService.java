package com.jwxt.exam.service;

import com.jwxt.entity.academic.Exam;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;

import java.util.Map;

public interface StudentExamService {

    Result getList(Map<String, Object> map) throws CommonException;

    Result getQuestionStudentExamList(Exam exam);

    Result getScoreStatus(String userId, String examId) throws CommonException;

    Result getExamStatus(String examId);

    Result saveExam(Map<String, Object> map);

    Result saveTempAnswer(Map<String, Object> map);

    Result echoTempAnswer(String examId, String userId);

    Result goBackStudentExamData(Exam exam,String userId);

    Result getAnswerQuestionStudentList(Exam exam);
}
