package cn.raccoon.team.boot.config.shiro.filter;

import cn.raccoon.team.boot.config.shiro.token.AuthToken;
import cn.raccoon.team.boot.exception.CommonException;
import cn.raccoon.team.boot.exception.EmError;
import cn.raccoon.team.boot.exception.response.R;
import cn.raccoon.team.boot.utils.JSONAuthentication;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * shiro是使用一系列过滤链来完成登录认证权限授权验证的。因此我们需要自定义一个认证过滤器，对其中的相关逻辑方法进行重写
 *
 * @author wangjie
 * @date 10:07 2022年05月31日
 **/
@Component("authFilter")
public class AutoFilter extends AuthenticatingFilter {

    /**
     * @description 生成自定义token
     *
     * @author wangjie
     * @date 10:02 2022年05月31日
     * @param servletRequest
     * @param servletResponse
     * @return org.apache.shiro.authc.AuthenticationToken
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = ((HttpServletRequest) servletRequest).getHeader("token");
        return new AuthToken(token);
    }

    /**
     * @description 判断是否登录，在登录的情况下会走此方法，此方法返回true直接访问控制器
     * 因此除了options请求，其他请求全部拒绝，进入到shiro过滤
     *
     * @author wangjie
     * @date 11:06 2022年05月31日
     * @param request
     * @param response
     * @param mappedValue
     * @return boolean
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest)request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }

    /**
     * @description 是否是拒绝登录，isAccessAllowed返回False的情况下会走此方法，这里用token直接执行登录。
     *
     * @author wangjie
     * @date 11:10 2022年05月31日
     * @param request
     * @param response
     * @return boolean
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = ((HttpServletRequest) request).getHeader("token");
        if (!StringUtils.hasLength(token)) {
            R<String> r = R.failed("登录凭证已失效");
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(403);
            JSONAuthentication.WriteJSON((HttpServletRequest) request, res, r);
        }
        return executeLogin(request, response);
    }

    /**
     * @description token失效时的调用
     *
     * @author wangjie
     * @date 11:44 2021年10月14日
     * @param token, e, request, response
     * @return boolean
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        R<String> r = R.failed("登录失败");
        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(403);
        try {
            JSONAuthentication.WriteJSON((HttpServletRequest) request,(HttpServletResponse) response, r);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ServletException servletException) {
            servletException.printStackTrace();
        }
        return false;
    }

    /**
     * @description 认证
     *
     * @author wangjie
     * @date 17:00 2021年10月22日
     * @param request, response
     * @return boolean
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String token = ((HttpServletRequest)request).getHeader("token");
        try {
            SecurityUtils.getSubject().login(new AuthToken(token));
        } catch (Exception e) {
            R<String> r = R.failed("您不具备访问的权限");
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(403);
            JSONAuthentication.WriteJSON((HttpServletRequest) response, res, r);
            return false;
        }
        return true;
    }
}
