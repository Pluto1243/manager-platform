package cn.raccoon.team.boot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Qian
 * @Date 2021/12/20 16:32
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    @TableField(exist = false)
    private String token;
}
