package com.jwxt.academic.service.impl;

import com.jwxt.academic.mapper.*;
import com.jwxt.academic.service.QuestionService;
import com.jwxt.entity.academic.*;
import com.jwxt.entity.job.Company;
import com.jwxt.entity.other.General;
import com.jwxt.exceptions.CommonException;
import com.jwxt.job.mapper.CompanyMapper;
import com.jwxt.other.mapper.GeneralMapper;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.utils.MyRedisTemplate;
import com.jwxt.utils.SftpUtil;
import com.jwxt.vo.QuestionCountVo;
import com.jwxt.vo.QuestionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

@Service
@Transactional
@SuppressWarnings("all")
public class QuestionServiceImpl implements QuestionService {

    @Value("${ftp.host}")
    private String FTP_HOST;
    @Value("${ftp.port}")
    private String FTP_PORT;
    @Value("${ftp.username}")
    private String FTP_USERNAME;
    @Value("${ftp.password}")
    private String FTP_PASSWORD;




    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private SingleMapper singleMapper;
    @Autowired
    private MutipleMapper mutipleMapper;
    @Autowired
    private AskMapper askMapper;
    @Autowired
    private UpperMapper upperMapper;

    @Autowired
    private MyRedisTemplate myRedisTemplate;


    @Override
    public Result list(Map<String, Object> map) {
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());

        long total = 0; //总记录数

        List<QuestionVo> list = null;  //查询的数据集合

        String type = map.get("type").toString();

        switch (type) {
            case "1":
                total = questionMapper.getSingleTotal(map);
                list = questionMapper.getSingleList((page - 1) * size, size,map);
                break;
            case "2":
                total = questionMapper.getMutipleTotal(map);
                list = questionMapper.getMutipleList((page - 1) * size, size,map);
                for (QuestionVo questionVo : list) {
                    String handleMutipleAsk = questionVo.getHandleMutipleAsk();  // 1,2,3  [1,2,3]
                    String[] split = handleMutipleAsk.split(",");
                    questionVo.setMutipleAsk(split);
                }
                break;
            case "3":
                total = questionMapper.getAskTotal(map);
                list = questionMapper.getAskList((page - 1) * size, size,map);
                break;
            case "4":
                total = questionMapper.getUpperTotal(map);
                list = questionMapper.getUpperList((page - 1) * size, size,map);
                break;
            default:
                list = new ArrayList<>();
        }

        //处理课程名称
        if (list != null && list.size() > 0) {
            for (QuestionVo questionVo : list) {

                if (questionVo != null && questionVo.getLessionId() != null) {
                    General general = generalMapper.selectById(questionVo.getLessionId());
                    questionVo.setLessionName(general.getName()); //课程名称
                }

                if (questionVo != null && questionVo.getCompanyId() != null) {
                    Company company = companyMapper.selectById(questionVo.getCompanyId());
                    if (company != null) {
                        questionVo.setCompanyName(company.getName());  //企业名称
                    }
                }
            }
        }

        PageResult<QuestionVo> pageResult = new PageResult<>(total, list);
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    @Override
    public Result upperList(Map<String, Object> map) {
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());

        List<QuestionVo> list = questionMapper.getUpperList((page - 1) * size, size,map);
        long upperTotal = questionMapper.getUpperTotal(map);

        PageResult<QuestionVo> pageResult = new PageResult<>(upperTotal, list);

        return new Result(ResultCode.SUCCESS, pageResult);
    }


    @Override
    public Result save(QuestionVo questionVo,String nickName) {

        General general = generalMapper.selectById(questionVo.getLessionId());

        Question question = Question.builder()
                .sourced(questionVo.getSourced())
                .discipline(questionVo.getDiscipline())
                .companyId(questionVo.getCompanyId() == null ? null : questionVo.getCompanyId())
                .lessionId(questionVo.getLessionId())
                .lessionName(general.getName())
                .type(questionVo.getType())
                .build();

        if (questionVo.getCompanyId() != null) {
            Company company = companyMapper.selectById(questionVo.getCompanyId());
            question.setCompanyName(company.getName());
        }

        questionMapper.insert(question);



        String type = questionVo.getType();

        String id = question.getId();
        switch (type) {
            case "1":
                Single single = Single.builder()
                        .id(id)
                        .singleContent(questionVo.getSingleContent())
                        .singleOptionA(questionVo.getSingleOptionA())
                        .singleOptionB(questionVo.getSingleOptionB())
                        .singleOptionC(questionVo.getSingleOptionC())
                        .singleOptionD(questionVo.getSingleOptionD())
                        .singleAsk(questionVo.getSingleAsk())
                        .singleStatus("1")
                        .build();
                single.setModifyName(nickName);
                single.setModifyTime(new Date());
                single.setSingleStatus("1");
                int insert = singleMapper.insert(single);

                if(insert == 1){

                    QuestionVo vo = new QuestionVo();

                    BeanUtils.copyProperties(question,vo);
                    BeanUtils.copyProperties(single,vo);

                    myRedisTemplate.hSetObject("single",question.getId(),vo);
                }
                break;
            case "2":
                String mutipleAsk = StringUtils.join(questionVo.getMutipleAsk(), ",");
                Mutiple mutiple = Mutiple.builder()
                        .id(id)
                        .mutipleContent(questionVo.getMutipleContent())
                        .mutipleOptionA(questionVo.getMutipleOptionA())
                        .mutipleOptionB(questionVo.getMutipleOptionB())
                        .mutipleOptionC(questionVo.getMutipleOptionC())
                        .mutipleOptionD(questionVo.getMutipleOptionD())
                        .mutipleAsk(mutipleAsk)
                        .mutipleStatus("1")
                        .modifyName(nickName)
                        .modifyTime(new Date())
                        .build();
                int insert1 = mutipleMapper.insert(mutiple);
                if(insert1 == 1){

                    QuestionVo vo = new QuestionVo();

                    BeanUtils.copyProperties(question,vo);
                    BeanUtils.copyProperties(mutiple,vo);

                    myRedisTemplate.hSetObject("mutiple",question.getId(),vo);
                }
                break;
            case "3":
                Ask ask = Ask.builder()
                        .id(id)
                        .askContent(questionVo.getAskContent())
                        .askStatus("1")
                        .modifyName(nickName)
                        .modifyTime(new Date())
                        .build();
                int insert2 = askMapper.insert(ask);

                if(insert2 == 1){
                    QuestionVo vo = new QuestionVo();
                    BeanUtils.copyProperties(question,vo);
                    BeanUtils.copyProperties(ask,vo);
                    myRedisTemplate.hSetObject("ask",question.getId(),vo);
                }
                break;
            case "4":
                Upper upper = upperMapper.selectById(questionVo.getId());
                upper.setModifyName(nickName);
                upper.setModifyTime(new Date());
                upper.setUpperContent(questionVo.getUpperContent());
                int insert3 = upperMapper.updateById(upper);

                if(insert3 == 1){
                    QuestionVo vo = new QuestionVo();
                    BeanUtils.copyProperties(question,vo);
                    BeanUtils.copyProperties(upper,vo);
                    myRedisTemplate.hSetObject("upper",question.getId(),vo);
                }
                break;
            default:
                return Result.FAIL();
        }
        return Result.SUCCESS();
    }


    @Override
    public Result update(QuestionVo questionVo,String nickName) {

        if (questionVo.getType().equals("1")) {
            Single single = singleMapper.selectById(questionVo.getId());
            single.setModifyTime(new Date());
            single.setModifyName(nickName);
            single.setSingleAsk(questionVo.getSingleAsk());
            single.setSingleContent(questionVo.getSingleContent());
            single.setSingleOptionA(questionVo.getSingleOptionA());
            single.setSingleOptionB(questionVo.getSingleOptionB());
            single.setSingleOptionC(questionVo.getSingleOptionC());
            single.setSingleOptionD(questionVo.getSingleOptionD());

            singleMapper.updateById(single);

        } else if (questionVo.getType().equals("2")) {
            String mutipleAsk = StringUtils.join(questionVo.getMutipleAsk(), ",");
            Mutiple mutiple = mutipleMapper.selectById(questionVo.getId());
            mutiple.setModifyName(nickName);
            mutiple.setModifyTime(new Date());
            mutiple.setMutipleAsk(mutipleAsk);// 1,3,4    [1,3,4]
            mutiple.setMutipleContent(questionVo.getMutipleContent());
            mutiple.setMutipleOptionA(questionVo.getMutipleOptionA());
            mutiple.setMutipleOptionB(questionVo.getMutipleOptionB());
            mutiple.setMutipleOptionC(questionVo.getMutipleOptionC());
            mutiple.setMutipleOptionD(questionVo.getMutipleOptionD());

            mutipleMapper.updateById(mutiple);

        } else if (questionVo.getType().equals("3")) {

            Ask ask = askMapper.selectById(questionVo.getId());
            ask.setAskContent(questionVo.getAskContent());
            ask.setModifyName(nickName);
            ask.setModifyTime(new Date());

            askMapper.updateById(ask);

        } else if (questionVo.getType().equals("4")) {
            Upper upper = upperMapper.selectById(questionVo.getId());
            upper.setUpperContent(questionVo.getUpperContent());
            upper.setModifyName(nickName);
            upper.setModifyTime(new Date());
            upperMapper.updateById(upper);
        }


        Question question = questionMapper.selectById(questionVo.getId());

        General general = generalMapper.selectById(questionVo.getLessionId());

        question.setLessionName(general.getName());
        question.setCompanyId(questionVo.getCompanyId());

        if (questionVo.getCompanyId() != null) {
            Company company = companyMapper.selectById(questionVo.getCompanyId());
            question.setCompanyName(company.getName());
        }


        question.setDiscipline(questionVo.getDiscipline());
        question.setLessionId(questionVo.getLessionId());
        question.setSourced(questionVo.getSourced());
        question.setType(questionVo.getType());
        questionMapper.updateById(question);


        return Result.SUCCESS();
    }


    @Override
    public Result upload(String originalFilename, InputStream inputStream,String nickName) {
        //dsd233dasdsad123312312.zip
        String newFileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        newFileName = newFileName.replace("-", "");

        SftpUtil sftpUtil = new SftpUtil(FTP_HOST, Integer.parseInt(FTP_PORT), FTP_USERNAME, FTP_PASSWORD);

        sftpUtil.upload("/var/ftp/pub", newFileName, inputStream);

        String url = "ftp://".concat(FTP_HOST).concat(":21/pub/").concat(newFileName);


        Upper upper = Upper.builder()
                .upperStatus("1")
                .upperUrl(url)
                .modifyName(nickName)
                .modifyTime(new Date())
                .build();

        upperMapper.insert(upper);


        //sftpUtil.delete("/var/ftp/pub",newFileName);
        //System.out.println("已删除");

        Question question = Question.builder().id(upper.getId()).type("4").build();

        questionMapper.insert(question);
        return new Result(ResultCode.SUCCESS, upper);
    }


    @Override
    public Result opt(String id) {
        Question question = questionMapper.selectById(id);
        String type = question.getType();
        switch (type) {
            case "1":
                Single single = singleMapper.selectById(id);
                String status1 = single.getSingleStatus();
                if ("1".equals(status1)) {
                    single.setSingleStatus("2");
                } else {
                    single.setSingleStatus("1");
                }
                singleMapper.updateById(single);
                break;
            case "2":
                Mutiple mutiple = mutipleMapper.selectById(id);
                String status2 = mutiple.getMutipleStatus();
                if (status2.equals("1")) {
                    mutiple.setMutipleStatus("2");
                } else {
                    mutiple.setMutipleStatus("1");
                }
                mutipleMapper.updateById(mutiple);
                break;
            case "3":
                Ask ask = askMapper.selectById(id);
                String status3 = ask.getAskStatus();
                if (status3.equals("1")) {
                    ask.setAskStatus("2");
                } else {
                    ask.setAskStatus("1");
                }
                askMapper.updateById(ask);
                break;
            case "4":
                Upper upper = upperMapper.selectById(id);
                String status4 = upper.getUpperStatus();
                if (status4.equals("1")) {
                    upper.setUpperStatus("2");
                } else {
                    upper.setUpperStatus("1");
                }
                upperMapper.updateById(upper);
                break;
        }

        return Result.SUCCESS();
    }


    @Override
    public Result count(String ids) throws CommonException {
        if (ids != null) {
            String[] chapterIdsArray = ids.split(",");
            if (chapterIdsArray.length > 0) {

                Integer singleCount = 0;
                Integer mutipleCount = 0;
                Integer askCount = 0;
                Integer upperCount = 0;

                for (String chapterId : chapterIdsArray) {
                    //查询每一个章节的试题数量
                    singleCount += questionMapper.count(chapterId, "1");
                    mutipleCount += questionMapper.count(chapterId, "2");
                    askCount += questionMapper.count(chapterId, "3");
                    upperCount += questionMapper.count(chapterId,"4");
                }

                QuestionCountVo result = new QuestionCountVo();
                result.setAskCount(askCount);
                result.setMutipleCount(mutipleCount);
                result.setSingleCount(singleCount);
                result.setUpperCount(upperCount);

                return new Result(ResultCode.SUCCESS, result);
            } else {
                throw new CommonException(ResultCode.FAIL);
            }
        } else {
            throw new CommonException(ResultCode.FAIL);
        }

    }

}
