package com.jwxt.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreeResult  implements Serializable {

  private String id;
  private String label;
  private String code;
  private String description;
  private Boolean expand;
  private String pid;
  private List<TreeResult> children;
}
