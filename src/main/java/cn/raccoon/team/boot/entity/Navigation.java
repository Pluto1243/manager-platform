package cn.raccoon.team.boot.entity;

import java.io.Serializable;
import java.util.Date;

import cn.raccoon.team.boot.entity.enums.OpenType;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导航
 *
 * @author wangjie
 * @date 11:33 2022年05月30日
 **/
@Data
@ApiModel("导航")
public class Navigation implements Serializable {
    private Integer id;

    /**
    * 菜单ID
    */
    @ApiModelProperty("菜单ID")
    private Integer menuId;

    /**
    * 标题
    */
    @ApiModelProperty("标题")
    private String title;

    /**
    * 描述
    */
    @ApiModelProperty("描述")
    private String description;

    /**
    * 链接地址
    */
    @ApiModelProperty("链接地址")
    private String link;

    /**
    * 图标地址或者图片的BASE64
    */
    @ApiModelProperty("图标地址或者图片的BASE64")
    private String icon;

    /**
    * 导航打开方式
    */
    @ApiModelProperty("导航打开方式")
    private OpenType openType;

    /**
    * 排序
    */
    @ApiModelProperty("排序")
    @TableField("`order`")
    private Integer order;
}