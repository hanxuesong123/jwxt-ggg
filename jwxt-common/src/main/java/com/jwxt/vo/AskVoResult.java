package com.jwxt.vo;

import com.jwxt.entity.academic.Ask;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AskVoResult implements Serializable {

    private List<Ask> asks;

    private List<AskVo> list;
}
