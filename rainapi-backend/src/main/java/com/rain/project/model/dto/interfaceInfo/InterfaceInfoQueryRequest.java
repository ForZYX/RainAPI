package com.rain.project.model.dto.interfaceInfo;

import com.rain.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author rainapi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * 主键
     * 用户可能根据id查询
     */
//    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     * 用户可能根据名称查询
     */
    private String name;

    /**
     * 描述
     * 用户可能根据描述查询
     */
    private String description;

    /**
     * 接口地址
     * 用户可能根据地址查询
     */
    private String url;

//下面的都有可能
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
     */
    private Long userId;

//    用户不太可能根据创建时间、更新时间、是否删除查询，一般都是范围查询(删除)
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