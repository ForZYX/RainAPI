package com.rain.project.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {

    /**
     * 主键;
     * 在用户上传接口的时候，不用自己输入 id，后台会自动生成，所以不用用户上传;(刪掉)
     */
//    @TableId(type = IdType.AUTO)
//    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     * 接口状态默认都是关闭，有默认值就不用用户上传;(刪掉)
     */
//    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人
     * 创建人由当前登录的用户自动同步到后台，是后台自动生成的，不用用户上传;(刪掉)
     */
//    private Long userId;

//   创建时间、更新时间、是否删除都是自动生成的，不用用户上传;(刪掉)
    /**
     * 创建时间
     */
//    private Date createTime;

    /**
     * 更新时间
     */
//    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
//    @TableLogic
//    private Integer isDelete;
}