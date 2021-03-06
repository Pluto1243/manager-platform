package cn.raccoon.team.boot.mapper;

import cn.raccoon.team.boot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Author Qian
 * @Date 2021/12/20 16:33
 */
@Repository
public interface IUserMapper extends BaseMapper<User> {

    /**
     * 获取用户
     *
     * @param account
     * @return
     */
    User getUserByAccount(String account);

}
