package com.jwxt.academic.service;

import com.jwxt.entity.academic.Exam;
import com.jwxt.entity.academic.Score;
import com.jwxt.exceptions.CommonException;
import com.jwxt.response.Result;

import java.util.Map;

public interface ExamService {
    Result list(Map<String, Object> map);

    Result save(Exam exam,String nickName)throws CommonException;

    Result startExam(String id,String nickName) throws CommonException;

    Result showExam(Exam exam);

    Result stopExam(Exam exam, String nickName);

    Result readStudentAsks(String id);

    Result readStudentUppers(Exam exam);

    Result lastExam(Score score);

    Result endExam(Exam exam,String nickName);

    Result analysisExam(Exam exam);

    /*Result showObjectExam(Exam exam);*/

    Result getQuestionExamList(Exam exam);


}
