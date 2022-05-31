package cn.raccoon.team.boot.entity;

import lombok.Data;

/**
 * 权限路径
 *
 * @author wangjie
 * @date 14:12 2021年10月12日
 **/
@Data
public class Permission {
    private Integer id;

    /**
    * 权限名称
    */
    private String permissionName;

    /**
    * 权限url
    */
    private String permission;
}