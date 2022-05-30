package cn.raccoon.team.boot.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 主菜单
 *
 * @author wangjie
 * @date 11:33 2022年05月30日
 **/
@ApiModel("主菜单")
@Data
public class Menu implements Serializable {

    private Integer id;

    /**
    * 标题
    */
    @ApiModelProperty("标题")
    private String title;

    /**
    * 排序
    */
    @ApiModelProperty("排序")
    @TableField("`order`")
    private Integer order;

    /**
    * 图标地址或者图片的BASE64
    */
    @ApiModelProperty("图标地址或者图片的BASE64")
    private String icon;

    @ApiModelProperty("导航列表")
    @TableField(exist = false)
    private List<Navigation> navigationList;
}