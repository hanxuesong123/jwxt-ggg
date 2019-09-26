package com.jwxt.vo;

import com.jwxt.entity.other.General;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralVo implements Serializable {

  private General general;

  private List<General> children;
}
