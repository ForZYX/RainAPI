package com.rain.project.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {
    /**
     * 主键
     * id不能改，但是我们更新对象需要知道是更新哪条数据
     */
//    @TableId(type = IdType.AUTO)
    private Long id;

// 下面的都有可能会改
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
     * 请求参数
     */
    private String requestParams;

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
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人
     * 创建人一般情况下不会改(删除)
     * 如果你限定只有管理员能改，修改创建人也是合理的
     */
//    private Long userId;

    // 创建时间、更新时间、是否删除也不能改
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