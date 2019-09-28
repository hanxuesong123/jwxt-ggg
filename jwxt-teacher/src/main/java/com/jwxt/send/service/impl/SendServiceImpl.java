package com.jwxt.send.service.impl;

import com.jwxt.academic.mapper.QuestionMapper;
import com.jwxt.send.service.SendService;
import com.jwxt.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SendServiceImpl implements SendService {

    @Autowired
    private QuestionMapper questionMapper;


    @Override
    public QuestionVo getSingleVOByIdAndType(String id, String type) {
        return questionMapper.getSingleVOByIdAndType(id,type);
    }

    @Override
    public QuestionVo getMutipleVOByIdAndType(String id, String type) {
        return questionMapper.getMutipleVOByIdAndType(id,type);
    }

    @Override
    public QuestionVo getAskVOByIdAndType(String id, String type) {
        return questionMapper.getAskVOByIdAndType(id,type);
    }
}
