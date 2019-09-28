package com.jwxt.send.controller;

import com.jwxt.send.service.SendService;
import com.jwxt.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//发送服务的公共controller
@RestController
public class SendServiceController {

    @Autowired
    private SendService sendService;

    @RequestMapping(value = "/send/question/getSingleVOByIdAndType",method = RequestMethod.POST,name = "PROFILE")
    public QuestionVo getSingleVOByIdAndType(@RequestParam("id")String id,@RequestParam("type") String type){
        return sendService.getSingleVOByIdAndType(id,type);
    }



    @RequestMapping(value = "/send/question/getMutipleVOByIdAndType",method = RequestMethod.POST,name = "PROFILE")
    public QuestionVo getMutipleVOByIdAndType(@RequestParam("id")String id,@RequestParam("type") String type){
        return sendService.getMutipleVOByIdAndType(id,type);
    }

    @RequestMapping(value = "/send/question/getAskVOByIdAndType",method = RequestMethod.POST,name = "PROFILE")
    public QuestionVo getAskVOByIdAndType(@RequestParam("id")String id,@RequestParam("type") String type){
        return sendService.getAskVOByIdAndType(id,type);
    }
}
