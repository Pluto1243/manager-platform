package cn.raccoon.team.boot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Qian
 * @Date 2021/12/20 16:32
 */
@ApiModel("用户信息")
@Data
public class User implements Serializable {
    private Integer id;
    @ApiModelProperty("账号")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @TableField(exist = false)
    private String token;
}
