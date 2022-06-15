package cn.raccoon.team.boot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Qian
 * @Date 2021/12/20 16:32
 */
@ApiModel("更新用户信息")
@Data
public class UpdateUserInfo implements Serializable {
    @ApiModelProperty("头像ID")
    private Integer profile;
}
