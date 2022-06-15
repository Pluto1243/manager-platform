package cn.raccoon.team.boot.service;

import cn.raccoon.team.boot.entity.RegisterInfo;
import cn.raccoon.team.boot.entity.UpdateUserInfo;
import cn.raccoon.team.boot.entity.User;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author Qian
 * @Date 2021/12/20 16:32
 */
public interface IUserService {
    /**
     * 获取用户
     *
     * @param account
     * @return
     */
    User getUserByAccount(String account);

    /**
     * 用户登录
     *
     * @param account
     * @param passwd
     * @return
     */
    User userLogin(String account, String passwd);


    /**
     * 生成密钥
     *
     * @param password
     * @return
     */
    Boolean changePassword(String password, HttpServletResponse response);
    
    /**
     * @description 注册
     *
     * @author wangjie
     * @date 10:22 2022年06月10日 
     * @param registerInfo
     * @return java.lang.Boolean 
     */
    Boolean register(RegisterInfo registerInfo);
    
    /**
     * @description 校验用户名是否重复
     *
     * @author wangjie
     * @date 16:44 2022年06月10日
     * @param username
     * @return java.lang.Boolean 
     */
    Boolean checkUserName(String username);

    /**
     * @description 更新用户信息
     *
     * @author wangjie
     * @date 11:38 2022年06月15日
     * @param updateUserInfo
     * @return java.lang.Boolean 
     */
    Boolean updateUser(UpdateUserInfo updateUserInfo);

    /**
     * @description 退出登录
     *
     * @author wangjie
     * @date 16:57 2022年06月13日
     * @param
     * @return java.lang.Boolean
     */
    Boolean logout();
}
