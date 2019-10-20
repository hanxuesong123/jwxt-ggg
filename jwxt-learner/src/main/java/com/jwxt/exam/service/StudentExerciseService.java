package com.jwxt.exam.service;

import com.jwxt.entity.academic.ErrorQuestion;
import com.jwxt.response.Result;

import java.util.Map;

public interface StudentExerciseService {
    Result getStages();

    Result getChaptersCount(String stageId,String studentId);

    Result getRandomQuestionByChapter(Map<String, Object> map);

    Result saveQuestionToCache(Map<String, Object> map,String studentId);

    Result saveErrorQuestion(ErrorQuestion errorQuestion,String studentId,String nickName);
}
