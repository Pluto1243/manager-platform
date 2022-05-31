package cn.raccoon.team.boot.config.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 我们的用户信息使用jwt存入了token中，用户是使用token进行登录，后台再验证的。因此我们需要重写Shiro的token类
 * 自定义一个AuthToken类，继承UsernamePasswordToken即可
 *
 * @author wangjie
 * @date 10:08 2022年05月31日
 **/
public class AuthToken extends UsernamePasswordToken {

    private String token;

    public AuthToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return this.token;
    }

    @Override
    public String getCredentials() {
        return this.token;
    }
}
