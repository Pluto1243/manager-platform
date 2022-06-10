package cn.raccoon.team.boot.service.impl;

import cn.raccoon.team.boot.entity.RegisterInfo;
import cn.raccoon.team.boot.entity.User;
import cn.raccoon.team.boot.exception.CommonException;
import cn.raccoon.team.boot.exception.EmError;
import cn.raccoon.team.boot.mapper.IUserMapper;
import cn.raccoon.team.boot.mapper.ShiroMapper;
import cn.raccoon.team.boot.service.IUserService;
import cn.raccoon.team.boot.utils.BCryptPasswordEncoderUtil;
import cn.raccoon.team.boot.utils.JwtTokenUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Qian
 * @Date 2021/12/20 16:33
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private ShiroMapper shiroMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private BCryptPasswordEncoderUtil passwordEncoderUtil;

    /**
     * 获取用户
     *
     * @param account
     * @return
     */
    @Override
    public User getUserByAccount(String account) {
        return userMapper.getUserByAccount(account);
    }

    /**
     * 用户登录
     *
     * @param account
     * @param passwd
     * @return
     */
    @Override
    public User userLogin(String account, String passwd) {
        final User user = getUserByAccount(account);
        if (user == null) {
            throw new CommonException(EmError.USER_NOT_FOUND);
        }
        if (passwordEncoderUtil.matches(passwd, user.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            // token放入用户ID
            claims.put(Claims.ID, user.getId());
            // token放入用户电话
            claims.put(Claims.SUBJECT, user.getUsername());
            claims.put(Claims.ISSUED_AT, new Date());
            user.setToken(jwtTokenUtil.generateToken(claims));
            return user;
        }
        throw new CommonException(EmError.PASSWD_ACCOUNT_ERROR);
    }

    /**
     * 生成密钥
     *
     * @param password
     * @return
     */
    @Override
    public Boolean changePassword(String password, HttpServletResponse response) {
        // 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        Integer id = jwtTokenUtil.getIdFromToken(token);
        User user = userMapper.selectById(id);
        user.setPassword(passwordEncoderUtil.encode(password));
        response.setHeader("token", token);
        response.setHeader("Access-control-Expose-Headers", "token");   //跨域
        return userMapper.updateById(user) > 0;
    }

    @Override
    public Boolean register(RegisterInfo registerInfo) {
        User user = new User();
        user.setUsername(registerInfo.getUsername());
        user.setPassword(passwordEncoderUtil.encode(registerInfo.getPassword()));
        int flag = userMapper.insert(user);
        if (flag > 0) {
            // 设置 2（游客） 权限
            shiroMapper.setUserWithRole(user.getId(), 2);
        }
        return flag > 0;
    }

    @Override
    public Boolean checkUserName(String username) {
        return userMapper.selectCount(new QueryWrapper<User>().eq("username", username)) == 0;
    }
}
