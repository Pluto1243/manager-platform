package cn.raccoon.team.boot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 注册信息
 *
 * @author wangjie
 * @date 10:18 2022年06月10日
 **/
@ApiModel("注册信息")
@Data
public class RegisterInfo implements Serializable {

    @NotEmpty(message = "请输入用户名")
    @ApiModelProperty("用户名")
    private String username;

    @NotEmpty(message = "请输入密码")
    @ApiModelProperty("密码")
    private String password;
}
