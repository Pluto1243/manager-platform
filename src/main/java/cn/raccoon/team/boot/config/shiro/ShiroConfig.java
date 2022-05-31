package cn.raccoon.team.boot.config.shiro;

import cn.raccoon.team.boot.config.shiro.filter.AutoFilter;
import cn.raccoon.team.boot.entity.Permission;
import cn.raccoon.team.boot.mapper.ShiroMapper;
import com.github.pagehelper.util.StringUtil;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import  cn.raccoon.team.boot.config.shiro.realm.MyRealm;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Shiro配置
 *
 * @author wangjie
 * @date 11:19 2022年05月31日
 **/
@Configuration
@AutoConfigureAfter(DataSourceFactory.class)
public class ShiroConfig {

    @Bean("securityManager")
    public SecurityManager securityManager(MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm
        securityManager.setRealm(myRealm);
        // rememberMe
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, ShiroMapper shiroMapper) {
        System.out.println("----------------------进入Shiro过滤链----------------------");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 自定义authFilter
        Map<String, Filter> filter = new HashMap<>();

        filter.put("auth", new AutoFilter());

        shiroFilterFactoryBean.setFilters(filter);

        shiroFilterFactoryBean.setLoginUrl("/login");

        // 设置过滤 anon可以匿名访问， authc需要认证
        LinkedHashMap<String, String> shiroMap = new LinkedHashMap<>();
        // 放行
        shiroMap.put("/webjars/**", "anon");


        shiroMap.put("/error", "anon");
        shiroMap.put("/img/**", "anon");
        shiroMap.put("/swagger-resources", "anon");
        shiroMap.put("/v2/api-docs", "anon");
        shiroMap.put("v2/**", "anon");
        shiroMap.put("/swagger-resources/**", "anon");
        shiroMap.put("/doc.html", "anon");
        shiroMap.put("/user/userLogin", "anon");
        // 加载资源权限
        List<Permission> permissions = shiroMapper.getAllResourcePermission();
        permissions.stream().forEach(permission -> {
            if (StringUtil.isNotEmpty(permission.getPermission())) {
                shiroMap.put(permission.getPermission(), "auth");
            }
        });
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroMap);
        return shiroFilterFactoryBean;
    }

    /**
     * @description 生命周期后置处理器，实现bean 的生命周期控制。
     *
     * @author wangjie
     * @date 11:32 2022年05月31日 
     * @param 
     * @return org.apache.shiro.spring.LifecycleBeanPostProcessor 
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @description 开启Aop注解支持
     *
     * @author wangjie
     * @date 11:32 2022年05月31日
     * @param securityManager
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
