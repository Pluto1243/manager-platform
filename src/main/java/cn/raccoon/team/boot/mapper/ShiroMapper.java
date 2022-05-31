package cn.raccoon.team.boot.mapper;

import cn.raccoon.team.boot.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShiroMapper {

  /**
   * @description 根据用户名获得资源列表
   *
   * @author wangjie
   * @date 10:42 2022年05月31日
   * @param username
   * @return java.util.List<cn.raccoon.team.boot.entity.Permission>
   */
  List<Permission> getPermissionByUsername(String username);

  /**
   * @description 获得所有资源权限
   *
   * @author wangjie
   * @date 10:43 2022年05月31日
   * @param
   * @return java.util.List<cn.raccoon.team.boot.entity.Permission>
   */
  List<Permission> getAllResourcePermission();
}
