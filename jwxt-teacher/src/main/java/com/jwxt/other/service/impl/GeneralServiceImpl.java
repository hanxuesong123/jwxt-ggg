package com.jwxt.other.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwxt.base.BaseService;
import com.jwxt.entity.other.General;
import com.jwxt.other.mapper.GeneralMapper;
import com.jwxt.other.service.GeneralService;
import com.jwxt.response.PageResult;
import com.jwxt.response.Result;
import com.jwxt.response.ResultCode;
import com.jwxt.response.TreeResult;
import com.jwxt.vo.GeneralVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GeneralServiceImpl extends BaseService<General> implements GeneralService {

    @Autowired
    private GeneralMapper generalMapper;

    @Override
    public Result findGeneralByCode(Map<String, Object> map) {
        QueryWrapper<General> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("code",map.get("code").toString());
        queryWrapper.eq("status","1");
        List<General> generals = generalMapper.selectList(queryWrapper);
        return new Result(ResultCode.SUCCESS,generals);
    }

    @Override
    public Result list(Map<String, Object> map) {


        IPage<General> iPage = super.iPage(map);

        QueryWrapper<General> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(map.get("name"))){
            queryWrapper.like("name",map.get("name").toString());
        }

        if(!StringUtils.isEmpty(map.get("code"))){
            queryWrapper.like("code",map.get("code").toString());
        }

        if(!StringUtils.isEmpty(map.get("status"))){
            queryWrapper.eq("status",map.get("status").toString());
        }


        IPage<General> result = generalMapper.selectPage(iPage, queryWrapper);

        PageResult<General> pageResult = new PageResult<>(result.getTotal(),result.getRecords());

        return new Result(ResultCode.SUCCESS,pageResult);
    }


    @Override
    public Result opt(General general) {
        String status = general.getStatus();
        if(status.equals("1")) general.setStatus("2");
        else if(status.equals("2")) general.setStatus("1");
        general.setModifyTime(new Date());
        general.setModifyName("张三");
        generalMapper.updateById(general);
        return Result.SUCCESS();
    }


    @Override
    public Result save(General general){
        general.setModifyName("张三");
        general.setModifyTime(new Date());
        general.setStatus("1");
        generalMapper.insert(general);

        return Result.SUCCESS();
    }


    @Override
    public Result update(General general){
        General target = generalMapper.selectById(general.getId());
        BeanUtils.copyProperties(general,target);
        generalMapper.updateById(target);
        return Result.SUCCESS();
    }


    @Override
    public Result findChapterByCodeAndStage(Map<String,Object> map){

        QueryWrapper<General> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("code",map.get("code").toString());
        queryWrapper.eq("pid",map.get("stageId").toString());

        List<General> generals = generalMapper.selectList(queryWrapper);

        List<TreeResult> children = new ArrayList<>();

        if(generals != null && generals.size() > 0){
            for (General chapter : generals) {
                TreeResult treeResult = TreeResult.builder()
                        .id(chapter.getId())
                        .label(chapter.getName())
                        .expand(false)
                        .pid(map.get("stageId").toString())
                        .children(null)
                        .description(chapter.getDescription())
                        .code(chapter.getCode())
                        .build();
                children.add(treeResult);
            }
        }
        General stage = generalMapper.selectById(map.get("stageId").toString());

        TreeResult treeResult = TreeResult.builder()
                .id(map.get("stageId").toString())
                .label(stage.getName())
                .expand(false)
                .pid(null)
                .code(stage.getCode())
                .description(stage.getDescription())
                .children(children)
                .build();

        List<TreeResult> parent = new ArrayList<>();

        parent.add(treeResult);

        return new Result(ResultCode.SUCCESS,parent);
    }


    @Override
    public Result findGenerals(){
        List<GeneralVo> list = new ArrayList<>();
        QueryWrapper<General> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code","STAGE");

        List<General> stages = generalMapper.selectList(queryWrapper);

        for (General stage : stages) {
            QueryWrapper<General> query = new QueryWrapper<>();
            String pid = stage.getId();
            query.eq("code","CHAPTER");
            query.eq("pid",pid);
            List<General> chapter = generalMapper.selectList(query);
            GeneralVo vo = new GeneralVo();
            vo.setGeneral(stage);
            vo.setChildren(chapter);

            list.add(vo);
        }


        return new Result(ResultCode.SUCCESS,list);
    }

}
