package cn.raccoon.team.boot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件
 *
 * @author wangjie
 * @date 10:42 2022年05月31日
 **/
@Data
@ApiModel("文件")
public class File implements Serializable {

  @ApiModelProperty("id")
  private Integer id;

  @ApiModelProperty("路径")
  private String path;
}
