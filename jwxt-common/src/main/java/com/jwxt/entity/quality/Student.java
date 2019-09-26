package com.jwxt.entity.quality;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("sys_student")
public class Student implements Serializable {

    @TableId(value = "id",type = IdType.INPUT)
    private String id;

    @TableField(value = "study_type")
    private String studyType;


    @TableField(exist = false)
    private String classesName;

}

