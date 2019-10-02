package com.jwxt.entity.academic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("st_exam")
@ApiModel(value = "com.jwxt.entity.academic.Exam",description = "试卷实体类")
public class Exam implements Serializable {

  @TableId(type = IdType.ID_WORKER_STR)
  @ApiModelProperty(value = "ID")
  private String id;

  @TableField("exam_name")
  @ApiModelProperty(value = "试卷名称")
  private String examName;

  @TableField("class_id")
  @ApiModelProperty(value = "班级ID")
  private String classId;

  @TableField("class_name") //算出来的
  @ApiModelProperty(value = "班级名称")
  private String className;

  @TableField("person_number") //算出来的
  @ApiModelProperty(value = "班级人数")
  private Integer personNumber;

  @TableField("exam_time")
  @ApiModelProperty(value = "开考时间")
  private Date examTime;

  @TableField("exam_time_length")
  @ApiModelProperty(value = "考试时长")
  private Integer examTimeLength;

  @TableField("exam_type")
  @ApiModelProperty(value = "试卷类型 1日测 2周测 3月考")
  private String examType;  //1日测 2周测 3月考

  @TableField("single_count")
  @ApiModelProperty(value = "单选题数量")
  private Integer singleCount;

  @TableField("single_score")
  @ApiModelProperty(value = "单选题每题分数")
  private Integer singleScore;

  @TableField("single_joins")
  @ApiModelProperty(value = "单选题ID组")
  private String singleJoins; //算出来的

  @TableField("mutiple_count")
  @ApiModelProperty(value = "多选题数量")
  private Integer mutipleCount;

  @TableField("mutiple_score")
  @ApiModelProperty(value = "多选题每题分数")
  private Integer mutipleScore;

  @TableField("mutiple_joins")
  @ApiModelProperty(value = "多选题ID组")
  private String mutipleJoins;//算出来的

  @TableField("ask_count")
  @ApiModelProperty(value = "问答题数量")
  private Integer askCount;

  @TableField("ask_score")
  @ApiModelProperty(value = "问答题每题分数")
  private Integer askScore;

  @TableField("ask_joins")
  @ApiModelProperty(value = "问答题ID组")
  private String askJoins;//算出来的

  @TableField("upper_count")
  @ApiModelProperty(value = "上机题数量")
  private Integer upperCount;

  @TableField("upper_score")
  @ApiModelProperty(value = "上机题每题分数")
  private Integer upperScore;

  @TableField("upper_joins")
  @ApiModelProperty(value = "上机题ID组")
  private String upperJoins;//算出来的

  @TableField("exam_status")
  @ApiModelProperty(value = "试卷状态 1未开始 2进行中 3批阅中 4已结束")
  private String examStatus;//后台填写

  @TableField("modify_time")
  @ApiModelProperty(value = "操作时间")
  private Date modifyTime;  //后台填写

  @TableField("modify_name")
  @ApiModelProperty(value = "操作人")
  private String modifyName;//后台填写

  @TableField(exist = false)
  @ApiModelProperty(value = "章节ID组(传递)")
  private String[] chapterIdsArray; //(传递)

  @TableField("chapter_ids")
  @ApiModelProperty(value = "章节ID组(存储)")
  private String chapterIds;//章节id组 ,号拼接(存储)

  @TableField(exist = false)//(传递)
  @ApiModelProperty(value = "试卷题型id组(传递)")
  private String [] questionTypeIdsArray;

  @TableField("question_type_ids")
  @ApiModelProperty(value = "试卷类型id组(存储)")
  private String questionTypeIds;   //(存储)试题类型 以,号拼接  1单选题2多选题3问答题4上机题


}

