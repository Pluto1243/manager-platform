package cn.raccoon.team.boot.service.impl;

import cn.raccoon.team.boot.entity.User;
import cn.raccoon.team.boot.exception.CommonException;
import cn.raccoon.team.boot.exception.EmError;
import cn.raccoon.team.boot.mapper.IUserMapper;
import cn.raccoon.team.boot.service.IUserService;
import cn.raccoon.team.boot.utils.BCryptPasswordEncoderUtil;
import cn.raccoon.team.boot.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    private IUserMapper mapper;

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
        return mapper.getUserByAccount(account);
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
    public Boolean changePassword(String password) {
        // 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        Integer id = jwtTokenUtil.getIdFromToken(token);
        User user = mapper.selectById(id);
        user.setPassword(passwordEncoderUtil.encode(password));
        return mapper.updateById(user) > 0;
    }


}
