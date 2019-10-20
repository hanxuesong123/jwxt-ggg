package com.jwxt.vo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChapterVo implements Serializable {

    private String id;
    private String name;
    private int totalCount;
    private int okCount;
}
