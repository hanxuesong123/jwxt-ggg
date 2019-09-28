package com.jwxt.send.service;

import com.jwxt.vo.QuestionVo;

public interface SendService {

    QuestionVo getSingleVOByIdAndType(String id, String type);

    QuestionVo getMutipleVOByIdAndType(String id, String type);

    QuestionVo getAskVOByIdAndType(String id, String type);
}
