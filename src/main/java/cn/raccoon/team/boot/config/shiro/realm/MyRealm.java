package cn.raccoon.team.boot.config.shiro.realm;

import cn.raccoon.team.boot.entity.Permission;
import cn.raccoon.team.boot.entity.User;
import cn.raccoon.team.boot.exception.CommonException;
import cn.raccoon.team.boot.exception.EmError;
import cn.raccoon.team.boot.mapper.IUserMapper;
import cn.raccoon.team.boot.mapper.ShiroMapper;
import cn.raccoon.team.boot.utils.JwtTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 *  Realm: 管理安全数据(用户、角色、权限) 保存信息让Shiro判断用户信息是否合法，进行验证
 *
 * @author wangjie
 * @date 10:06 2022年05月31日
 **/
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private ShiroMapper shiroMapper;

    /**
     * @description 授权
     *
     * @author wangjie
     * @date 10:13 2022年05月31日
     * @param principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获得登录的用户
        User user = (User)SecurityUtils.getSubject().getPrincipal();

        // 查询用户所拥有的的资源权限列表
        List<Permission> permissionByUsername = shiroMapper.getPermissionByUsername(user.getUsername());

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 权限列表授权
        permissionByUsername.stream().forEach(permission -> {
            simpleAuthorizationInfo.addStringPermission(permission.getPermission());
        });

        return simpleAuthorizationInfo;
    }

    /**
     * @description 认证
     *
     * @author wangjie
     * @date 10:12 2022年05月31日
     * @param authenticationToken
     * @return org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();

        // 没有 token 则为未登录状态
        if (!StringUtils.hasLength(token)) {
            throw new CommonException(EmError.PLEASE_TO_LOGIN);
        }

        // 解析token
        String username = jwtTokenUtil.getUsernameFromToken(token);

        // 获得用户信息
        User user = userMapper.getUserByAccount(username);

        if (user == null) {
            throw new CommonException(EmError.USER_NOT_FOUND);
        }

        // 构建AuthenticationInfo对象并返回
        return new SimpleAuthenticationInfo(user, token, this.getName());
    }
}
