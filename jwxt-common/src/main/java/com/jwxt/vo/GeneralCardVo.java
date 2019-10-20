package com.jwxt.vo;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GeneralCardVo implements Serializable {

    private String id;
    private String title;
    private String icon;
    private int count;
    private String color;
}
