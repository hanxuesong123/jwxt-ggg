package com.jwxt.vo;

import com.jwxt.entity.academic.AskResult;
import com.jwxt.entity.academic.MutipleResult;
import com.jwxt.entity.academic.SingleResult;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class GoBackVo implements Serializable {

    private List<AskResult> askResults;
    private List<SingleResult> singleResults;
    private List<MutipleResult> mutipleResults;

}
