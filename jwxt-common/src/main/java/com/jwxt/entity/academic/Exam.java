package com.jwxt.entity.academic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("st_exam")
public class Exam implements Serializable {

  @TableId(type = IdType.ID_WORKER_STR)
  private String id;

  @TableField("exam_name")
  private String examName;

  @TableField("class_id")
  private String classId;

  @TableField("class_name") //算出来的
  private String className;

  @TableField("person_number") //算出来的
  private Integer personNumber;

  @TableField("exam_time")
  private Date examTime;

  @TableField("exam_time_length")
  private Integer examTimeLength;

  @TableField("exam_type")
  private String examType;  //1日测 2周测 3月考

  @TableField("single_count")
  private Integer singleCount;

  @TableField("single_score")
  private Integer singleScore;

  @TableField("single_joins")
  private String singleJoins; //算出来的

  @TableField("mutiple_count")
  private Integer mutipleCount;

  @TableField("mutiple_score")
  private Integer mutipleScore;

  @TableField("mutiple_joins")
  private String mutipleJoins;//算出来的

  @TableField("ask_count")
  private Integer askCount;

  @TableField("ask_score")
  private Integer askScore;

  @TableField("ask_joins")
  private String askJoins;//算出来的

  @TableField("upper_count")
  private Integer upperCount;

  @TableField("upper_score")
  private Integer upperScore;

  @TableField("upper_joins")
  private String upperJoins;//算出来的

  @TableField("exam_status")
  private String examStatus;//后台填写

  @TableField("modify_time")
  private Date modifyTime;  //后台填写

  @TableField("modify_name")
  private String modifyName;//后台填写

  @TableField(exist = false)
  private String[] chapterIdsArray; //(传递)

  @TableField("chapter_ids")
  private String chapterIds;//章节id组 ,号拼接(存储)

  @TableField(exist = false)//(传递)
  private String [] questionTypeIdsArray;

  @TableField("question_type_ids")
  private String questionTypeIds;   //(存储)试题类型 以,号拼接  1单选题2多选题3问答题4上机题


}

