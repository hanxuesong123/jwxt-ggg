package com.jwxt.vo;

import com.jwxt.entity.academic.Ask;
import com.jwxt.entity.academic.Upper;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpperVoResult implements Serializable {

    private List<Upper> uppers;

    private List<UpperVo> list;
}
