package cn.raccoon.team.boot.mapper;

import cn.raccoon.team.boot.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShiroMapper {

    /**
     * @param username
     * @return java.util.List<cn.raccoon.team.boot.entity.Permission>
     * @description 根据用户名获得资源列表
     * @author wangjie
     * @date 10:42 2022年05月31日
     */
    List<Permission> getPermissionByUsername(String username);

    /**
     * @param
     * @return java.util.List<cn.raccoon.team.boot.entity.Permission>
     * @description 获得所有资源权限
     * @author wangjie
     * @date 10:43 2022年05月31日
     */
    List<Permission> getAllResourcePermission();

    /**
     * @param userId
     * @param roleId
     * @return java.lang.Boolean
     * @description 给用户添加角色
     * @author wangjie
     * @date 10:44 2022年06月10日
     */
    Boolean setUserWithRole(@Param("userId") Integer userId,
                            @Param("roleId") Integer roleId);
}
