package com.jwxt.response;

import com.jwxt.entity.permission.Permission;
import com.jwxt.entity.permission.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
@SuppressWarnings("all")
public class ProfileResult  implements Serializable {

  private String username;
  private String nickName;
  private Map<String,Object> roles = new HashMap<>(); //存入到该map中的数据是: 三个集合

  public ProfileResult(User user, List<Permission> list) { //一个是当前user对象,一个是当前用户所拥有的所有权限
    this.nickName = user.getNickName();
    this.username = user.getUsername();

    Set<String> menus = new HashSet<>();
    Set<String> points = new HashSet<>();
    Set<String> apis = new HashSet<>(); //访问后台的数据编码CODE

    if(list != null && list.size() > 0){
      for (Permission perm : list) {
        String code = perm.getCode();
        Integer type = perm.getType();
        /*if("1".equals()) {
          menus.add(code);
        }else if("2".equals(perm.getType())) {
          points.add(code);
        }else {
          apis.add(code);
        }*/

        if(type == 1){
          menus.add(code);
        }else if(type == 2){
          points.add(code);
        }else if(type == 3){
          apis.add(code);
        }

      }
    }

    this.roles.put("menus",menus);
    this.roles.put("points",points);
    this.roles.put("apis",apis);

  }
}
